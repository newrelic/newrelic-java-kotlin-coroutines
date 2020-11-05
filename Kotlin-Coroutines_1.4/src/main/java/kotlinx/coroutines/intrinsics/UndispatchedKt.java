package kotlinx.coroutines.intrinsics;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.AbstractCoroutine;
import kotlinx.coroutines.internal.ScopeCoroutine;

@Weave
public abstract class UndispatchedKt {

	@Trace
	public static <T> void startCoroutineUnintercepted(Function1<? super Continuation<? super T>, ? extends Object> f, Continuation<? super T> c) {
		Weaver.callOriginal();
	}

	@Trace
	public static <R, T> void startCoroutineUnintercepted(Function2<? super R, ? super Continuation<? super T>, ? extends Object> f, R r, Continuation<? super T> c) {
		Weaver.callOriginal();
	}

	@Trace
	public static <T> void startCoroutineUndispatched(Function1<? super Continuation<? super T>, ? extends Object> f, Continuation<? super T> c) {
		Weaver.callOriginal();
	}

	@Trace
	public static <R, T> void startCoroutineUndispatched(Function2<? super R, ? super Continuation<? super T>, ? extends Object> f, R r, Continuation<? super T> c) {
		Weaver.callOriginal();
	}

	@Trace
	public static final <T, R> Object startUndispatchedOrReturn(ScopeCoroutine<? super T> sCoroutine, R r, Function2<? super R, ? super Continuation<? super T>, ? extends Object> f) {
		return Weaver.callOriginal();
	}
	
	@Trace
	public static final <T, R> Object startUndispatchedOrReturnIgnoreTimeout(ScopeCoroutine<? super T> sc, R r, Function2<? super R, ? super Continuation<? super T>, ? extends Object> f) {
			return Weaver.callOriginal();
	}

}
