package org.yy.studyjbpm.recruiting;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.yy.studyjbpm.model.Candidate;
import org.yy.studyjbpm.model.JobPositionRequest;
import org.yy.studyjbpm.model.TechnicalSkills;

public class RecruitingProcessWithOutServicesTestCase {

	@Before
	public void setUp() throws Exception{}
	@After
	public void tearDown() throws Exception{}
	@Test
	public void test_NormalFlow(){
		
		ProcessDefinition requestJobPositionPD = ProcessDefinition.parseXmlResource("jpdl/RequestJobPosition/processDefinition.xml");
		ProcessInstance requestJobInstance = requestJobPositionPD.createProcessInstance();
		requestJobInstance.signal();
		Assert.assertEquals("Create request", requestJobInstance.getRootToken().getNode().getName());
		
		requestJobInstance.signal();
		JobPositionRequest jobRequest = (JobPositionRequest) requestJobInstance.getContextInstance().getVariable("REQUEST_INFO");
		
		Assert.assertNotNull(jobRequest);
		Candidate candidate = CreateNewCandidate();
		
		ProcessDefinition candidateInterviewsPD = ProcessDefinition.parseXmlResource("jpdl/CandidateInterviews/processdefinition.xml");
		ProcessInstance interviewInstance = candidateInterviewsPD.createProcessInstance();
		Map<String,Object> vars = new HashMap<String,Object>();
		vars.put("REQUEST_TO_FULFILL", requestJobInstance);
		vars.put("REQUEST_INFO", requestJobInstance.getContextInstance().getVariable("REQUEST_INFO"));
		vars.put("CANDIDATE_INFO", candidate);
		interviewInstance.addInitialContextVariables(vars);
		
		interviewInstance.signal();
		
		System.out.println(interviewInstance.getRootToken().getNode().getName());
		
		Assert.assertEquals("Initial Interview", interviewInstance.getRootToken().getNode().getName());
        //Continue the process, the automatic decision node will decide if the process will continue
        //or if the candidate is discarded, because the minimal requirements are not meet.
		interviewInstance.signal();

        Assert.assertEquals("Technical Interview",interviewInstance.getRootToken().getNode().getName());
        // Here we continue the process, the Technical Interview only adds a flag to know
        // if the candidate meets the technical requirements evaluated by the technical staff.
        // This flag is added by the action handler (TechnicalQuestionnaireActionHandler) inside the node-enter event
        // of the Technical Interview. This value es hardcoded, you can change if you want
        
        interviewInstance.signal();

        Assert.assertEquals("Medical Check Ups",interviewInstance.getRootToken().getNode().getName());
        // Here the Fork node creates three child tokens, one for each transition, we need to get these
        // child tokens to finish that activities. Each of these medical check ups will add one process variable
        // that will describe the outcome of the medical examination.
        Map<String, Token> childTokens = interviewInstance.getRootToken().getChildren();
        Assert.assertEquals(3,childTokens.size());

        //In this case we will choose each token and finish them in a random order
        //Try  changing the order of the following method calls. Also try commenting one of them, you will
        //see that the join node will wait until you signal all the activities.
        childTokens.get("to Heart Check Up").signal();

        childTokens.get("to Physical Check Up").signal();

        childTokens.get("to Psychological Check Up").signal();

        Assert.assertEquals("Project leader Interview",interviewInstance.getRootToken().getNode().getName());
        //The project leader accept the candidate (is hardcoded in the class ProjectLeaderInterviewActionHandler)
        //called inside the node-enter event of the Project leader Interview state node
        //This will create the workStation for the new user (user, password, email account) and
        //then automatically signal the process to the end node called Candidate Accepted
        interviewInstance.signal();
        //The last node (end state) will retrieve the requestJobPositionPI and signal it
        //to abort all the other interviews for that fulfilled Job Position.
        //This happens inside the FulFillJobPositionActionHandler inside the node-event
        //of the Candidate Accepted end state

        Assert.assertEquals("Candidate Accepted",interviewInstance.getRootToken().getNode().getName());

        
        Assert.assertEquals("Job Position Fulfilled",requestJobInstance.getRootToken().getNode().getName());
	}
	
	private Candidate CreateNewCandidate() {
		
		Candidate c = new Candidate();
		c.setAgeOfExperience(10);

		c.getSkills().add(TechnicalSkills.JBPM);
		c.getSkills().add(TechnicalSkills.JMS);
		return c;
	}
}
