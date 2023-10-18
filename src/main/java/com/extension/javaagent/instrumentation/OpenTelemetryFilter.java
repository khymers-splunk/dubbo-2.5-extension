package com.extension.javaagent.instrumentation;

import com.alibaba.dubbo.common.extension.Activate;
import com.alibaba.dubbo.rpc.Filter;
import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;
import com.alibaba.dubbo.rpc.Result;
import io.opentelemetry.api.GlobalOpenTelemetry;
import com.extension.library.instrumentation.alibabadubbo.v2_5.DubboTelemetry;
import com.extension.library.instrumentation.alibabadubbo.v2_5.internal.DubboClientNetworkAttributesGetter;
import io.opentelemetry.instrumentation.api.instrumenter.net.PeerServiceAttributesExtractor;
import io.opentelemetry.javaagent.bootstrap.internal.CommonConfig;

@Activate(group = {"consumer", "provider"})
public class OpenTelemetryFilter implements Filter {

  private final Filter delegate;

  public OpenTelemetryFilter() {
    delegate =
        DubboTelemetry.builder(GlobalOpenTelemetry.get())
            .addAttributesExtractor(
                PeerServiceAttributesExtractor.create(
                    new DubboClientNetworkAttributesGetter(),
                    CommonConfig.get().getPeerServiceResolver()))
            .build()
            .newFilter();
  }

  @Override
  public Result invoke(Invoker<?> invoker, Invocation invocation) {
    return delegate.invoke(invoker, invocation);
  }
}
