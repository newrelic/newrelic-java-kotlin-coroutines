package kotlinx.coroutines;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.kotlin.coroutines.NRFunction1Wrapper;
import com.newrelic.instrumentation.kotlin.coroutines.NRFunction2Wrapper;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

@Weave(type=MatchType.BaseClass)
public abstract class AbstractCoroutine<T> {

	public AbstractCoroutine(CoroutineContext ctx, boolean active) {

	}

	public abstract String nameString$kotlinx_coroutines_core();

	@Trace
	public final void resumeWith(Object obj) {
		String name = nameString$kotlinx_coroutines_core();
		if(name != null && !name.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Coroutine",name,"resumeWith"});
		}
		Weaver.callOriginal();
	}

	@Trace
	public final void start(CoroutineStart start, Function1<? super Continuation<? super T>, ? extends Object> f) {
		String name = nameString$kotlinx_coroutines_core();
		if(name != null && !name.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Coroutine",name,"start"});
		}
//		NRFunction1Wrapper<? super Continuation<? super T>, ? extends Object> wrapper = new NRFunction1Wrapper(f,name);
//		f = wrapper;
		Weaver.callOriginal();
	}

	@Trace
	public final <R> void start(CoroutineStart start, R r, Function2<? super R, ? super Continuation<? super T>, ? extends Object> f) {
		String name = nameString$kotlinx_coroutines_core();
		if(name != null && !name.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Coroutine",name,"start"});
		}
//		NRFunction2Wrapper<? super R, ? super Continuation<? super T>, ? extends Object> wrapper = new NRFunction2Wrapper(f,name);
//		f = wrapper;
		Weaver.callOriginal();
	}

	@Trace
	protected void onCompleted(T t) {
		String name = nameString$kotlinx_coroutines_core();
		if(name != null && !name.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Coroutine",name,"onCompleted"});
		}
		Weaver.callOriginal();
	}

	@Trace
	public  void handleOnCompletionException$kotlinx_coroutines_core(java.lang.Throwable t) {
		String name = nameString$kotlinx_coroutines_core();
		if(name != null && !name.isEmpty()) {
			NewRelic.getAgent().getTracedMethod().setMetricName(new String[] {"Custom","Coroutine",name,"handleOnCompletionException"});
		}
		Weaver.callOriginal();
	}
}
