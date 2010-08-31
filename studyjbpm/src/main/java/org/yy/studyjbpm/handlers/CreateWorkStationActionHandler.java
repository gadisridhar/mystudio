package org.yy.studyjbpm.handlers;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

public class CreateWorkStationActionHandler implements ActionHandler {

	public void execute(ExecutionContext executionContext) throws Exception {
		System.out.println("Creating new user ...");
		System.out.println("Generating new password ...");
		System.out.println("Creating new email account ...");
		executionContext.leaveNode();

	}

}
