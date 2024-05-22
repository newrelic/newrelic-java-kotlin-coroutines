package com.newrelic.instrumentation.kotlin.coroutines_15.tracing;

import com.newrelic.agent.instrumentation.classmatchers.ClassAndMethodMatcher;
import com.newrelic.agent.instrumentation.classmatchers.ClassMatcher;
import com.newrelic.agent.instrumentation.methodmatchers.MethodMatcher;

public class DispatchedTaskClassAndMethod implements ClassAndMethodMatcher {
	
	private DispatchedTaskClassMatcher classMatcher;
	private DispatchedTaskMethodMatcher methodMatcher;
	
	public DispatchedTaskClassAndMethod() {
		classMatcher = new DispatchedTaskClassMatcher();
		methodMatcher = new DispatchedTaskMethodMatcher();
	}

	@Override
	public ClassMatcher getClassMatcher() {
		return classMatcher;
	}

	@Override
	public MethodMatcher getMethodMatcher() {
		return methodMatcher;
	}

}
