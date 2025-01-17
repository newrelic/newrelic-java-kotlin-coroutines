package com.newrelic.instrumentation.kotlin.coroutines;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		String objString = obj.toString();
		String className = obj.getClass().getName();
		NewRelic.getAgent().getLogger().log(Level.FINE, "Call to SuspendIgnores.ignoreSuspend, objString = {0}, className = {1}" , objString, className);
		
		if(ignoredSuspends.contains(objString) || ignoredSuspends.contains(className)) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "Matched classname or objString");
			return true;
		}
 		
		for(String s : ignoredSuspends) {
			NewRelic.getAgent().getLogger().log(Level.FINE, "Comparing to regex {0}", s);
			Pattern pattern = Pattern.compile(s);
			Matcher matcher1 = pattern.matcher(objString);
			Matcher matcher2 = pattern.matcher(className);
			NewRelic.getAgent().getLogger().log(Level.FINE, matcher1.matches() ? "Matched objString" : matcher2.matches() ? "Matched Classname" : "not a match for object string or classname");
			if(matcher1.matches() || matcher2.matches()) return true;
		}
		
		return false;
//		return ignoredSuspends.contains(obj.toString()) || ignoredSuspends.contains(obj.getClass().getName());
	}
}
