package kotlinx.coroutines;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.kotlin.coroutines.NRFunction2Wrapper;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;

@Weave
public class BuildersKt {

	@Trace
	public static final <T> T runBlocking(CoroutineContext context, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> f) {
		if(!(f instanceof NRFunction2Wrapper)) {
			NRFunction2Wrapper<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> wrapper = new NRFunction2Wrapper(f,f.getClass().getName());
			f = wrapper;
		}
		return Weaver.callOriginal();
	}
	
	@Trace
	public static final <T> Deferred<T> async(CoroutineScope scope, CoroutineContext context, CoroutineStart cStart, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> f) {
		if(!(f instanceof NRFunction2Wrapper)) {
			NRFunction2Wrapper<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> wrapper = new NRFunction2Wrapper(f,f.getClass().getName());
			f = wrapper;
		}
		return Weaver.callOriginal();
	}
	
	@Trace
	public static final <T> Object invoke(CoroutineDispatcher dispatcher, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> f, Continuation<? super T> c) {
		if(!(f instanceof NRFunction2Wrapper)) {
			NRFunction2Wrapper<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> wrapper = new NRFunction2Wrapper(f,f.getClass().getName());
			f = wrapper;
		}
		return Weaver.callOriginal();
	}
	
	@Trace
	public static final kotlinx.coroutines.Job launch(CoroutineScope scope, CoroutineContext context, CoroutineStart cStart, Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> f) {
		if(!(f instanceof NRFunction2Wrapper)) {
			NRFunction2Wrapper<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> wrapper = new NRFunction2Wrapper(f,f.getClass().getName());
			f = wrapper;
		}
		return Weaver.callOriginal();
	}
}
