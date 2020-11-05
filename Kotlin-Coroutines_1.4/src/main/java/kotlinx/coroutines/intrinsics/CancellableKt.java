package kotlinx.coroutines.intrinsics;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

@Weave
public abstract class CancellableKt {

	@Trace
	public static <T> void startCoroutineCancellable(Function1<? super Continuation<? super T>, ? extends Object> f, Continuation<? super T> c) {
		Weaver.callOriginal();
	}

	@Trace
	public static final <R, T> void startCoroutineCancellable(Function2<? super R, ? super Continuation<? super T>, ? extends java.lang.Object> f, R r, Continuation<? super T> c, Function1<? super Throwable, Unit> f2) {
		Weaver.callOriginal();
	}

	@Trace
	public static final void startCoroutineCancellable(Continuation<? super Unit> c1, Continuation<?> c2) {
		Weaver.callOriginal();
	}

}
