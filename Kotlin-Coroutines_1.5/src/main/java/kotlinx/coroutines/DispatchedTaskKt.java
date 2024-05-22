package kotlinx.coroutines;

import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class DispatchedTaskKt {

	@Trace
	public static <T> void dispatch(kotlinx.coroutines.DispatchedTask<? super T> task, int r) {
		Weaver.callOriginal();
	}

	@Trace
	public static final <T> void resume(kotlinx.coroutines.DispatchedTask<? super T> task, kotlin.coroutines.Continuation<? super T> cont, boolean b) {
		Weaver.callOriginal();
	}

	@Trace
	public static final void runUnconfinedEventLoop(kotlinx.coroutines.DispatchedTask<?> task, kotlinx.coroutines.EventLoop eventLoop, kotlin.jvm.functions.Function0<kotlin.Unit> f) {
		Weaver.callOriginal();
	}

	@Trace
	public static final void resumeWithStackTrace(kotlin.coroutines.Continuation<?> cont, java.lang.Throwable t) {
		Weaver.callOriginal();
	}

}
