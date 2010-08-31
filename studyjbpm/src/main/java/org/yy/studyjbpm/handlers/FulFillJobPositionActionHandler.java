package org.yy.studyjbpm.handlers;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.ProcessInstance;

public class FulFillJobPositionActionHandler implements ActionHandler {

	public void execute(ExecutionContext executionContext) throws Exception {
		ProcessInstance requestJobPositionPI = (ProcessInstance) executionContext
				.getVariable("REQUEST_TO_FULFILL");
		requestJobPositionPI.signal();

	}

}
