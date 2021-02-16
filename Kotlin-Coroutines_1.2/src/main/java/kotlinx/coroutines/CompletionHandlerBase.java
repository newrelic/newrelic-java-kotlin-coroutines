package kotlinx.coroutines;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave(type=MatchType.BaseClass)
public abstract class CompletionHandlerBase {

	@Trace
	public void invoke(Throwable t) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","CompletionHandler",getClass().getSimpleName(),"invoke");
		if(t != null) {
			NewRelic.noticeError(t);
		}
		Weaver.callOriginal();
	}
}
