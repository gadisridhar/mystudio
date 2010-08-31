package org.yy.studyjbpm.handlers;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;

public class PhysicalCheckUpActionHandler implements ActionHandler {

	public void execute(ExecutionContext executionContext) throws Exception {
		 executionContext.setVariable("PHYSICAL_CHECK_APPROVED", true);

	}

}
