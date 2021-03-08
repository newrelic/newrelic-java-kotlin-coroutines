package kotlinx.coroutines;

import java.util.logging.Level;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import kotlin.coroutines.Continuation;

@Weave
public abstract class CancellableContinuationImpl<T> extends DispatchedTask<T> {

	@NewField
	public Token token = null;

	public CancellableContinuationImpl(Continuation<? super T> c, int mode) {
		NewRelic.getAgent().getLogger().log(Level.FINE, new Exception("new CancellableContinuationImpl"), "created CancellableContinuationImpl with Continuation {0} on Thread: {1}",c, Thread.currentThread().getName());

	}

	public abstract boolean isActive();
	public abstract boolean isCompleted();
	public abstract boolean isCancelled();

	@Trace
	public void resumeUndispatched(kotlinx.coroutines.CoroutineDispatcher d, T t) {
		Weaver.callOriginal();
	}

	@Trace
	public void resumeUndispatchedWithException(kotlinx.coroutines.CoroutineDispatcher d, Throwable t) {
		NewRelic.noticeError(t);
		Weaver.callOriginal();
	}

}
