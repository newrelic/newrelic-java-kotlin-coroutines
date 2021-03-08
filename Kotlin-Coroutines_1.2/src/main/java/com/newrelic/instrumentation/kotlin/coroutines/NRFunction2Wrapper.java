package com.newrelic.instrumentation.kotlin.coroutines;

import java.util.logging.Level;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.jvm.functions.Function2;

public class NRFunction2Wrapper<P1, P2, R> implements Function2<P1, P2, R> {
	
	private Function2<P1, P2, R> delegate = null;
	private String name = null;
	private static boolean isTransformed = false;
	
	public NRFunction2Wrapper(Function2<P1, P2, R> d,String n) {
		NewRelic.getAgent().getLogger().log(Level.FINE, "Wrapping Function2: {0} - {1} using name: {2}", d, d.getClass().getName(),n);
		delegate = d;
		name = n;
		if(!isTransformed) {
			isTransformed = true;
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
		}
	}

	@Override
	@Trace(dispatcher=true)
	public R invoke(P1 p1, P2 p2) {
		String nameStr = null;
		if(p2 instanceof Continuation) {
			Continuation continuation = (Continuation)p2;
			NRContinuationWrapper wrapper = new NRContinuationWrapper(continuation);
			p2 = (P2) wrapper;
		}
		if(nameStr == null) {
			nameStr = name;
		}
		if(nameStr != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","WrappedSuspend",nameStr);
		}
		if(delegate != null) {
			return delegate.invoke(p1, p2);
		}
		return null;
	}

}
