package com.newrelic.instrumentation.kotlin.coroutines_17;

import static com.newrelic.instrumentation.kotlin.coroutines_17.Utils.CREATEMETHOD1;
import static com.newrelic.instrumentation.kotlin.coroutines_17.Utils.CREATEMETHOD2;
import static com.newrelic.instrumentation.kotlin.coroutines_17.Utils.sub;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.logging.Level;

import com.newrelic.api.agent.Config;
import com.newrelic.api.agent.NewRelic;

public class DispatchedTaskIgnores {
	
	private static Class<?> dispatchedTaskClass = null;
	private static List<String> ignoredTasks = new ArrayList<>();
	private static Hashtable<Class<?>,Method> methodMappings = new Hashtable<>();
	private static final String DISPATCHEDIGNORECONFIG = "Coroutines.ignores.dispatched";
	
	static {
		Config config = NewRelic.getAgent().getConfig();
		String ignores = config.getValue(DISPATCHEDIGNORECONFIG);
		configure(ignores);

	}
	
	public static void addIgnore(String ignore) {
		if(!ignoredTasks.contains(ignore)) {
			ignoredTasks.add(ignore);
		}
	}
	
	public static void reset() {
		ignoredTasks.clear();
	}
	
	protected static void configure(String result) {
		String[] ignores = result.split(",");
		for(String ignore : ignores) {
			addIgnore(ignore);
		}
	}
	
	public static boolean ignoreDispatchedTask(Object obj) {
		String result = invoke(obj);
		if(result.equals(CREATEMETHOD1) || result.equals(CREATEMETHOD2)) {
			result = sub;
		}
		if(result != null) {
			return ignoredTasks.contains(result);
		}
		return false;
	}
	
	public static String invoke(Object obj) {
		Class<?> clazz = obj.getClass();
		if(!methodMappings.containsKey(clazz)) {
			initialize(obj);
		}
		Method toUse = methodMappings.get(clazz);
		
		if(toUse != null) {
			try {
				Object value = toUse.invoke(obj, new Object[] {});
				if(value != null) {
					return value.toString();
				}
			} catch (Exception e) {
				NewRelic.getAgent().getLogger().log(Level.FINE, e, "Failed to invoke method on DispatchedTask");
			}
		}
		return null;
	}

	private static void initialize(Object obj) {
		try {
			dispatchedTaskClass = obj.getClass();
			Method method = dispatchedTaskClass.getMethod("getDelegate$kotlinx_coroutines_core", new Class<?>[] {}	);
			method.setAccessible(true);
			methodMappings.put(obj.getClass(), method);
		} catch (Exception e) {
			NewRelic.getAgent().getLogger().log(Level.FINE, e, "Failed to get DispatchedTask class and method");
		}
	}
}
