package kotlinx.coroutines;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.nr.instrumentation.kotlin.coroutines.NRFunction1;
import com.nr.instrumentation.kotlin.coroutines.NRFunction2;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

@SuppressWarnings({"rawtypes", "unchecked"})
@Weave(type=MatchType.BaseClass)
public abstract class AbstractCoroutine<T> implements Continuation<T> {

	public AbstractCoroutine(CoroutineContext ctx, boolean active) {

	}

	public abstract String nameString$kotlinx_coroutines_core();

	@NewField
	private Token token = null;

	@Trace(async=true)
	public final void resumeWith(Object obj) {
		String name = nameString$kotlinx_coroutines_core();
		if(name != null && !name.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Coroutine",name,"resumeWith"});
		}
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public final void start(CoroutineStart start, Function1<? super Continuation<? super T>, ? extends Object> f) {
		if(!(f instanceof NRFunction1)) {
			NRFunction1 wrapper = new NRFunction1(f);
			f = wrapper;
		}
		String name = nameString$kotlinx_coroutines_core();
		if(name != null && !name.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Coroutine",name,"start"});
		}
		Weaver.callOriginal();
	}

	@Trace(dispatcher=true)
	public final <R> void start(CoroutineStart start, R r, Function2<? super R, ? super Continuation<? super T>, ? extends Object> f) {
		if(!(f instanceof NRFunction2)) {
			NRFunction2 wrapper = new NRFunction2(f);
			f = wrapper;
		}
		String name = nameString$kotlinx_coroutines_core();
		if(name != null && !name.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Coroutine",name,"start"});
		}
		Weaver.callOriginal();
	}

	@Trace(async=true)
	protected void onCompleted(T t) {
		String name = nameString$kotlinx_coroutines_core();
		if(name != null && !name.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Coroutine",name,"onCompleted"});
		}
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}

	@Trace(async=true)
	public  void handleOnCompletionException$kotlinx_coroutines_core(java.lang.Throwable t) {
		String name = nameString$kotlinx_coroutines_core();
		if(name != null && !name.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Coroutine",name,"handleOnCompletionException"});
		}
		if(token != null) {
			token.linkAndExpire();
			token = null;
		}
		Weaver.callOriginal();
	}
}
