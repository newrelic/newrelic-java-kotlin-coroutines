package com.github.marcoferrer.krotoplus.coroutines.client;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import io.grpc.CallOptions;
import io.grpc.Channel;
import io.grpc.MethodDescriptor;
import kotlin.coroutines.Continuation;

@Weave
public abstract class ClientCallsKt {

	@Trace
	public static final <ReqT, RespT> Object clientCallUnary(ReqT request,MethodDescriptor<ReqT, RespT> method, Channel channel, CallOptions options, Continuation<? super RespT> completion)  {
		return Weaver.callOriginal();
	}
}
