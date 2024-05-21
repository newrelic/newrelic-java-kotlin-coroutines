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

@Weave
public abstract class CancellableKt {

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Trace
	public static <T> void startCoroutineCancellable(Function1<? super Continuation<? super T>, ? extends java.lang.Object> f, Continuation<? super T> cont) {
		if(f instanceof SuspendFunction) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "In CancellableKt.startCoroutineCancellable, block is suspend function: {0} (class - {1})", f, f.getClass());
		}
		if(cont instanceof SuspendFunction) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "In CancellableKt.startCoroutineCancellable, cont is suspend function: {0} (class - {1})", cont, cont.getClass());
		}
		String continuationString = Utils.getContinuationString(cont);
		if(continuationString != null) {
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("Continuation", continuationString);
		}
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Suspend-Type", "Function1");
		if(!(f instanceof NRFunction1Wrapper)) {
			NRFunction1Wrapper wrapper = new NRFunction1Wrapper<>(f);
			f = wrapper;
		}
		Weaver.callOriginal();
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Trace
	public static <R, T> void startCoroutineCancellable(Function2<? super R, ? super Continuation<? super T>, ? extends java.lang.Object> f, R receiver, Continuation<? super T> cont, Function1<? super java.lang.Throwable, kotlin.Unit> onCancellation) {
		if(f instanceof SuspendFunction) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "In CancellableKt.startCoroutineCancellable, block is suspend function: {0} (class - {1})", f, f.getClass());
		}
		if(cont instanceof SuspendFunction) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "In CancellableKt.startCoroutineCancellable, cont is suspend function: {0} (class - {1})", cont, cont.getClass());
		}
		if(!(f instanceof NRFunction2Wrapper)) {
			NRFunction2Wrapper wrapper = new NRFunction2Wrapper<>(f, null);
			f = wrapper;
		}
		String continuationString = Utils.getContinuationString(cont);
		if(continuationString != null) {
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("Continuation", continuationString);
		}
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Suspend-Type", "Function2");
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Receiver", receiver.getClass().getName());

		if(!(onCancellation instanceof NRFunction1Wrapper)) {
			NRFunction1Wrapper wrapper = new NRFunction1Wrapper<>(onCancellation);
			onCancellation = wrapper;

		}
		Weaver.callOriginal();
	}

	@Trace
	public static void startCoroutineCancellable(Continuation<? super kotlin.Unit> completion, Continuation<?> cont) {
		if(completion instanceof SuspendFunction) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "In CancellableKt.startCoroutineCancellable, completion is suspend function: {0} (class - {1})", completion, completion.getClass());
		}
		if(cont instanceof SuspendFunction) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "In CancellableKt.startCoroutineCancellable, cont is suspend function: {0} (class - {1})", cont, cont.getClass());
		}
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		String completionString = Utils.getContinuationString(completion);
		if(completionString.startsWith("Continuation at")) {
			traced.addCustomAttribute("Completion", completion.toString());
		}
		String continuationString = Utils.getContinuationString(cont);
		if(continuationString != null) {
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("Continuation", continuationString);
		}
		traced.addCustomAttribute("Suspend-Type", "None");
		Weaver.callOriginal();
	}

}
