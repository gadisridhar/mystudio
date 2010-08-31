package org.yy.studyjbpm.handlers;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

public class FinalAcceptanceDecisionHandler implements DecisionHandler {

	public String decide(ExecutionContext executionContext) throws Exception {
		boolean result = (Boolean) executionContext
				.getVariable("PROJECT_LEADER_INTERVIEW_OK");
		System.out.println("Project Leader Approved? = " + result);
		if (result) {
			return "to Create WorkStation";
		}
		return "No - Find a New Candidate";
	}

}
