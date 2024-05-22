package com.newrelic.instrumentation.kotlin.coroutines_17.tracing;

import java.util.Collection;

import com.newrelic.agent.deps.org.objectweb.asm.ClassReader;
import com.newrelic.agent.instrumentation.classmatchers.ChildClassMatcher;
import com.newrelic.agent.instrumentation.classmatchers.ClassMatcher;

public class DispatchedTaskClassMatcher extends ClassMatcher {
	
	private ChildClassMatcher matcher;
	
	public DispatchedTaskClassMatcher() {
		matcher = new ChildClassMatcher("kotlinx.coroutines.DispatchedTask",false);
	}

	@Override
	public boolean isMatch(ClassLoader loader, ClassReader cr) {
		return matcher.isMatch(loader, cr);
	}

	@Override
	public boolean isMatch(Class<?> clazz) {
		return matcher.isMatch(clazz);
	}

	@Override
	public Collection<String> getClassNames() {
		return matcher.getClassNames();
	}

}
