package kotlinx.coroutines;

import java.util.logging.Level;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class DispatchedTask<T> {
	
	@Trace(async=true)
	public void run() {
		if(this instanceof CancellableContinuationImpl) {
			CancellableContinuationImpl<T> cancelable = (CancellableContinuationImpl<T>)this;
			NewRelic.getAgent().getLogger().log(Level.FINE, "call to CancellableContinuationImpl.run(), token: {0}, active: {1}, cancelled: {2}, completed: {3}", cancelable.token, cancelable.isActive(), cancelable.isCancelled(),cancelable.isCompleted());
			if(cancelable.token != null) {
				cancelable.token.link();
				if(!cancelable.isActive() || cancelable.isCancelled() || cancelable.isCompleted()) {
					cancelable.token.expire();
					cancelable.token = null;
				}
			}
		}
		Weaver.callOriginal();
	}

}
