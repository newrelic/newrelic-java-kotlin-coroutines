package kotlinx.coroutines.intrinsics;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.kotlin.coroutines_15.Utils;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.internal.ScopeCoroutine;

@Weave
public class UndispatchedKt {

	@Trace
	public static final <T> void startCoroutineUnintercepted(Function1<? super Continuation<? super T>, ? extends Object> f, Continuation<? super T> cont) {
		String continuationString = Utils.getContinuationString(cont);
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("Suspend-Type", "Function1");
		if(continuationString != null) {
			traced.addCustomAttribute("Continuation", continuationString);
		}
		Weaver.callOriginal();
	}

	@Trace
	public static final <R, T> void startCoroutineUnintercepted(Function2<? super R, ? super Continuation<? super T>, ? extends Object> f, R receiver, Continuation<? super T> cont) {
		String continuationString = Utils.getContinuationString(cont);
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("Suspend-Type", "Function2");
		if(continuationString != null) {
			traced.addCustomAttribute("Continuation", continuationString);
		}
		traced.addCustomAttribute("Receiver", receiver.getClass().getName());
		Weaver.callOriginal();
	}

	@Trace
	public static final <T> void startCoroutineUndispatched(Function1<? super Continuation<? super T>, ? extends Object> f, Continuation<? super T> cont) {
		String continuationString = Utils.getContinuationString(cont);
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("Suspend-Type", "Function1");
		if(continuationString != null) {
			traced.addCustomAttribute("Continuation", continuationString);
		}
		Weaver.callOriginal();
	}

	@Trace
	public static final <R, T> void startCoroutineUndispatched(Function2<? super R, ? super Continuation<? super T>, ? extends Object> f, R receiver, Continuation<? super T> cont) {
		String continuationString = Utils.getContinuationString(cont);
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("Suspend-Type", "Function2");
		if(continuationString != null) {
			traced.addCustomAttribute("Continuation", continuationString);
		}
		traced.addCustomAttribute("Receiver", receiver.getClass().getName());
		Weaver.callOriginal();
	}

	@Trace
	public static final <T, R> Object startUndispatchedOrReturn(ScopeCoroutine<? super T> scope, R receiver, Function2<? super R, ? super Continuation<? super T>, ? extends Object> f) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("Suspend-Type", "Function2");
		traced.addCustomAttribute("Receiver", receiver.getClass().getName());
		return Weaver.callOriginal();
	}

	@Trace
	public static final <T, R> Object startUndispatchedOrReturnIgnoreTimeout(ScopeCoroutine<? super T> scope, R receiver, Function2<? super R, ? super Continuation<? super T>, ? extends Object> f) {
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("Suspend-Type", "Function2");
		traced.addCustomAttribute("Receiver", receiver.getClass().getName());
		return Weaver.callOriginal();
	}

}
