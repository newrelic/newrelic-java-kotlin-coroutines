package com.newrelic.instrumentation.kotlin.coroutines.tracing;

import com.newrelic.agent.Transaction;
import com.newrelic.agent.TransactionActivity;
import com.newrelic.agent.tracers.ClassMethodSignature;
import com.newrelic.agent.tracers.DefaultTracer;
import com.newrelic.agent.tracers.Tracer;
import com.newrelic.agent.tracers.metricname.SimpleMetricNameFormat;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.instrumentation.kotlin.coroutines.Utils;

public class KotlinCoroutineTracer extends DefaultTracer {

	public KotlinCoroutineTracer(Transaction transaction, ClassMethodSignature sig, Object object) {
		super(transaction, sig, object, new SimpleMetricNameFormat("Custom/SuspendFunction/"+Utils.getSuspendString(object.toString(), object)));
	}

	@Override
	public void performFinishWork(long finishTime, int opcode, Object returnValue) {
		TransactionActivity txa = getTransactionActivity();
		if(txa != null) {
			Tracer lastTracer = txa.getLastTracer();
			if(!lastTracer.equals(this)) {
				NewRelic.recordMetric("Kotlin/Coroutines/SuspendTracer/InconsistentState", 1.0F);
				return;
			}
		}
		super.performFinishWork(finishTime, opcode, returnValue);
	}	
	
}
