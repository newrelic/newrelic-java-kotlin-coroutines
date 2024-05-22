package com.newrelic.instrumentation.kotlin.coroutines_17.tracing;

import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

import com.newrelic.agent.deps.org.objectweb.asm.commons.Method;
import com.newrelic.agent.instrumentation.InstrumentationType;
import com.newrelic.agent.instrumentation.classmatchers.ClassAndMethodMatcher;
import com.newrelic.agent.instrumentation.classmatchers.OptimizedClassMatcher.Match;
import com.newrelic.agent.instrumentation.classmatchers.OptimizedClassMatcherBuilder;
import com.newrelic.agent.instrumentation.context.ClassMatchVisitorFactory;
import com.newrelic.agent.instrumentation.context.ContextClassTransformer;
import com.newrelic.agent.instrumentation.context.InstrumentationContext;
import com.newrelic.agent.instrumentation.context.InstrumentationContextManager;
import com.newrelic.agent.instrumentation.methodmatchers.MethodMatcher;
import com.newrelic.agent.instrumentation.tracing.TraceDetailsBuilder;
import com.newrelic.api.agent.NewRelic;

public class SuspendClassTransformer implements ContextClassTransformer {
	
	private Map<String, ClassMatchVisitorFactory> matchers = null;
	private final InstrumentationContextManager contextMgr;
	
	public SuspendClassTransformer(InstrumentationContextManager mgr) {
		contextMgr = mgr;
		matchers = new HashMap<>();
	}
	
	protected ClassMatchVisitorFactory addMatcher(ClassAndMethodMatcher matcher) {
		NewRelic.getAgent().getLogger().log(Level.FINE, "Adding matcher {0} to service classtransformer", matcher);
		OptimizedClassMatcherBuilder builder = OptimizedClassMatcherBuilder.newBuilder();
		builder.addClassMethodMatcher(matcher);
		ClassMatchVisitorFactory matchVistor = builder.build();
		matchers.put(matcher.getClass().getSimpleName(), matchVistor);
		contextMgr.addContextClassTransformer(matchVistor, this);
		return matchVistor;
	}
	
    protected void removeMatcher(ClassAndMethodMatcher matcher) {
    	ClassMatchVisitorFactory matchVisitor = matchers.remove(matcher.getClass().getSimpleName());
    	if(matchVisitor != null) {
    		contextMgr.removeMatchVisitor(matchVisitor);
    	}
    }

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined,
			ProtectionDomain protectionDomain, byte[] classfileBuffer, InstrumentationContext context, Match match)
			throws IllegalClassFormatException {
		for(Method method : match.getMethods()) {
			for(ClassAndMethodMatcher matcher : match.getClassMatches().keySet()) {
				if (matcher.getMethodMatcher().matches(MethodMatcher.UNSPECIFIED_ACCESS, method.getName(),
						method.getDescriptor(), match.getMethodAnnotations(method))) {
					context.putTraceAnnotation(method, TraceDetailsBuilder.newBuilder().setTracerFactoryName(SuspendTracerFactory.TRACER_FACTORY_NAME).setDispatcher(true).setInstrumentationSourceName("CoroutinesCore").setInstrumentationType(InstrumentationType.TraceAnnotation).build());
				}

			}
		}
		return null;
	}

}
