package com.newrelic.instrumentation.kotlin.coroutines_18.tracing;

import java.lang.instrument.Instrumentation;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import com.newrelic.agent.TracerService;
import com.newrelic.agent.core.CoreService;
import com.newrelic.agent.instrumentation.ClassTransformerService;
import com.newrelic.agent.instrumentation.context.ClassMatchVisitorFactory;
import com.newrelic.agent.instrumentation.context.InstrumentationContextManager;
import com.newrelic.agent.service.ServiceFactory;
import com.newrelic.api.agent.NewRelic;

public class CoroutinesPreMain {

	private static int max_retries = 20;
	private static ScheduledExecutorService executor = null;
	
	public static void premain(String args, Instrumentation inst) {
		boolean b = setup();
		if(!b) {
			executor = Executors.newSingleThreadScheduledExecutor();
			executor.schedule(new Setup(), 100L, TimeUnit.MILLISECONDS);
		}
		
	}
	
	public static boolean setup() {
		
		TracerService tracerService = ServiceFactory.getTracerService();
		ClassTransformerService classTransformerService = ServiceFactory.getClassTransformerService();
		CoreService coreService = ServiceFactory.getCoreService();
		
		if(tracerService != null && classTransformerService != null && coreService != null) {
			tracerService.registerTracerFactory(SuspendTracerFactory.TRACER_FACTORY_NAME, new SuspendTracerFactory());
			tracerService.registerTracerFactory(DispatchedTaskTracerFactory.TRACER_FACTORY_NAME, new DispatchedTaskTracerFactory());
			InstrumentationContextManager contextMgr = classTransformerService.getContextManager();
			
			if(contextMgr != null) {
				SuspendClassTransformer suspendTransformer = new SuspendClassTransformer(contextMgr);
				SuspendClassAndMethod suspendMatcher = new SuspendClassAndMethod();
				ClassMatchVisitorFactory suspendMatchVistor = suspendTransformer.addMatcher(suspendMatcher);
				
				Set<ClassMatchVisitorFactory> factories = new HashSet<>();
				factories.add(suspendMatchVistor);
				
				
				DispatchedClassTransformer dispatchedTransformer = new DispatchedClassTransformer(contextMgr);
				DispatchedTaskClassAndMethod dispatchedMatcher = new DispatchedTaskClassAndMethod();
				ClassMatchVisitorFactory dispatchedMatchVistor = dispatchedTransformer.addMatcher(dispatchedMatcher);
				
				Class<?>[] allLoadedClasses = coreService.getInstrumentation().getAllLoadedClasses();

				classTransformerService.retransformMatchingClassesImmediately(allLoadedClasses, factories);
				factories.add(dispatchedMatchVistor);
				
				return true;
			}
		}
		
		return false;
	}
	
	private static class Setup implements Runnable {
		
		private static int count = 0;

		@Override
		public void run() {
			count++;
			NewRelic.getAgent().getLogger().log(Level.FINE, "Call {0} to attempt setting up Suspend ClassTransformer",count);
			boolean b = setup();
			
			if(!b) {
				if(count < max_retries) {
					executor.schedule(this, 2L, TimeUnit.SECONDS);
				} else {
					NewRelic.getAgent().getLogger().log(Level.FINE, "Failed to initiate Suspend Client Transformer after {0} tries", max_retries);
					executor.shutdownNow();
				}
			}

		}
		
	}

}
