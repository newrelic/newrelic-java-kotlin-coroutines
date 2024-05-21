package kotlinx.coroutines.intrinsics;

import java.util.logging.Level;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.kotlin.coroutines.NRFunction1Wrapper;
import com.newrelic.instrumentation.kotlin.coroutines.NRFunction2Wrapper;
import com.newrelic.instrumentation.kotlin.coroutines.Utils;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.internal.ScopeCoroutine;

@Weave
public class UndispatchedKt {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Trace
	public static final <T> void startCoroutineUnintercepted(Function1<? super Continuation<? super T>, ? extends Object> f, Continuation<? super T> cont) {
		if(f instanceof SuspendFunction) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "In UndispatchedKt.startCoroutineUnintercepted block is suspend function: {0} (class - {1})", f, f.getClass());
		}
		if(cont instanceof SuspendFunction) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "In UndispatchedKt.startCoroutineUnintercepted, cont is suspend function: {0} (class - {1})", cont, cont.getClass());
		}
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("Suspend-Type", "Function1");
		String continuationString = Utils.getContinuationString(cont);
		if(continuationString != null) {
			traced.addCustomAttribute("Continuation", continuationString);
		}
		if(!(f instanceof NRFunction1Wrapper)) {
			NRFunction1Wrapper wrapper = new NRFunction1Wrapper<>(f);
			f = wrapper;
		}
		Weaver.callOriginal();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Trace
	public static final <R, T> void startCoroutineUnintercepted(Function2<? super R, ? super Continuation<? super T>, ? extends Object> f, R receiver, Continuation<? super T> cont) {
		if(f instanceof SuspendFunction) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "In UndispatchedKt.startCoroutineUnintercepted block is suspend function: {0} (class - {1})", f, f.getClass());
		}
		if(cont instanceof SuspendFunction) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "In UndispatchedKt.startCoroutineUnintercepted, cont is suspend function: {0} (class - {1})", cont, cont.getClass());
		}
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("Suspend-Type", "Function2");
		String continuationString = Utils.getContinuationString(cont);
		if(continuationString != null) {
			traced.addCustomAttribute("Continuation", continuationString);
		}
		traced.addCustomAttribute("Receiver", receiver.getClass().getName());
		if(!(f instanceof NRFunction2Wrapper)) {
			NRFunction2Wrapper wrapper = new NRFunction2Wrapper<>(f, null);
			f = wrapper;
		}
		Weaver.callOriginal();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Trace
	public static final <T> void startCoroutineUndispatched(Function1<? super Continuation<? super T>, ? extends Object> f, Continuation<? super T> cont) {
		if(f instanceof SuspendFunction) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "InUndispatchedKt.startCoroutineUndispatched block is suspend function: {0} (class - {1})", f, f.getClass());
		}
		if(cont instanceof SuspendFunction) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "In UndispatchedKt.startCoroutineUndispatched, cont is suspend function: {0} (class - {1})", cont, cont.getClass());
		}
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("Suspend-Type", "Function1");
		String continuationString = Utils.getContinuationString(cont);
		if(continuationString != null) {
			traced.addCustomAttribute("Continuation", continuationString);
		}
		if(!(f instanceof NRFunction1Wrapper)) {
			NRFunction1Wrapper wrapper = new NRFunction1Wrapper<>(f);
			f = wrapper;
		}
		Weaver.callOriginal();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Trace
	public static final <R, T> void startCoroutineUndispatched(Function2<? super R, ? super Continuation<? super T>, ? extends Object> f, R receiver, Continuation<? super T> cont) {
		if(f instanceof SuspendFunction) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "InUndispatchedKt.startCoroutineUndispatched block is suspend function: {0} (class - {1})", f, f.getClass());
		}
		if(cont instanceof SuspendFunction) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "In UndispatchedKt.startCoroutineUndispatched, cont is suspend function: {0} (class - {1})", cont, cont.getClass());
		}
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("Suspend-Type", "Function2");
		String continuationString = Utils.getContinuationString(cont);
		if(continuationString != null) {
			traced.addCustomAttribute("Continuation", continuationString);
		}
		traced.addCustomAttribute("Receiver", receiver.getClass().getName());
		if(!(f instanceof NRFunction2Wrapper)) {
			NRFunction2Wrapper wrapper = new NRFunction2Wrapper<>(f, null);
			f = wrapper;
		}
		Weaver.callOriginal();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Trace
	public static final <T, R> Object startUndispatchedOrReturn(ScopeCoroutine<? super T> scope, R receiver, Function2<? super R, ? super Continuation<? super T>, ? extends Object> f) {
		if(f instanceof SuspendFunction) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "InUndispatchedKt.startUndispatchedOrReturn block is suspend function: {0} (class - {1})", f, f.getClass());
		}
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("Suspend-Type", "Function2");
		traced.addCustomAttribute("Receiver", receiver.getClass().getName());
		if(!(f instanceof NRFunction2Wrapper)) {
			NRFunction2Wrapper wrapper = new NRFunction2Wrapper<>(f, null);
			f = wrapper;
		}
		return Weaver.callOriginal();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Trace
	public static final <T, R> Object startUndispatchedOrReturnIgnoreTimeout(ScopeCoroutine<? super T> scope, R receiver, Function2<? super R, ? super Continuation<? super T>, ? extends Object> f) {
		if(f instanceof SuspendFunction) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "InUndispatchedKt.startUndispatchedOrReturnIgnoreTimeout block is suspend function: {0} (class - {1})", f, f.getClass());
		}
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		traced.addCustomAttribute("Suspend-Type", "Function2");
		traced.addCustomAttribute("Receiver", receiver.getClass().getName());
		if(!(f instanceof NRFunction2Wrapper)) {
			NRFunction2Wrapper wrapper = new NRFunction2Wrapper<>(f, null);
			f = wrapper;
		}
		return Weaver.callOriginal();
	}

}
