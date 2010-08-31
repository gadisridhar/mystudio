package org.yy.studyjbpm.handlers;

import java.util.HashSet;
import java.util.Set;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.yy.studyjbpm.model.JobPositionRequest;
import org.yy.studyjbpm.model.TechnicalSkills;

public class CreateNewJobPositionRequestActionHandler implements ActionHandler {

	public void execute(ExecutionContext executionContext) throws Exception {
		JobPositionRequest request = CreateRequest();
		executionContext.setVariable("REQUEST_INFO", request);

	}

	private JobPositionRequest CreateRequest() {
		JobPositionRequest request = new JobPositionRequest();
		request.setYearsOfExperience(2);
		Set<TechnicalSkills> skills = new HashSet<TechnicalSkills>();
		skills.add(TechnicalSkills.JMS);
		skills.add(TechnicalSkills.JBPM);
		request.setTechicalSkillsRequested(skills);
		return request;
	}

}
