package com.extension.library.instrumentation.alibabadubbo.v2_5;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.rpc.RpcContext;
import com.alibaba.dubbo.rpc.RpcInvocation;
import java.net.InetSocketAddress;
import javax.annotation.Generated;
import javax.annotation.Nullable;

@Generated("com.google.auto.value.processor.AutoValueProcessor")
final class AutoValue_DubboRequest extends DubboRequest {

  private final RpcInvocation invocation;

  private final RpcContext context;

  private final URL url;

  @Nullable
  private final InetSocketAddress remoteAddress;

  @Nullable
  private final InetSocketAddress localAddress;

  AutoValue_DubboRequest(
      RpcInvocation invocation,
      RpcContext context,
      URL url,
      @Nullable InetSocketAddress remoteAddress,
      @Nullable InetSocketAddress localAddress) {
    if (invocation == null) {
      throw new NullPointerException("Null invocation");
    }
    this.invocation = invocation;
    if (context == null) {
      throw new NullPointerException("Null context");
    }
    this.context = context;
    if (url == null) {
      throw new NullPointerException("Null url");
    }
    this.url = url;
    this.remoteAddress = remoteAddress;
    this.localAddress = localAddress;
  }

  @Override
  RpcInvocation invocation() {
    return invocation;
  }

  @Override
  public RpcContext context() {
    return context;
  }

  @Override
  public URL url() {
    return url;
  }

  @Nullable
  @Override
  public InetSocketAddress remoteAddress() {
    return remoteAddress;
  }

  @Nullable
  @Override
  public InetSocketAddress localAddress() {
    return localAddress;
  }

  @Override
  public String toString() {
    return "DubboRequest{"
        + "invocation=" + invocation + ", "
        + "context=" + context + ", "
        + "url=" + url + ", "
        + "remoteAddress=" + remoteAddress + ", "
        + "localAddress=" + localAddress
        + "}";
  }

  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (o instanceof DubboRequest) {
      DubboRequest that = (DubboRequest) o;
      return this.invocation.equals(that.invocation())
          && this.context.equals(that.context())
          && this.url.equals(that.url())
          && (this.remoteAddress == null ? that.remoteAddress() == null : this.remoteAddress.equals(that.remoteAddress()))
          && (this.localAddress == null ? that.localAddress() == null : this.localAddress.equals(that.localAddress()));
    }
    return false;
  }

  @Override
  public int hashCode() {
    int h$ = 1;
    h$ *= 1000003;
    h$ ^= invocation.hashCode();
    h$ *= 1000003;
    h$ ^= context.hashCode();
    h$ *= 1000003;
    h$ ^= url.hashCode();
    h$ *= 1000003;
    h$ ^= (remoteAddress == null) ? 0 : remoteAddress.hashCode();
    h$ *= 1000003;
    h$ ^= (localAddress == null) ? 0 : localAddress.hashCode();
    return h$;
  }

}
