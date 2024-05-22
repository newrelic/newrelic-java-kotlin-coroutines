package com.newrelic.instrumentation.kotlin.coroutines.tracing;

import java.util.Set;

import com.newrelic.agent.deps.org.objectweb.asm.commons.Method;
import com.newrelic.agent.instrumentation.methodmatchers.MethodMatcher;
import com.newrelic.agent.instrumentation.methodmatchers.NameMethodMatcher;

public class DispatchedTaskMethodMatcher implements MethodMatcher {
	
	private NameMethodMatcher matcher;
	
	public DispatchedTaskMethodMatcher() {
		matcher = new NameMethodMatcher("run");
	}

	@Override
	public boolean matches(int access, String name, String desc, Set<String> annotations) {
		return matcher.matches(access, name, desc, annotations);
	}

	@Override
	public Method[] getExactMethods() {
		return matcher.getExactMethods();
	}

}
