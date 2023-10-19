/*
 * Copyright The OpenTelemetry Authors
 * SPDX-License-Identifier: Apache-2.0
 */

package io.opentelemetry.javaagent.instrumentation.alibabadubbo.v2_5;

import static io.opentelemetry.javaagent.extension.matcher.AgentElementMatchers.hasClassesNamed;
import static java.util.Collections.singletonList;
import static net.bytebuddy.matcher.ElementMatchers.named;

import com.google.auto.service.AutoService;
import io.opentelemetry.javaagent.extension.instrumentation.HelperResourceBuilder;
import io.opentelemetry.javaagent.extension.instrumentation.InstrumentationModule;
import io.opentelemetry.javaagent.extension.instrumentation.TypeInstrumentation;
import io.opentelemetry.javaagent.extension.instrumentation.TypeTransformer;
import io.opentelemetry.javaagent.extension.matcher.AgentElementMatchers;
import java.util.List;
import java.util.Arrays;
import java.util.logging.Logger;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.matcher.ElementMatcher;

/**
 * This is a custom java extension to provide backward support for Dubbo 2.5.*
 * Note that Dubbo 2.5 has been superceeded and is no longer supported.
 */
@AutoService(InstrumentationModule.class)
public final class DubboAlibabaInstrumentationModule extends InstrumentationModule {

  private static final Logger logger = Logger.getLogger(InstrumentationModule.class.getName());
  private static final String CLASS_LOADER_MATCH = "com.alibaba.dubbo.rpc.Filter";
  //private static final String CLASS_TO_INTERCEPT = "com.alibaba.dubbo.common.extension.ExtensionLoader";
  private static final String CLASS_TO_INTERCEPT = "com.alibaba.dubbo.monitor.support.MonitorFilter";
  private static final String METHOD_TO_INTERCEPT = "invoke";

  public DubboAlibabaInstrumentationModule() {
    super("alibaba-dubbo", "alibaba-dubbo-2.5");
  }

  @Override
  public ElementMatcher.Junction<ClassLoader> classLoaderMatcher() {
    return hasClassesNamed(CLASS_LOADER_MATCH);
  }

  @Override
  public boolean isHelperClass(String className) {
    return className.startsWith("io.opentelemetry.javaagent.instrumentation.alibabadubbo.v2_5");
  }

  @Override
  public boolean isIndyModule() {
    return false;
  }

  @Override
  public List<TypeInstrumentation> typeInstrumentations() {
    return singletonList(new ResourceInjectingTypeInstrumentation());
  }

  // A type instrumentation is needed to trigger resource injection.
  public static class ResourceInjectingTypeInstrumentation implements TypeInstrumentation {
    @Override
    public ElementMatcher<TypeDescription> typeMatcher() {
      return named(CLASS_TO_INTERCEPT);
    }

    @Override
    public void transform(TypeTransformer transformer) {

      transformer.applyAdviceToMethod(
          named(METHOD_TO_INTERCEPT),
          "io.opentelemetry.javaagent.instrumentation.alibabadubbo.v2_5.DubboAlibabaAdvice");
    }
  }
}
