package org.yy.studyjbpm.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Candidate implements Serializable {
	private Set<TechnicalSkills> techicalSkillsRequested = new HashSet<TechnicalSkills>();
	private int ageOfExperience = 0;
	List<Job> previousJobs = new ArrayList<Job>();
	
	public List<Job> getPreviousJobs() {
		return previousJobs;
	}
	public void setPreviousJobs(List<Job> previousJobs) {
		this.previousJobs = previousJobs;
	}
	public List<TechnicalSkills> getSkills() {
		return skills;
	}
	public void setSkills(List<TechnicalSkills> skills) {
		this.skills = skills;
	}
	List<TechnicalSkills> skills = new ArrayList<TechnicalSkills>();
	public Set<TechnicalSkills> getTechicalSkillsRequested() {
		return techicalSkillsRequested;
	}
	public void setTechicalSkillsRequested(
			Set<TechnicalSkills> techicalSkillsRequested) {
		this.techicalSkillsRequested = techicalSkillsRequested;
	}
	public int getAgeOfExperience() {
		return ageOfExperience;
	}
	public void setAgeOfExperience(int ageOfExperience) {
		this.ageOfExperience = ageOfExperience;
	}
	
	
	
}
