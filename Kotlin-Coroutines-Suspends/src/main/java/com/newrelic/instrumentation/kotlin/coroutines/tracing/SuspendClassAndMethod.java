package com.newrelic.instrumentation.kotlin.coroutines.tracing;

import com.newrelic.agent.instrumentation.classmatchers.ClassAndMethodMatcher;
import com.newrelic.agent.instrumentation.classmatchers.ClassMatcher;
import com.newrelic.agent.instrumentation.methodmatchers.MethodMatcher;

public class SuspendClassAndMethod implements ClassAndMethodMatcher {
	
	private ClassMatcher classMatcher;
	private MethodMatcher methodMatcher;
	
	public SuspendClassAndMethod() {
		classMatcher = new SuspendClassMatcher();
		methodMatcher = new SuspendMethodMatcher();
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
