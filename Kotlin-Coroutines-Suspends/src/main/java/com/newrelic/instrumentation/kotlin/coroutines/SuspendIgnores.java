package com.newrelic.instrumentation.kotlin.coroutines;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

import com.newrelic.api.agent.Config;
import com.newrelic.api.agent.NewRelic;

public class SuspendIgnores {

	private static final List<String> ignoredSuspends = new ArrayList<String>();
	private static final String SUSPENDSIGNORECONFIG = "Coroutines.ignores.suspends";

	static {
		Config config = NewRelic.getAgent().getConfig();
		String value = config.getValue(SUSPENDSIGNORECONFIG);
		init(value);

	}
	
	private static void init(String value) {
		if(value != null && !value.isEmpty()) {
			String[] ignores = value.split(",");
			for(String ignore : ignores) {
				addIgnore(ignore);
			}
		}
	}
	
	public static void reset(Config config) {
		ignoredSuspends.clear();
		String value = config.getValue(SUSPENDSIGNORECONFIG);
		init(value);
	}
	
	public static void addIgnore(String s) {
		if(!ignoredSuspends.contains(s)) {
			ignoredSuspends.add(s);
			NewRelic.getAgent().getLogger().log(Level.FINE, "Will ignore suspends named {0}", s);
		}
	}
	
	public static boolean ignoreSuspend(Object obj) {
		return ignoredSuspends.contains(obj.toString()) || ignoredSuspends.contains(obj.getClass().getName());
	}
}
