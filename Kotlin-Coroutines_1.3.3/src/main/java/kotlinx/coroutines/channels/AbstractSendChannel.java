package kotlinx.coroutines.channels;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.selects.SelectClause2;
import kotlinx.coroutines.selects.SelectInstance;

@Weave(type=MatchType.BaseClass)
public abstract class AbstractSendChannel<E> {

	@Trace
	public Object send(E e, Continuation<? super Unit> c) {
		return Weaver.callOriginal();
	}
	
	@Trace
	protected Object offerInternal(Object element) {
		return Weaver.callOriginal();
	}
	
	@SuppressWarnings("rawtypes")
	@Trace
	protected Object offerSelectInternal(Object element, SelectInstance select) {
		return Weaver.callOriginal();
	}
	
	@Trace
	public SelectClause2<E, AbstractSendChannel<E>> getOnSend() {
		return Weaver.callOriginal();
	}
}
