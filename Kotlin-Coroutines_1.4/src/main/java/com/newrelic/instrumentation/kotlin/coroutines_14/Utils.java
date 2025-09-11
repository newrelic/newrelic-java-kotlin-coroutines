package com.newrelic.instrumentation.kotlin.coroutines_14;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.newrelic.agent.config.AgentConfig;
import com.newrelic.agent.config.AgentConfigListener;
import com.newrelic.agent.service.ServiceFactory;
import com.newrelic.api.agent.Config;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlinx.coroutines.AbstractCoroutine;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DispatchedTask;

public class Utils implements AgentConfigListener {

	private static final List<String> ignoredContinuations = new ArrayList<String>();
	private static final List<String> ignoredScopes = new ArrayList<>();
	private static final String CONTIGNORECONFIG = "Coroutines.ignores.continuations";
	private static final String SCOPESIGNORECONFIG = "Coroutines.ignores.scopes";
	private static final String DISPATCHEDIGNORECONFIG = "Coroutines.ignores.dispatched";
	private static final String DELAYED_ENABLED_CONFIG = "Coroutines.delayed.enabled";

	public static final String CREATEMETHOD1 = "Continuation at kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$4";
	public static final String CREATEMETHOD2 = "Continuation at kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$3";
	private static final Utils INSTANCE = new Utils();
	private static final String CONT_LOC = "Continuation at";
	public static boolean DELAYED_ENABLED = true;

	static {
		ServiceFactory.getConfigService().addIAgentConfigListener(INSTANCE);
		Config config = NewRelic.getAgent().getConfig();
		loadConfig(config);
		ignoredContinuations.add(CREATEMETHOD1);
		ignoredContinuations.add(CREATEMETHOD2);
		Object value = config.getValue(DELAYED_ENABLED_CONFIG);
		if(value != null) {
			if(value instanceof Boolean) {
				DELAYED_ENABLED = (Boolean)value;
			} else {
				DELAYED_ENABLED = Boolean.valueOf(value.toString());
			}
		}

	}

	public static NRRunnable getRunnableWrapper(Runnable r) {
		if(r instanceof NRRunnable) {
			return null;
		}
		if(r instanceof DispatchedTask) {
			DispatchedTask<?> task = (DispatchedTask<?>)r;
			Continuation<?> cont = task.getDelegate$kotlinx_coroutines_core();
			if(cont != null) {
				String cont_string = getContinuationString(cont);
				if(cont_string != null && DispatchedTaskIgnores.ignoreDispatchedTask(cont_string)) {
					return null;
				}
			}
		}

		Token t = NewRelic.getAgent().getTransaction().getToken();
		if(t != null && t.isActive()) {
			return new NRRunnable(r, t);
		} else if(t != null) {
			t.expire();
			t = null;
		}
		return null;
	}

	private static void loadConfig(Config config) {
		String ignores = config.getValue(CONTIGNORECONFIG);
		NewRelic.getAgent().getLogger().log(Level.FINE, "Value of {0}: {1}", CONTIGNORECONFIG, ignores);
		if (ignores != null && !ignores.isEmpty()) {
			ignoredContinuations.clear();
			String[] ignoresList = ignores.split(",");

			for(String ignore : ignoresList) {
				if (!ignoredContinuations.contains(ignore)) {
					ignoredContinuations.add(ignore);
					NewRelic.getAgent().getLogger().log(Level.FINE, "Will ignore Continuations named {0}", ignore);
				}
			}
		} else if(!ignoredContinuations.isEmpty()) {
			ignoredContinuations.clear();
		}
		ignores = config.getValue(DISPATCHEDIGNORECONFIG);
		NewRelic.getAgent().getLogger().log(Level.FINE, "Value of {0}: {1}", DISPATCHEDIGNORECONFIG, ignores);
		DispatchedTaskIgnores.reset();
		if (ignores != null && !ignores.isEmpty()) {
			DispatchedTaskIgnores.configure(ignores);
		}
		ignores = config.getValue(SCOPESIGNORECONFIG);
		if (ignores != null && !ignores.isEmpty()) {
			ignoredScopes.clear();
			String[] ignoresList = ignores.split(",");

			for(String ignore : ignoresList) {
				if (!ignoredScopes.contains(ignore)) {
					ignoredScopes.add(ignore);
					NewRelic.getAgent().getLogger().log(Level.FINE, "Will ignore CoroutineScopes named {0}", ignore);
				}
			}
		} else if(!ignoredScopes.isEmpty()) {
			ignoredScopes.clear();
		}

	}

