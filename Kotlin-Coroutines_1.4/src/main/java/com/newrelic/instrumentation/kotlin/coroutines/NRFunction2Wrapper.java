package com.newrelic.instrumentation.kotlin.coroutines;

import java.util.HashMap;
import java.util.logging.Level;

import com.newrelic.agent.bridge.AgentBridge;
import com.newrelic.api.agent.Logger;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;

public class NRFunction2Wrapper<P1, P2, R> implements Function2<P1, P2, R> {

	private Function2<P1, P2, R> delegate = null;
	private String name = null;
	private static boolean isTransformed = false;

	public NRFunction2Wrapper(Function2<P1, P2, R> d,String n) {
		NewRelic.getAgent().getLogger().log(Level.FINE, new Exception("In NRFunction2Wrapper.<init>"),  "Creating NRFunction2Wrapper using {0}, {1}, function class is {2}" , d, n, d.getClass());
		delegate = d;
		name = n;
		if(!isTransformed) {
			isTransformed = true;
			AgentBridge.instrumentation.retransformUninstrumentedClass(getClass());
		}
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
//	@Trace(async=true)
	public R invoke(P1 p1, P2 p2) {
		String nameStr = null;
		HashMap<String, Object> attributes = new HashMap<>();
//		Token token = null;
//		boolean linked = false;
		String suspendName = p2.getClass().getName();
		if (p2 instanceof Continuation) {
			Continuation continuation = (Continuation) p2;
			suspendName = Utils.getContinuationString(continuation);
//			
//			if(!Utils.ignoreSuspend(p2.getClass(), delegate))
//
//			if (!linked) {
//				token = Utils.getToken(continuation.getContext());
//				if (token != null) {
//					token.link();
//					linked = true;
//				}
//			}
//
//			if(suspendName == null) {
//				suspendName = p2.getClass().getName();
//			}
//
//			if (!Utils.ignoreContinuation(suspendName)) {
//
//				NRContinuationWrapper wrapper = new NRContinuationWrapper(continuation, suspendName);
//
//				p2 = (P2) wrapper;
//			}
		}
//		if (p1 instanceof CoroutineContext) {
//			CoroutineContext ctx = (CoroutineContext) p1;
//			nameStr = Utils.getCoroutineName(ctx);
//			token = Utils.getToken(ctx);
////			if (token != null) {
////				token.link();
////				linked = true;
////			}
//		}
//		if (p1 instanceof CoroutineScope) {
//			CoroutineScope scope = (CoroutineScope) p1;
//			nameStr = Utils.getCoroutineName(scope.getCoroutineContext());
//			if (token == null) {
//				token = Utils.getToken(scope.getCoroutineContext());
//			}
//		}
//		if(nameStr != null) {
//			NewRelic.getAgent().getTracedMethod().addCustomAttribute("Coroutine-Name", nameStr);
//		}
//
//
//		NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "WrappedSuspend", suspendName);
//		
//		logger.log(Level.FINE, "Finished processing NRFunction2Wrapper.invoke, linked = {3}, suspendName = {0}, namestring = {1}, name = {2}", suspendName, nameStr, name, linked);
//
//		if(!Utils.ignoreSuspend(p2.getClass(), suspendName)) {
			return NRWrappedSuspend.getInstance().invoke(p1, p2, delegate, attributes,"Custom", "WrappedSuspend", suspendName);
//		}
//		if(delegate != null) {
//			return delegate.invoke(p1, p2);
//		}
//		return null;
	}

	public void markUndispatched() {

	}

	@Override
	public String toString() {
		return delegate != null ? delegate.toString() : super.toString();
	}



}
