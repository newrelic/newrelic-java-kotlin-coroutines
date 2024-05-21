package com.newrelic.instrumentation.kotlin.coroutines;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.newrelic.agent.config.AgentConfig;
import com.newrelic.agent.config.AgentConfigListener;
import com.newrelic.agent.service.ServiceFactory;
import com.newrelic.api.agent.Config;
import com.newrelic.api.agent.NewRelic;
import com.newrelic.api.agent.Token;

import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.jvm.internal.BaseContinuationImpl;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.AbstractCoroutine;
import kotlinx.coroutines.CoroutineName;

public class Utils implements AgentConfigListener {

	private static final List<String> ignoredSuspends = new ArrayList<String>();
	private static final List<String> ignoredContinuations = new ArrayList<String>();
	private static final List<String> ignoredDispatchs = new ArrayList<String>();
	private static final String SUSPENDSIGNORECONFIG = "Coroutines.ignores.suspends";
	private static final String CONTIGNORECONFIG = "Coroutines.ignores.continuations";
	private static final String DISPATCHEDIGNORECONFIG = "Coroutines.ignores.dispatched";
	
	public static final String CREATEMETHOD1 = "Continuation at kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$4";
	public static final String CREATEMETHOD2 = "Continuation at kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$3";
	private static final Utils INSTANCE = new Utils();
	private static final String CONT_LOC = "Continuation at";
	

	static {
		ServiceFactory.getConfigService().addIAgentConfigListener(INSTANCE);
		Config config = NewRelic.getAgent().getConfig();
		loadConfig(config);
		ignoredSuspends.add(CREATEMETHOD1);
		ignoredSuspends.add(CREATEMETHOD2);
		ignoredContinuations.add(CREATEMETHOD1);
		ignoredContinuations.add(CREATEMETHOD2);
		
	}
	
	public static NRRunnable getRunnableWrapper(Runnable r) {
		if(r instanceof NRRunnable) {
			return null;
		}
//		if(r instanceof DispatchedTask) {
//			return null;
//		}
		
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
		String ignores = config.getValue(SUSPENDSIGNORECONFIG);
		if (ignores != null && !ignores.isEmpty()) {
			StringTokenizer st = new StringTokenizer(ignores, ",");
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				if (token != null && !token.isEmpty()) {
					ignoredSuspends.add(token);
				}
			} 
		}
		ignores = config.getValue(CONTIGNORECONFIG);
		if (ignores != null && !ignores.isEmpty()) {
			StringTokenizer st = new StringTokenizer(ignores, ",");
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				if (token != null && !token.isEmpty()) {
					ignoredContinuations.add(token);
				}
			} 
		}
		ignores = config.getValue(DISPATCHEDIGNORECONFIG);
		if (ignores != null && !ignores.isEmpty()) {
			StringTokenizer st = new StringTokenizer(ignores, ",");
			while (st.hasMoreTokens()) {
				String token = st.nextToken();
				if (token != null && !token.isEmpty()) {
					ignoredDispatchs.add(token);
				}
			} 
		}
	}
	
	public static boolean ignoreContinuation(String name) {
		return ignoredContinuations.contains(name);
	}
	
	public static boolean ignoreContinuation(Class<?> continuation, Continuation<?> cont) {

		String classname = continuation.getName();
		if(ignoredContinuations.contains(classname)) {
			return true;
		}
		
		String continuationString = cont.toString();
		
		if(ignoredContinuations.contains(continuationString)) {
			return true;
		}
		
		return false;
	}
	
	
	public static boolean ignoreDispatched(String name) {
		return ignoredDispatchs.contains(name);
	}
	
	public static boolean ignoreDispatched(Class<?> dispatched, CoroutineContext context) {
		String classname = dispatched.getName();
		if(ignoredDispatchs.contains(classname)) {
			return true;
		}
		
		if(context == null) return false;
		
		String name = getCoroutineName(context);
		
		if(ignoredDispatchs.contains(name)) return true;
		
		return false;
	}

	public static boolean ignoreSuspend(Class<?> suspendClass, String suspendName) {
		
		String classname = suspendClass.getName();
		if(ignoredSuspends.contains(classname)) return true;
		
		return ignoredSuspends.contains(suspendName);
	}
	
	public static boolean ignoreSuspend(Class<?> suspend, Function2<?, ?, ?> susp) {
		
		String classname = suspend.getName();

		if(ignoredSuspends.contains(classname)) return true;

		if(susp == null) return false;

		String name = null;
		
		if(susp instanceof Continuation) {
			name = susp.toString();
		}
		
		if(name != null && ignoredSuspends.contains(name)) {
			return true;
		}

		return false;
	}

	public static String sub = "createCoroutineFromSuspendFunction";

	public static NRCoroutineToken setToken(CoroutineContext context) {
		NRCoroutineToken coroutineToken = context.get(NRCoroutineToken.key);
		if(coroutineToken == null) {
			Token t = NewRelic.getAgent().getTransaction().getToken();
			if(t != null && t.isActive()) {
				coroutineToken = new NRCoroutineToken(t);
				return coroutineToken;
			} else if(t != null) {
				t.expire();
				t = null;
			}
		}
		return null;
	}

	public static Token getToken(CoroutineContext context) {
		NRCoroutineToken coroutineToken = context.get(NRCoroutineToken.key);
		Token token = null;
		if(coroutineToken != null) {
			token = coroutineToken.getToken();
		}

		return token;
	}

	public static void expireToken(CoroutineContext context) {
		NRCoroutineToken coroutineToken = context.get(NRCoroutineToken.key);
		if(coroutineToken != null) {
			Token token = coroutineToken.getToken();
			token.expire();
			context.minusKey(NRCoroutineToken.key);
		}
	}
	
	@SuppressWarnings("unchecked")
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
		CoroutineName cName = context.get(CoroutineName.Key);
		if(cName != null) {
			String name = cName.getName();
			if(name != null && !name.isEmpty()) return name;
		}

		return null;
	}

	@Override
	public void configChanged(String appName, AgentConfig agentConfig) {
		loadConfig(agentConfig);

	}
	
	public static <T> String getContinuationString(Continuation<T> continuation) {
		String contString = continuation.toString();
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
