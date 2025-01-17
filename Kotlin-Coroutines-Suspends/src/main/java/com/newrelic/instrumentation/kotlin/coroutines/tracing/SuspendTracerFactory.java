package com.newrelic.instrumentation.kotlin.coroutines.tracing;

import com.newrelic.agent.Transaction;
import com.newrelic.agent.tracers.ClassMethodSignature;
import com.newrelic.agent.tracers.DefaultTracer;
import com.newrelic.agent.tracers.Tracer;
import com.newrelic.agent.tracers.TracerFactory;
import com.newrelic.agent.tracers.metricname.SimpleMetricNameFormat;
import com.newrelic.instrumentation.kotlin.coroutines.SuspendIgnores;
import com.newrelic.instrumentation.kotlin.coroutines.Utils;

public class SuspendTracerFactory implements TracerFactory {
	
	protected static final String TRACER_FACTORY_NAME = "SUSPEND_TRACER_FACTORY";

	@Override
	public Tracer getTracer(Transaction transaction, ClassMethodSignature sig, Object object, Object[] args) {
		
		if(SuspendIgnores.ignoreSuspend(object)) {
			return null;
		}
		return new DefaultTracer(transaction, sig, object, new SimpleMetricNameFormat("Custom/SuspendFunction/"+Utils.getSuspendString(object.toString(), object)));
	}

}
