/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.javaagent.instrumentation.alibabadubbo.v2_5.helpers;

import com.alibaba.dubbo.rpc.Result;
import io.opentelemetry.instrumentation.api.instrumenter.network.ClientAttributesGetter;
import io.opentelemetry.instrumentation.api.instrumenter.network.ServerAttributesGetter;
import java.net.InetSocketAddress;
import javax.annotation.Nullable;

final class DubboNetworkServerAttributesGetter
    implements ServerAttributesGetter<DubboRequest, Result>,
        ClientAttributesGetter<DubboRequest, Result> {

  @Nullable
  @Override
  public String getServerAddress(DubboRequest request) {
    return null;
  }

  @Nullable
  @Override
  public Integer getServerPort(DubboRequest request) {
    return null;
  }

  @Nullable
  @Override
  public InetSocketAddress getServerInetSocketAddress(
      DubboRequest request, @Nullable Result result) {
    return request.localAddress();
  }

  @Override
  @Nullable
  public InetSocketAddress getClientInetSocketAddress(
      DubboRequest request, @Nullable Result result) {
    return request.remoteAddress();
  }
}
