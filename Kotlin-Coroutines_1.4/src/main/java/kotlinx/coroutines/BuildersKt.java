package kotlinx.coroutines;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.kotlin.coroutines.NRContinuationWrapper;
import com.newrelic.instrumentation.kotlin.coroutines.NRCoroutineToken;
import com.newrelic.instrumentation.kotlin.coroutines.NRFunction2Wrapper;
import com.newrelic.instrumentation.kotlin.coroutines.Utils;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.SuspendFunction;
import kotlin.jvm.functions.Function2;

@SuppressWarnings({ "unchecked", "rawtypes" })
@Weave
public class BuildersKt {

	@Trace(dispatcher = true)
	public static final <T> T runBlocking(CoroutineContext context, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block) {
		String name = Utils.getCoroutineName(context);
		if(name != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Builders","runBlocking",name);
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Builders","runBlocking");
		}
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Block", block.toString());
//		if (!Utils.ignoreSuspend(block.getClass(), block)) {
			if (!(block instanceof NRFunction2Wrapper)) {
				NRFunction2Wrapper<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> wrapper = new NRFunction2Wrapper(block, name);
				block = wrapper;
			} 
//		}
		T t = Weaver.callOriginal();
		return t;
	}

	@Trace(dispatcher = true)
	public static final <T> Deferred<T> async(CoroutineScope scope, CoroutineContext context, CoroutineStart cStart, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block) {
		String name = Utils.getCoroutineName(context);
		if(name == null) {
			name = Utils.getCoroutineName(scope.getCoroutineContext());
		}
		if(name != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Builders","async",name);
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Builders","async");
		}
		if(cStart != CoroutineStart.UNDISPATCHED && !Utils.ignoreSuspend(block.getClass(),block)) {

			NRCoroutineToken nrContextToken = Utils.setToken(context);
			if(nrContextToken != null) {
				context = context.plus(nrContextToken);
			}
			if(!(block instanceof NRFunction2Wrapper)) {
				NRFunction2Wrapper<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> wrapper = new NRFunction2Wrapper(block,name);
				block = wrapper;
			}
		}
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public static final <T> Object invoke(CoroutineDispatcher dispatcher, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block, Continuation<? super T> c) {

		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Continuation", c.toString());
		if(!Utils.ignoreSuspend(block.getClass(), block)) {
			if(!(block instanceof NRFunction2Wrapper)) {
				NRFunction2Wrapper<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> wrapper = new NRFunction2Wrapper(block,Utils.getCoroutineName(null, c));
				block = wrapper;
			}
		}
		if(c != null && !Utils.ignoreContinuation(c.getClass(), c)) {
			boolean isSuspend = c instanceof SuspendFunction;
			if(isSuspend) {
				if(!(Utils.ignoreSuspend(c.getClass(), c.toString()))) {
					NRContinuationWrapper wrapper = new NRContinuationWrapper<>(c, null);
					c = wrapper;
				}
			} else {
				NRContinuationWrapper wrapper = new NRContinuationWrapper<>(c, null);
				c = wrapper;
			}
		}
		Object t = Weaver.callOriginal();
		return t;
	}

	@Trace(dispatcher = true)
	public static final kotlinx.coroutines.Job launch(CoroutineScope scope, CoroutineContext context, CoroutineStart cStart, Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> block) {
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("CoroutineStart", cStart.name());
		String name = Utils.getCoroutineName(context);
		if(name == null) {
			name = Utils.getCoroutineName(scope.getCoroutineContext());
		}
		if(name != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Builders","launch",name);
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Builders","launch");
		}
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Block", block.toString());
		boolean check1 = Utils.ignoreSuspend(block.getClass(), block);
		if(!check1) {
			NRCoroutineToken nrContextToken = Utils.setToken(context);
			if(nrContextToken != null) {
				context = context.plus(nrContextToken);
			}
			if(!(block instanceof NRFunction2Wrapper)) {
				NRFunction2Wrapper<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> wrapper = new NRFunction2Wrapper(block,name);
				block = wrapper;
			}
		}
		Job j = Weaver.callOriginal();
		return j;
	}

	@Trace(dispatcher = true)
	public static final <T> Object withContext(CoroutineContext context,Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block, Continuation<? super T> completion) {
		String name = Utils.getCoroutineName(context,completion);
		if(name != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Builders","withContext",name);
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Builders","withContext");
		}
		if(completion != null) {
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("Completion", completion.toString());
		}
		if(!Utils.ignoreSuspend(block.getClass(),block)) {

			NRCoroutineToken nrContextToken = Utils.setToken(context);
			if(nrContextToken != null) {
				context = context.plus(nrContextToken);
			}
			if(!(block instanceof NRFunction2Wrapper)) {
				NRFunction2Wrapper<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> wrapper = new NRFunction2Wrapper(block,name);
				block = wrapper;
			}
		}
		if(completion != null && !Utils.ignoreContinuation(completion.getClass(), completion)) {
			if(!(completion instanceof NRContinuationWrapper)) {
				NRContinuationWrapper wrapper = new NRContinuationWrapper<>(completion, null);
				completion = wrapper;
			}
		}
		return Weaver.callOriginal();
	}
}