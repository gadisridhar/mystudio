package org.yy.studyjbpm.handlers;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

public class HeartCheckUpActionHandler implements ActionHandler {

	public void execute(ExecutionContext executionContext) throws Exception {
		executionContext.setVariable("HEART_CHECK_APPROVED", true);

	}

}
