/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.javaagent.instrumentation.alibabadubbo.v2_5.helpers;

import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcInvocation;
import java.util.concurrent.Future;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.instrumentation.api.instrumenter.Instrumenter;

public final class TracingFilter implements Filter {

  private final Instrumenter<DubboRequest, Result> serverInstrumenter;
  private final Instrumenter<DubboRequest, Result> clientInstrumenter;

  TracingFilter(
      Instrumenter<DubboRequest, Result> serverInstrumenter,
      Instrumenter<DubboRequest, Result> clientInstrumenter) {
    this.serverInstrumenter = serverInstrumenter;
    this.clientInstrumenter = clientInstrumenter;
  }

  @Override
  public Result invoke(Invoker<?> invoker, Invocation invocation) {
    if (!(invocation instanceof RpcInvocation)) {
      return invoker.invoke(invocation);
    }

    RpcContext rpcContext = RpcContext.getContext();
    if (rpcContext.getUrl() == null || "injvm".equals(rpcContext.getUrl().getProtocol())) {
      return invoker.invoke(invocation);
    }

    boolean isServer = rpcContext.isProviderSide();
    Instrumenter<DubboRequest, Result> instrumenter =
        isServer ? serverInstrumenter : clientInstrumenter;
    Context parentContext = Context.current();
    DubboRequest request = DubboRequest.create((RpcInvocation) invocation, rpcContext);

    if (!instrumenter.shouldStart(parentContext, request)) {
      return invoker.invoke(invocation);
    }
    Context context = instrumenter.start(parentContext, request);

    Result result;
    boolean isSynchronous = true;
    try (Scope ignored = context.makeCurrent()) {
      result = invoker.invoke(invocation);
       /*if(!isServer) { //
        Future f = rpcContext.getContext().getFuture();
        if (f != null) {
          isSynchronous = false;
          // TODO: No CompletableFuture in dubbo 2.5, fix this for async
          CompletableFuture<Object> future = CompletableFuture.supplyAsync(()
          {
            try {
              f.get();
            } catch (InterruptedException e) {
              throw e;
            }
          });
          future.whenComplete(
              (o, throwable) -> instrumenter.end(context, request, result, throwable));
          }
        }*/
    } catch (Throwable e) {
      instrumenter.end(context, request, null, e);
      throw e;
    }
    if (isSynchronous) {
      instrumenter.end(context, request, result, result.getException());
    }
    return result;
  }
}
