package org.yy.studyjbpm.handlers;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

public class ProjectLeaderInterviewActionHandler implements ActionHandler {

	public void execute(ExecutionContext executionContext) throws Exception {
		 executionContext.setVariable("PROJECT_LEADER_INTERVIEW_OK", true);

	}

}
