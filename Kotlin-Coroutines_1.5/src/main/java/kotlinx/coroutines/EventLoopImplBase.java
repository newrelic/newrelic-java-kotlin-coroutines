package kotlinx.coroutines;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;

@Weave
public abstract class EventLoopImplBase {

	@Weave
	static class DelayedResumeTask {

		@NewField
		private Segment segment = null;

		public DelayedResumeTask(long nanos, kotlinx.coroutines.CancellableContinuation<? super kotlin.Unit> cont) {
			segment = NewRelic.getAgent().getTransaction().startSegment("DelayedTask");

		}

		@Trace(async = true)
		public void run() {
			if(segment != null) {
				segment.end();
				segment = null;
			}
			Weaver.callOriginal();
		}
	}

	@Weave
	static class DelayedRunnableTask {

		@NewField
		private Segment segment = null;

		public DelayedRunnableTask(long nanos, Runnable r) {
			segment = NewRelic.getAgent().getTransaction().startSegment("DelayedRunnable");

		}

		@Trace(async = true)
		public void run() {
			if(segment != null) {
				segment.end();
				segment = null;
			}
			Weaver.callOriginal();
		}
	}

}
