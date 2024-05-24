package com.newrelic.instrumentation.kotlin.coroutines_17;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.newrelic.api.agent.Config;
import com.newrelic.api.agent.NewRelic;

public class DispatchedTaskIgnores {
	
	private static List<String> ignoredTasks = new ArrayList<>();
	private static final String DISPATCHEDIGNORECONFIG = "Coroutines.ignores.dispatched";
	
	static {
		Config config = NewRelic.getAgent().getConfig();
		String ignores = config.getValue(DISPATCHEDIGNORECONFIG);
		configure(ignores);

	}
	
	
	public static boolean ignoreDispatchedTask(String contString) {
		return ignoredTasks.contains(contString);
	}
	
	public static void addIgnore(String ignore) {
		if(!ignoredTasks.contains(ignore)) {
			ignoredTasks.add(ignore);
			NewRelic.getAgent().getLogger().log(Level.FINE, "Will ignore DispatchedTasks with continuation string {0}", ignore);
		}
	}
	
	public static void reset() {
		ignoredTasks.clear();
	}
	
	protected static void configure(String result) {
		if(result == null || result.isEmpty()) return;
		String[] ignores = result.split(",");
		for(String ignore : ignores) {
			addIgnore(ignore);
		}
	}
	
}
