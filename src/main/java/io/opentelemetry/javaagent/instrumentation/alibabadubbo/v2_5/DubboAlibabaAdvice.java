package io.opentelemetry.javaagent.instrumentation.alibabadubbo.v2_5;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.common.AttributesBuilder;
import io.opentelemetry.api.metrics.DoubleHistogram;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.javaagent.bootstrap.Java8BytecodeBridge;
import net.bytebuddy.asm.Advice.This;

import net.bytebuddy.asm.Advice;

import com.alibaba.dubbo.rpc.Invocation;
import com.alibaba.dubbo.rpc.Invoker;

import io.opentelemetry.javaagent.instrumentation.alibabadubbo.v2_5.OpenTelemetryFilter;

public class DubboAlibabaAdvice {

  @SuppressWarnings("unchecked")
  @Advice.OnMethodExit(suppress = Throwable.class)
  public static void onExit(@Advice.AllArguments() Object[] args, @Advice.Origin("#t") String type, @Advice.Origin("#m") String method) throws ClassNotFoundException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, NoSuchFieldException
  {
    if(method == "invoke") {
      OpenTelemetryFilter f = new OpenTelemetryFilter();
      Invoker i1 = (Invoker)args[0];
      Invocation i2 = (Invocation)args[1];
      f.invoke(i1, i2);
    }
  }

}
