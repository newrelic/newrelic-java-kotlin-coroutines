package kotlinx.coroutines.intrinsics;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.kotlin.coroutines_17.Utils;

import kotlin.coroutines.Continuation;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

@Weave
public abstract class CancellableKt {

	@Trace
	public static <T> void startCoroutineCancellable(Function1<? super Continuation<? super T>, ? extends java.lang.Object> f, Continuation<? super T> cont) {
		String continuationString = Utils.getContinuationString(cont);
		if(continuationString != null) {
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("Continuation", continuationString);
		}
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Suspend-Type", "Function1");
		Weaver.callOriginal();
	}

	@Trace
	public static <R, T> void startCoroutineCancellable(Function2<? super R, ? super Continuation<? super T>, ? extends java.lang.Object> f, R receiver, Continuation<? super T> cont, Function1<? super java.lang.Throwable, kotlin.Unit> onCancellation) {
		String continuationString = Utils.getContinuationString(cont);
		if(continuationString != null) {
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("Continuation", continuationString);
		}
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Suspend-Type", "Function2");
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Receiver", receiver.getClass().getName());

		Weaver.callOriginal();
	}

	@Trace
	public static void startCoroutineCancellable(Continuation<? super kotlin.Unit> completion, Continuation<?> cont) {
		String completionString = Utils.getContinuationString(completion);
		String continuationString = Utils.getContinuationString(cont);
		TracedMethod traced = NewRelic.getAgent().getTracedMethod();
		if(completionString != null) {
			traced.addCustomAttribute("Completion", completionString);
		}
		if(continuationString != null) {
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("Continuation", continuationString);
		}
		traced.addCustomAttribute("Suspend-Type", "None");
		Weaver.callOriginal();
	}

}
