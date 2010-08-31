package org.yy.studyjbpm.handlers;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;
import org.yy.studyjbpm.model.Candidate;
import org.yy.studyjbpm.model.JobPositionRequest;
import org.yy.studyjbpm.model.TechnicalSkills;

public class ApproveCandidateSkillsDecisionHandler implements DecisionHandler {

	public String decide(ExecutionContext executionContext) throws Exception {
		Candidate c = (Candidate) executionContext.getVariable("CANDIDATE_INFO");
		JobPositionRequest request = (JobPositionRequest) executionContext.getVariable("REQUEST_INFO");
		boolean result = fulfillRequirements(c,request);
		System.out.println("Candidate Approved ? = " + result);
		if(result)
		{
			return "Approved - Go to Tecnical Interview";
		}
		return "No -  Find a new Candidate";
	}

	private boolean fulfillRequirements(Candidate c, JobPositionRequest request) {
		boolean yearOfExperienceOK = c.getAgeOfExperience() > request.getYearsOfExperience();
		for(TechnicalSkills skill: request.getTechicalSkillsRequested()){
			if(!(c.getSkills().contains(skill))){
				return false;
			}
		}
		return yearOfExperienceOK;
	}

}
