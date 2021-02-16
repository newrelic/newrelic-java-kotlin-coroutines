package com.github.marcoferrer.krotoplus.coroutines.client;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import kotlin.coroutines.Continuation;

@Weave
public class SuspendingUnaryObserver<RespT> {
	
	@NewField
	private Token token = null;

	public SuspendingUnaryObserver(Continuation<RespT> cont) {
		Token t = NewRelic.getAgent().getTransaction().getToken();
		if(t != null && t.isActive()) {
			token =t;
		} else if(t != null) {
			t.expire();
			t = null;
		}
		
	}
	
	@Trace(async=true)
	public void onNext(Object value) {
		if(token != null) {
			token.link();
		}
		Weaver.callOriginal();
	}
	
	public void onError(Throwable t) {
		NewRelic.noticeError(t);
		if(token != null) {
			token.expire();
			token = null;
		}
		Weaver.callOriginal();
	}
	
	public void onCompleted() {
		if(token != null) {
			token.expire();
			token = null;
		}
		Weaver.callOriginal();
	}
}
