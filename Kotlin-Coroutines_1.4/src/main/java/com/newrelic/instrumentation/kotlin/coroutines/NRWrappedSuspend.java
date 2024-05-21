package com.newrelic.instrumentation.kotlin.coroutines;

import java.util.Map;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;

import kotlin.jvm.functions.Function2;

public class NRWrappedSuspend {
	
	private static NRWrappedSuspend INSTANCE = null;
	
	protected synchronized static NRWrappedSuspend getInstance() {
		if(INSTANCE == null) {
			INSTANCE = new NRWrappedSuspend();
		}
		return INSTANCE;
	}
	
	private NRWrappedSuspend() {
		AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
	}

	@Trace(dispatcher = true)
	protected <R,P1,P2>  R invoke(P1 p1, P2 p2, Function2<P1, P2, R> f,Map<String, Object> attributes, String... metricNames) {
		NewRelic.getAgent().getTracedMethod().setMetricName(metricNames);
		if(attributes != null && !attributes.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().addCustomAttributes(attributes);
		}
		if(f != null) {
			return f.invoke(p1, p2);
		}
		return null;
	}
}
