package kotlinx.coroutines;

import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.kotlin.coroutines_17.Utils;

@Weave
public abstract class CoroutineExceptionHandlerKt {

	@Trace
	public static void handleCoroutineException(kotlin.coroutines.CoroutineContext ctx,Throwable t) {
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
