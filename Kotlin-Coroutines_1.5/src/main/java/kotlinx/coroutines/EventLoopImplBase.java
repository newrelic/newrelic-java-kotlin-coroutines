package kotlinx.coroutines;

import com.newrelic.agent.tracers.Tracer;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Segment;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.TracedMethod;
import com.newrelic.api.agent.Transaction;
import com.newrelic.api.agent.weaver.MatchType;
import com.newrelic.api.agent.weaver.NewField;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.kotlin.coroutines_15.Utils;

@Weave
public abstract class EventLoopImplBase {
	
	@Weave(type = MatchType.BaseClass) 
	public static abstract class DelayedTask {
		
		public DelayedTask(long nanos) {
			if(Utils.DELAYED_ENABLED) {
				segment = NewRelic.getAgent().getTransaction().startSegment("Custom/Kotlin/Coroutines/" + getClass().getSimpleName());
				token = NewRelic.getAgent().getTransaction().getToken();
			}
		}
		
		@NewField
		protected Segment segment = null;
		
		@NewField
		protected Token token = null;

		public void dispose() {
			if(segment != null) {
				segment.ignore();
				segment = null;
			}
			if(token != null) {
				token.expire();
				token = null;
			}
			Weaver.callOriginal();
		}
	}
	
	@Weave
	static class DelayedResumeTask extends DelayedTask {


		public DelayedResumeTask(long nanos, kotlinx.coroutines.CancellableContinuation<? super kotlin.Unit> cont) {
			super(nanos);
		}

		@Trace(async = true)
		public void run() {
			if(segment != null) {
				Transaction txn = segment.getTransaction();
				if(txn != null) {
					TracedMethod traced = txn.getTracedMethod();
					if(traced != null)  {
						if(traced instanceof Tracer) {
							Tracer tracer = (Tracer)traced;
							if(tracer.getParentTracer() == null) NewRelic.incrementCounter("NRLabs/Kotlin/DelayedResumeTask/NullParent");
							if(tracer.getTransactionActivity() == null) NewRelic.incrementCounter("NRLabs/Kotlin/DelayedResumeTask/NullTransactionActivity");							
						}
					}
				}
				segment.end();
				segment = null;
			}
			if(token != null) {
				token.linkAndExpire();
				token = null;
			}
			Weaver.callOriginal();
		}
	}

	@Weave
	static class DelayedRunnableTask extends DelayedTask {

		public DelayedRunnableTask(long nanos, Runnable r) {
			super(nanos);
		}

		@Trace(async = true)
		public void run() {
			if(segment != null) {
				segment.end();
				segment = null;
			}
			if(token != null) {
				token.linkAndExpire();
				token = null;
			}
			Weaver.callOriginal();
		}
	}

	@Weave
	public static class DelayedTaskQueue {
		
	}
}
