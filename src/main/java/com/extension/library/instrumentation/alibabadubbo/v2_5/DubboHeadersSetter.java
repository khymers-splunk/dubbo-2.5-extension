/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package com.extension.library.instrumentation.alibabadubbo.v2_5;

import io.opentelemetry.context.propagation.TextMapSetter;

enum DubboHeadersSetter implements TextMapSetter<DubboRequest> {
  INSTANCE;

  @Override
  public void set(DubboRequest request, String key, String value) {
    request.context().setAttachment(key, value);
    request.invocation().setAttachment(key, value);
  }
}
