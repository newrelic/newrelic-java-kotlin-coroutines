package com.newrelic.instrumentation.kotlin.coroutines_18.tracing;

import com.newrelic.agent.Transaction;
import com.newrelic.agent.tracers.ClassMethodSignature;
import com.newrelic.agent.tracers.DefaultTracer;
import com.newrelic.agent.tracers.Tracer;
import com.newrelic.agent.tracers.TracerFactory;
import com.newrelic.agent.tracers.metricname.SimpleMetricNameFormat;
import com.newrelic.instrumentation.kotlin.coroutines_18.DispatchedTaskIgnores;

public class DispatchedTaskTracerFactory implements TracerFactory {
	
	protected static final String TRACER_FACTORY_NAME = "DISPATCHED_TASK_TRACER_FACTORY";

	@Override
	public Tracer getTracer(Transaction transaction, ClassMethodSignature sig, Object object, Object[] args) {
		
		if(DispatchedTaskIgnores.ignoreDispatchedTask(object)) {
			return null;
		}
		String name = DispatchedTaskIgnores.invoke(object);
		if(name == null || name.isEmpty()) name = "Unknown";
		return new DefaultTracer(transaction, sig, object, new SimpleMetricNameFormat("Custom/DispatchedTask/"+name));
	}

}
