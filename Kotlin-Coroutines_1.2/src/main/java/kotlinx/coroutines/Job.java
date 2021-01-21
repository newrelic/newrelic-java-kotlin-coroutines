package kotlinx.coroutines;

import java.util.concurrent.CancellationException;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import kotlin.Unit;
import kotlin.coroutines.Continuation;

@Weave(type=MatchType.Interface)
public abstract class Job {

	@Trace
	public void cancel(CancellationException e) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Job",getClass().getSimpleName(),"cancel");
		if(e != null) {
			NewRelic.noticeError(e);
		}
		Weaver.callOriginal();
	}
	
	@Trace
	public Object join(Continuation<? super Unit> c) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Job",getClass().getSimpleName(),"join");
		return Weaver.callOriginal();
	}
	
}
