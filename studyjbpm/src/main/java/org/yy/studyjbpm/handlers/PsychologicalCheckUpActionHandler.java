package org.yy.studyjbpm.handlers;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

public class PsychologicalCheckUpActionHandler implements ActionHandler {

	public void execute(ExecutionContext executionContext) throws Exception {
		executionContext.setVariable("PSYCHO_CHECK_APPROVED", true);

	}

}
