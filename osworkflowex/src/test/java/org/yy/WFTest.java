package org.yy;

import java.util.Collection;

import org.junit.Assert;
import org.junit.Test;

import com.opensymphony.workflow.InvalidActionException;
import com.opensymphony.workflow.InvalidEntryStateException;
import com.opensymphony.workflow.InvalidInputException;
import com.opensymphony.workflow.InvalidRoleException;
import com.opensymphony.workflow.Workflow;
import com.opensymphony.workflow.WorkflowException;
import com.opensymphony.workflow.basic.BasicWorkflow;
import com.opensymphony.workflow.config.DefaultConfiguration;
import com.opensymphony.workflow.spi.Step;

public class WFTest {
	@Test
	public void Test1() throws InvalidActionException, InvalidRoleException, InvalidInputException, InvalidEntryStateException, WorkflowException{
		
		Workflow wf = new BasicWorkflow("tester");
		DefaultConfiguration config = new DefaultConfiguration();
		wf.setConfiguration(config);
		long workflowId = wf.initialize("mytest", 1, null);
		Collection currentSteps = wf.getCurrentSteps(workflowId);
		Assert.assertEquals("Unexpected number of current steps",1,currentSteps.size());
		Step currentStep = (Step) currentSteps.iterator().next();
		System.out.println("current step status:" + currentStep.getStatus());
		Assert.assertEquals("Unexpected curent step", 1, currentStep.getStepId());
		int[] actions = wf.getAvailableActions(workflowId, null);
		Assert.assertEquals("Unexpected number of available actions", 1, actions.length);
		//校验这个步骤是1
		Assert.assertEquals("Unexpected available action", 2, actions[0]);
		//next do action 2:
		wf.doAction(workflowId, 2, null);
		currentSteps = wf.getCurrentSteps(workflowId);
		Assert.assertEquals("Unexpected number of current steps",1,currentSteps.size());
		 currentStep = (Step) currentSteps.iterator().next();
		System.out.println("current step status:" + currentStep.getStatus());
		Assert.assertEquals("Unexpected curent step", 1, currentStep.getStepId());
		actions = wf.getAvailableActions(workflowId, null);
		Assert.assertEquals("Unexpected number of available actions", 1, actions.length);
		//校验这个步骤是1
		Assert.assertEquals("Unexpected available action", 3, actions[0]);
		
		//next do action 3:
		wf.doAction(workflowId, 3, null);
		currentSteps = wf.getCurrentSteps(workflowId);
		Assert.assertEquals("Unexpected number of current steps",0,currentSteps.size());
		
		actions = wf.getAvailableActions(workflowId, null);
		Assert.assertEquals("Unexpected number of available actions", 0, actions.length);
	
	}
}
