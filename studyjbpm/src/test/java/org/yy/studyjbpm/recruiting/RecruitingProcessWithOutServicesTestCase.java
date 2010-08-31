package org.yy.studyjbpm.recruiting;

import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.exe.ProcessInstance;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RecruitingProcessWithOutServicesTestCase {

	@Before
	public void setUp() throws Exception{}
	@After
	public void tearDown() throws Exception{}
	@Test
	public void test_NormalFlow(){
		ProcessDefinition candidateInterviewsPD = ProcessDefinition.parseXmlResource("jpdl/CandidateInterviews/processdefinition.xml");
		ProcessInstance instance = candidateInterviewsPD.createProcessInstance();
		
		instance.signal();
		System.out.println(instance.getRootToken().getNode().getName());
	}
}
