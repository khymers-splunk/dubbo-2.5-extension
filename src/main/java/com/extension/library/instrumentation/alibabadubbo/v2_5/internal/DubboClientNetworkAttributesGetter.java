/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package com.extension.library.instrumentation.alibabadubbo.v2_5.internal;

import com.extension.library.instrumentation.alibabadubbo.v2_5.DubboRequest;
import io.opentelemetry.instrumentation.api.instrumenter.network.ServerAttributesGetter;
import java.net.InetSocketAddress;
import javax.annotation.Nullable;
import com.alibaba.dubbo.rpc.Result;

/**
 * This class is internal and is hence not for public use. Its APIs are unstable and can change at
 * any time.
 */
public final class DubboClientNetworkAttributesGetter
    implements ServerAttributesGetter<DubboRequest, Result> {

  @Nullable
  @Override
  public String getServerAddress(DubboRequest request) {
    return request.url().getHost();
  }

  @Override
  public Integer getServerPort(DubboRequest request) {
    return request.url().getPort();
  }

  @Override
  @Nullable
  public InetSocketAddress getServerInetSocketAddress(
      DubboRequest request, @Nullable Result response) {
    return request.remoteAddress();
  }
}
