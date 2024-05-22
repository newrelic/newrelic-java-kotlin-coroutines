package com.newrelic.instrumentation.kotlin.coroutines.tracing;

import java.util.ArrayList;
import java.util.Collection;

import com.newrelic.agent.deps.org.objectweb.asm.ClassReader;
import com.newrelic.agent.instrumentation.classmatchers.ChildClassMatcher;
import com.newrelic.agent.instrumentation.classmatchers.ClassMatcher;
import com.newrelic.agent.instrumentation.classmatchers.ExactClassMatcher;
import com.newrelic.agent.instrumentation.classmatchers.NotMatcher;

public class SuspendClassMatcher extends ClassMatcher {
	
	ChildClassMatcher matcher;
	NotMatcher nrMatcher;
	
	public SuspendClassMatcher() {
		matcher = new ChildClassMatcher("kotlin.coroutines.jvm.internal.BaseContinuationImpl",false);
		nrMatcher = new NotMatcher(new ExactClassMatcher("com.newrelic.instrumentation.kotlin.coroutines.NRWrappedSuspend"));
	}

	@Override
	public boolean isMatch(ClassLoader loader, ClassReader cr) {
		return matcher.isMatch(loader, cr) && nrMatcher.isMatch(loader, cr);
	}

	@Override
	public boolean isMatch(Class<?> clazz) {
		return matcher.isMatch(clazz) && nrMatcher.isMatch(clazz);
	}

	@Override
	public Collection<String> getClassNames() {
		Collection<String> childClasses = matcher.getClassNames();
		Collection<String> nrClasses = nrMatcher.getClassNames();
		if(childClasses == null && nrClasses == null) return null;
		
		ArrayList<String> list = new ArrayList<>();
		if(childClasses != null) {
			list.addAll(childClasses);
		}
		if(nrClasses != null) {
			list.addAll(nrClasses);
		}
		
		return list;
	}

}
