package com.newrelic.instrumentation.kotlin.coroutines;

import com.newrelic.agent.config.AgentConfig;
import com.newrelic.agent.config.AgentConfigListener;
import com.newrelic.agent.service.ServiceFactory;
import com.newrelic.api.agent.Config;
import com.newrelic.api.agent.NewRelic;

public class Utils implements AgentConfigListener {

	private static final String SUSPENDSIGNORECONFIG = "Coroutines.ignores.suspends";
	
	private static final Utils INSTANCE = new Utils();
	

	static {
		ServiceFactory.getConfigService().addIAgentConfigListener(INSTANCE);
		Config config = NewRelic.getAgent().getConfig();
		loadConfig(config);
		
	}
		
	private static void loadConfig(Config config) {
		String ignores = config.getValue(SUSPENDSIGNORECONFIG);
		if (ignores != null && !ignores.isEmpty()) {
			SuspendIgnores.reset(config);
		}
	}
	
	public static String sub = "createCoroutineFromSuspendFunction";

	@Override
	public void configChanged(String appName, AgentConfig agentConfig) {
		loadConfig(agentConfig);

	}
	
}
