package org.yy.studyjbpm.handlers;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

public class TechnicalQuestionnaireApprovedDecisionHandler implements
		DecisionHandler {

	public String decide(ExecutionContext executionContext) throws Exception {
		boolean approved = ((Boolean) executionContext.getVariable("TECHNICAL_INTERVIEW_OK")).booleanValue();
		System.out.println("Technical Interview Approved? = " + approved);
		
		if(approved){
            return "to Medical Check Ups";
        }
        return "No - Find a new Candidate";
	}

}
