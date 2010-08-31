package org.yy.studyjbpm.handlers;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

public class TechnicalQuestionnaireActionHandler implements ActionHandler {

	public void execute(ExecutionContext executionContext) throws Exception {
		executionContext.setVariable("TECHNICAL_INTERVIEW_OK", true);

	}

}
