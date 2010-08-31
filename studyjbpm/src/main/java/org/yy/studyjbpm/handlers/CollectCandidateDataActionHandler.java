package org.yy.studyjbpm.handlers;

import java.util.ArrayList;
import java.util.List;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.yy.studyjbpm.model.Candidate;
import org.yy.studyjbpm.model.Job;

public class CollectCandidateDataActionHandler implements ActionHandler {

	public void execute(ExecutionContext executionContext) throws Exception {
		Candidate candidate = (Candidate) executionContext.getVariable("CANDIDATE_INFO");
		List<Job> previousJobs = new ArrayList<Job>();
		previousJobs.add(new Job("Senior Java Developer",4));
		previousJobs.add(new Job("Java Architect",2));
		candidate.setPreviousJobs(previousJobs);
		executionContext.setVariable("CANDIDATE_INFO", candidate);
	}

}