	public static boolean ignoreScope(CoroutineScope scope) {
		CoroutineContext ctx = scope.getCoroutineContext();
		String name = getCoroutineName(ctx);
		String className = scope.getClass().getName();
		return ignoreScope(className) || ignoreScope(name);
	}

	public static boolean ignoreScope(String coroutineScope) {
		return ignoredScopes.contains(coroutineScope);
	}

	public static boolean ignoreContinuation(String cont_string) {
		return ignoredContinuations.contains(cont_string);
	}

	/*
	 * Allows certain Coroutine scopes to be ignored
	 * coroutineScope can be a Coroutine name or CoroutineScope class name
	 */
	public static boolean continueWithScope(CoroutineScope scope) {
		CoroutineContext ctx = scope.getCoroutineContext();
		String name = getCoroutineName(ctx);
		String className = scope.getClass().getName();
		return continueWithScope(className) && continueWithScope(name);
	}

	/*
	 * Allows certain Coroutine scopes to be ignored
	 * coroutineScope can be a Coroutine name or CoroutineScope class name
	 */
	public static boolean continueWithScope(String coroutineScope) {
		for(String ignoredScope : ignoredScopes) {
			if(coroutineScope.matches(ignoredScope)) {
				return false;
			}
		}
		return !ignoredScopes.contains(coroutineScope);
	}

	public static boolean continueWithContinuation(Continuation<?> continuation) {
		/*
		 *	Don't trace internal Coroutines Continuations
		 */
		String className = continuation.getClass().getName();
		if(className.startsWith("kotlin")) return false;

		/*
		 * Get the continuation string and check if it should be ignored
		 */
		String cont_string = getContinuationString(continuation);
		if(cont_string == null) { return false; }

		for(String ignored : ignoredContinuations) {
			if(cont_string.matches(ignored)) {
				return false;
			}
		}
		return !ignoredContinuations.contains(cont_string);
	}



	public static String sub = "createCoroutineFromSuspendFunction";

	public static void setToken(CoroutineContext context) {
		TokenContext tokenContext = NRTokenContextKt.getTokenContextOrNull(context);
		if (tokenContext == null) {
			Token t = NewRelic.getAgent().getTransaction().getToken();
			if(t != null && t.isActive()) {
				NRTokenContextKt.addTokenContext(context, t);
			} else if(t != null) {
				t.expire();
				t = null;
			}
		}
	}

	public static Token getToken(CoroutineContext context) {
		TokenContext tokenContext = NRTokenContextKt.getTokenContextOrNull(context);
		if(tokenContext != null) {
			return tokenContext.getToken();
		}
		return null;
	}

	public static void expireToken(CoroutineContext context) {
		TokenContext tokenContext = NRTokenContextKt.getTokenContextOrNull(context);
		if(tokenContext != null) {
			Token token = tokenContext.getToken();
			token.expire();
			NRTokenContextKt.removeTokenContext(context);
		}
	}

	public static <T> String getCoroutineName(CoroutineContext context, Continuation<T> continuation) {
		if(continuation instanceof AbstractCoroutine) {
			return ((AbstractCoroutine<T>)continuation).nameString$kotlinx_coroutines_core();
		}
		if(continuation instanceof BaseContinuationImpl) {
			return ((BaseContinuationImpl)continuation).toString();
		}
		return null;
	}

	public static String getCoroutineName(CoroutineContext context) {
		return CoroutineNameUtilsKt.getCoroutineName(context);
	}

	@Override
	public void configChanged(String appName, AgentConfig agentConfig) {
		loadConfig(agentConfig);
		Object value = agentConfig.getValue(DELAYED_ENABLED_CONFIG);
		if(value != null) {
			if(value instanceof Boolean) {
				DELAYED_ENABLED = (Boolean)value;
			} else {
				DELAYED_ENABLED = Boolean.valueOf(value.toString());
			}
		}
	}

	public static <T> String getContinuationString(Continuation<T> continuation) {
		String contString = continuation.toString();

		if(contString.equals(CREATEMETHOD1) || contString.equals(CREATEMETHOD2)) {
			return sub;
		}

		if(contString.startsWith(CONT_LOC)) {
			return contString;
		}

		if(continuation instanceof AbstractCoroutine) {
			return ((AbstractCoroutine<?>)continuation).nameString$kotlinx_coroutines_core();
		}

		int index = contString.indexOf('@');
		if(index > -1) {
			return contString.substring(0, index);
		}

		return null;
	}
}
