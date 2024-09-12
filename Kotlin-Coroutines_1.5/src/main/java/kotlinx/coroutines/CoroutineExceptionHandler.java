package kotlinx.coroutines;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.kotlin.coroutines_15.Utils;

@Weave(type = MatchType.Interface)
public abstract class CoroutineExceptionHandler {

	@Trace
	public void handleException(kotlin.coroutines.CoroutineContext ctx, java.lang.Throwable t) {
		if (!(t instanceof JobCancellationException)) {
			String cName = Utils.getCoroutineName(ctx);
			if(cName != null && !cName.isEmpty()) {
				HashMap<String, String> attributes = new HashMap<>();
				attributes.put("Coroutine-Name", cName);
				if (!(t instanceof JobCancellationException)) {
					NewRelic.noticeError(t, attributes);
				}
			} else {
				if (!(t instanceof JobCancellationException)) {
					NewRelic.noticeError(t);
				}
			}
		}		
		Weaver.callOriginal();
	}
}
