package com.newrelic.instrumentation.kotlin.coroutines.tracing;

import java.util.ArrayList;
import java.util.Collection;

import com.newrelic.agent.deps.org.objectweb.asm.ClassReader;
import com.newrelic.agent.instrumentation.classmatchers.AndClassMatcher;
import com.newrelic.agent.instrumentation.classmatchers.ChildClassMatcher;
import com.newrelic.agent.instrumentation.classmatchers.ClassMatcher;
import com.newrelic.agent.instrumentation.classmatchers.ExactClassMatcher;
import com.newrelic.agent.instrumentation.classmatchers.NotMatcher;

public class SuspendClassMatcher extends ClassMatcher {
	
	ChildClassMatcher matcher;
	AndClassMatcher allMatcher;
	
	public SuspendClassMatcher() {
		matcher = new ChildClassMatcher("kotlin.coroutines.jvm.internal.BaseContinuationImpl",false);
		allMatcher = new AndClassMatcher(new NotMatcher(new ExactClassMatcher("com.newrelic.instrumentation.kotlin.coroutines_17.NRContinuationWrapper")),
				new NotMatcher(new ExactClassMatcher("com.newrelic.instrumentation.kotlin.coroutines_15.NRContinuationWrapper")),
				new NotMatcher(new ExactClassMatcher("com.newrelic.instrumentation.kotlin.coroutines_14.NRContinuationWrapper")),
				new NotMatcher(new ExactClassMatcher("com.newrelic.instrumentation.kotlin.coroutines.NRContinuationWrapper")));	}

	@Override
	public boolean isMatch(ClassLoader loader, ClassReader cr) {
		return matcher.isMatch(loader, cr) && allMatcher.isMatch(loader, cr);
	}

	@Override
	public boolean isMatch(Class<?> clazz) {
		return matcher.isMatch(clazz) && allMatcher.isMatch(clazz);
	}

	@Override
	public Collection<String> getClassNames() {
		Collection<String> childClasses = matcher.getClassNames();
		Collection<String> nrClasses = allMatcher.getClassNames();
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
