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
		String cName = Utils.getCoroutineName(ctx);
		if(cName != null && !cName.isEmpty()) {
			HashMap<String, String> attributes = new HashMap<>();
			attributes.put("Coroutine-Name", cName);
			NewRelic.noticeError(t, attributes);
		} else {
			NewRelic.noticeError(t);
		}
		
		Weaver.callOriginal();
	}
}
