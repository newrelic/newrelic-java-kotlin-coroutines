package com.newrelic.instrumentation.kotlin.coroutines;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Trace;

import kotlin.jvm.functions.Function1;

public class NRFunction1Wrapper<P1, R> implements Function1<P1, R> {
	
	private Function1<P1, R> delegate = null;
	private static boolean isTransformed = false;
	
	public NRFunction1Wrapper(Function1<P1,R> d) {
		delegate = d;
		if(!isTransformed) {
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
			isTransformed = true;
		}
	}

	@Override
	@Trace(dispatcher = true)
	public R invoke(P1 arg0) {
		return delegate != null ? delegate.invoke(arg0) : null;
	}

}
