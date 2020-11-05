package kotlinx.coroutines.channels;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.selects.SelectClause2;

@Weave(type=MatchType.Interface)
public abstract class SendChannel<E> {

	@Trace
	public Object send(E e, Continuation<? super Unit> c) {
		return Weaver.callOriginal();
	}
	
	@Trace
	public boolean offer(E e) {
		return Weaver.callOriginal();
	}
	
	@Trace
	public SelectClause2<E, SendChannel<E>> getOnSend() {
		return Weaver.callOriginal();
	}
}
