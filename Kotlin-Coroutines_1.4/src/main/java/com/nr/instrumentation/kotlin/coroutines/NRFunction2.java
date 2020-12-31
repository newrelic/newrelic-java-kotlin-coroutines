package com.nr.instrumentation.kotlin.coroutines;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Trace;

import kotlin.jvm.functions.Function2;

public class NRFunction2<P1, P2, R> implements Function2<P1, P2, R> {
	
	Function2<P1, P2, R> delegate = null;
	private static boolean isTransformed = false;
	
	public NRFunction2(Function2<P1, P2, R> d) {
		delegate = d;
		if(!isTransformed) {
			isTransformed = true;
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
		}
	}

	@Override
	@Trace(dispatcher=true)
	public R invoke(P1 arg0, P2 arg1) {
		
		return delegate.invoke(arg0, arg1);
	}

}
