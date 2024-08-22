package com.newrelic.instrumentation.kotlin.coroutines.tracing;

import java.util.Collection;

import com.newrelic.agent.deps.org.objectweb.asm.ClassReader;
import com.newrelic.agent.instrumentation.classmatchers.ChildClassMatcher;
import com.newrelic.agent.instrumentation.classmatchers.ClassMatcher;
import com.newrelic.agent.instrumentation.classmatchers.OrClassMatcher;

public class SuspendClassMatcher extends ClassMatcher {
	
	private ClassMatcher orMatcher;
	
	public SuspendClassMatcher() {
		ChildClassMatcher lambdamatcher = new ChildClassMatcher("kotlin.coroutines.jvm.internal.SuspendLambda",true);
		ChildClassMatcher restrictedlambdamatcher = new ChildClassMatcher("kotlin.coroutines.jvm.internal.RestrictedSuspendLambda",true);
		orMatcher = OrClassMatcher.getClassMatcher(lambdamatcher,restrictedlambdamatcher);
	}

	@Override
	public boolean isMatch(ClassLoader loader, ClassReader cr) {
		return orMatcher.isMatch(loader, cr);
	}

	@Override
	public boolean isMatch(Class<?> clazz) {
		return orMatcher.isMatch(clazz);
	}

	@Override
	public Collection<String> getClassNames() {
		return orMatcher.getClassNames();
	}

}
