package kotlinx.coroutines;

import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;
import com.newrelic.api.agent.Trace;
import com.newrelic.api.agent.weaver.Weave;
import com.newrelic.api.agent.weaver.Weaver;
import com.newrelic.instrumentation.kotlin.coroutines_15.NRCoroutineToken;
import com.newrelic.instrumentation.kotlin.coroutines_15.Utils;

import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.jvm.functions.Function2;

@Weave
public class BuildersKt {

	@Trace(dispatcher = true)
	public static final <T> T runBlocking(CoroutineContext context, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block) {
		Token token = Utils.getToken(context);
		if(token != null) {
			token.link();
		} else {
			NRCoroutineToken nrContextToken = Utils.setToken(context);
			if(nrContextToken != null) {
				context = context.plus(nrContextToken);
			}
		}
		String name = Utils.getCoroutineName(context);
		if(name != null) {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Builders","runBlocking",name);
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("CoroutineName", name);
		} else {
			NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Builders","runBlocking");
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("CoroutineName", "Could not determine");
		}
		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Block", block.toString());

		T t = Weaver.callOriginal();
		return t;
	}

	@Trace(dispatcher = true)
	public static final <T> Deferred<T> async(CoroutineScope scope, CoroutineContext context, CoroutineStart cStart, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block) {
		if (!Utils.ignoreScope(scope)) {
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("CoroutineStart", cStart.name());
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("CoroutineScope-Class", scope.getClass().getName());
			String name = Utils.getCoroutineName(context);
			if(name == null) {
				name = Utils.getCoroutineName(scope.getCoroutineContext());
			}
			if(name != null) {
				NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Builders","async",name);
				NewRelic.getAgent().getTracedMethod().addCustomAttribute("CoroutineName", name);
			} else {
				NewRelic.getAgent().getTracedMethod().setMetricName("Custom","Builders","async");
				NewRelic.getAgent().getTracedMethod().addCustomAttribute("CoroutineName", "Could not determine");
			}

			Token token = Utils.getToken(context);
			if(token != null) {
				token.link();
			} else {
				NRCoroutineToken nrContextToken = Utils.setToken(context);
				if(nrContextToken != null) {
					context = context.plus(nrContextToken);
				}
			}
		} else {
			NewRelic.getAgent().getTransaction().ignore();
		}
		return Weaver.callOriginal();
	}

	@Trace(dispatcher = true)
	public static final <T> Object invoke(CoroutineDispatcher dispatcher, Function2<? super CoroutineScope, ? super Continuation<? super T>, ? extends Object> block, Continuation<? super T> c) {

		NewRelic.getAgent().getTracedMethod().addCustomAttribute("Continuation", c.toString());
		Object t = Weaver.callOriginal();
		return t;
	}

	@Trace(dispatcher = true)
	public static final kotlinx.coroutines.Job launch(CoroutineScope scope, CoroutineContext context, CoroutineStart cStart, Function2<? super CoroutineScope, ? super Continuation<? super Unit>, ? extends Object> block) {
		if (!Utils.ignoreScope(scope)) {
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("CoroutineStart", cStart.name());
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("CoroutineScope-Class", scope.getClass().getName());
			
			String name = Utils.getCoroutineName(context);
			if (name == null) {
				name = Utils.getCoroutineName(scope.getCoroutineContext());
			}
			if (name != null) {
				NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Builders", "launch", name);
				NewRelic.getAgent().getTracedMethod().addCustomAttribute("CoroutineName", name);
			} else {
				NewRelic.getAgent().getTracedMethod().setMetricName("Custom", "Builders", "launch");
				NewRelic.getAgent().getTracedMethod().addCustomAttribute("CoroutineName", "Could not determine");
			}
			NewRelic.getAgent().getTracedMethod().addCustomAttribute("Block", block.toString());
			Token token = Utils.getToken(context);
			if(token != null) {
				token.link();
			} else {
				NRCoroutineToken nrContextToken = Utils.setToken(context);
				if(nrContextToken != null) {
					context = context.plus(nrContextToken);
				}
			}
		} else {
			NewRelic.getAgent().getTransaction().ignore();
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

		Token token = Utils.getToken(context);
		if(token != null) {
			token.link();
		} else {
			NRCoroutineToken nrContextToken = Utils.setToken(context);
			if(nrContextToken != null) {
				context = context.plus(nrContextToken);
			}
		}
		return Weaver.callOriginal();
	}
}