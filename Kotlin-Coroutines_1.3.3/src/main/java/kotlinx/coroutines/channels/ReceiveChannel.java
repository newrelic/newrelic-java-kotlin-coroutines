package kotlinx.coroutines.channels;

import java.util.concurrent.CancellationException;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import kotlin.coroutines.Continuation;
import kotlinx.coroutines.selects.SelectClause1;

@Weave(type=MatchType.Interface)
public abstract class ReceiveChannel<E> {

	@Trace
	public Object receive(Continuation<? super E> c) {
		return Weaver.callOriginal();
	}

	@Trace
	public abstract SelectClause1<E> getOnReceive();
	
	@Trace
	public abstract Object receiveOrNull(Continuation<? super E> c);
	
	@Trace
	public abstract SelectClause1<E> getOnReceiveOrNull();
	
	@Trace
	public abstract E poll();
	
	@Trace
	public abstract void cancel(CancellationException t);
	

}
