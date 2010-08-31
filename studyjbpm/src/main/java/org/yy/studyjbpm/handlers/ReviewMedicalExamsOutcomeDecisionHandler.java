package org.yy.studyjbpm.handlers;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

public class ReviewMedicalExamsOutcomeDecisionHandler implements
		DecisionHandler {

	public String decide(ExecutionContext executionContext) throws Exception {
		boolean heartCheckUp = (Boolean) executionContext
				.getVariable("HEART_CHECK_APPROVED");
		boolean psychoCheckUp = (Boolean) executionContext
				.getVariable("PSYCHO_CHECK_APPROVED");
		boolean physicalCheckUp = (Boolean) executionContext
				.getVariable("PHYSICAL_CHECK_APPROVED");
		
		System.out.println("Heart is okay ? = " + heartCheckUp);
		System.out.println("psychological check up is okay ? = " + psychoCheckUp);
		System.out.println("Physical check up is okay ? = " + physicalCheckUp);
		
		if (heartCheckUp && psychoCheckUp && physicalCheckUp) {
			return "Last Interview";
		}
		return "No -  Find a New Candidate";
	}

}
