package kotlinx.coroutines;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.coroutines.Continuation;

@Weave
public abstract class CoroutineStart {

	public static final CoroutineStart DEFAULT = Weaver.callOriginal();
	public static final CoroutineStart LAZY = Weaver.callOriginal();
	public static final CoroutineStart ATOMIC = Weaver.callOriginal();
	public static final CoroutineStart UNDISPATCHED = Weaver.callOriginal();
	
	@Trace
	public final <T> void invoke(Function1<? super Continuation<? super T>, ? extends Object> f, Continuation<? super T> c) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Coroutines","CoroutineStart","invoke","F1",getType());
		Weaver.callOriginal();
	}
	
	@Trace
	public final <R, T> void invoke(Function2<? super R, ? super Continuation<? super T>, ? extends java.lang.Object> f, R r, Continuation<? super T> c) {
		NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Coroutines","CoroutineStart","invoke","F2",getType());
		Weaver.callOriginal();
	}
	
	
	private String getType() {
		if(this == DEFAULT) {
			return "Default";
		}
		if(this == ATOMIC) {
			return "Atomic";
		}
		if(this == UNDISPATCHED) {
			return "Undispatched";
		}
		if(this == LAZY) {
			return "Lazy";
		}
		return "Unknown";
	}
}
