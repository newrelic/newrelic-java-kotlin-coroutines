package com.nr.instrumentation.kotlin.coroutines;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Trace;

import kotlin.jvm.functions.Function1;

public class NRFunction1<P1, R> implements Function1<P1, R> {
	
	private Function1<P1, R> delegate = null;
	private static boolean isTransformed = false;
	
	public NRFunction1(Function1<P1, R> d) {
		delegate = d;
		if(!isTransformed) {
			isTransformed = true;
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
		}
	}

	@Override
	@Trace(dispatcher=true)
	public R invoke(P1 arg0) {
		return delegate.invoke(arg0);
	}

}
