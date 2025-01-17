package com.newrelic.instrumentation.kotlin.coroutines;

import com.newrelic.agent.config.AgentConfig;
import com.newrelic.agent.config.AgentConfigListener;
import com.newrelic.agent.service.ServiceFactory;
import com.newrelic.api.agent.Config;
import com.newrelic.api.agent.NewRelic;

public class Utils implements AgentConfigListener {

	private static final Utils INSTANCE = new Utils();
	public static final String CREATEMETHOD1 = "Continuation at kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$4";
	public static final String CREATEMETHOD2 = "Continuation at kotlin.coroutines.intrinsics.IntrinsicsKt__IntrinsicsJvmKt$createCoroutineUnintercepted$$inlined$createCoroutineFromSuspendFunction$IntrinsicsKt__IntrinsicsJvmKt$3";
	public static String sub = "createCoroutineFromSuspendFunction";
	private static final String CONT_LOC = "Continuation at";
	
	static {
		ServiceFactory.getConfigService().addIAgentConfigListener(INSTANCE);
		Config config = NewRelic.getAgent().getConfig();
		SuspendIgnores.reset(config);
	}
	
	@Override
	public void configChanged(String appName, AgentConfig agentConfig) {
		SuspendIgnores.reset(agentConfig);
	}

	public static String getSuspendString(String cont_string, Object obj) {
		if(cont_string.equals(CREATEMETHOD1) || cont_string.equals(CREATEMETHOD2)) return sub;
		if(cont_string.startsWith(CONT_LOC)) {
			return cont_string;
		}

		int index = cont_string.indexOf('@');
		if(index > -1) {
			return cont_string.substring(0, index);
		}
		
		return obj.getClass().getName();
	}
}
