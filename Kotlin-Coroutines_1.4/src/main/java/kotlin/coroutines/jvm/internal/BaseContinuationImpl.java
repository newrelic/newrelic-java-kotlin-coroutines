package kotlin.coroutines.jvm.internal;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type = MatchType.BaseClass)
public abstract class BaseContinuationImpl  {


	@Trace(dispatcher = true)
	protected Object invokeSuspend(Object obj) {
		String contString = toString();
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Kotlin","Coroutines","SuspendFunction",contString);
		return Weaver.callOriginal();
	}
}
