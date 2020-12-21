package kotlin.coroutines.jvm.internal;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import kotlin.coroutines.Continuation;

@Weave(type=MatchType.BaseClass)
public abstract class BaseContinuationImpl {
	
//	@NewField
//	private Token token = null;
	
	public BaseContinuationImpl(Continuation<Object> c) {
//		if(token == null) {
//			token = NewRelic.getAgent().getTransaction().getToken();
//		}
	}
	
	@Trace(async=true)
	protected Object invokeSuspend(java.lang.Object obj) {
//		if(token != null) {
//			token.linkAndExpire();
//			token = null;
//		}
		String name =  null;
		StackTraceElement element = getStackTraceElement();
		if(element != null) {
			name = element.getClassName() + "." + element.getMethodName();
		}
		if(name != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Continuation",name,"invokeSuspend");
		}
		return Weaver.callOriginal();
	}
	
	@Trace
	public void resumeWith(Object obj) {
		String name =  null;
		StackTraceElement element = getStackTraceElement();
		if(element != null) {
			name = element.getClassName() + "." + element.getMethodName();
		}
		if(name != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Continuation",name,"resumeWith");
		}
		Weaver.callOriginal();
	}

	public abstract StackTraceElement getStackTraceElement();
}
