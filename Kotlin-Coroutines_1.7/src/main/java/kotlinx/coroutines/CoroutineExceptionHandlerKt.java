package kotlinx.coroutines;

import java.util.logging.Level;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.kotlin.coroutines.Utils;

@Weave
public abstract class CoroutineExceptionHandlerKt {

	@Trace(dispatcher = true)
	public static void handleCoroutineException(kotlin.coroutines.CoroutineContext ctx,Throwable t) {
		Token token = Utils.getToken(ctx);
		if(token != null) {
			token.link();
		}
		NewRelic.getAgent().getLogger().log(Level.FINE, new Exception("Call to CoroutineExceptionHandlerKt" ), "Call to CoroutineExceptionHandlerKt.handleCoroutineException({0},{1}", ctx, t);
		Weaver.callOriginal();
	}
}
