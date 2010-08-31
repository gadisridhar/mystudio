package org.yy.studyjbpm.model;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class JobPositionRequest implements Serializable{
	
	private Set<TechnicalSkills> techicalSkillsRequested = new HashSet<TechnicalSkills>();
	private int yearsOfExperience = 0;
	public Set<TechnicalSkills> getTechicalSkillsRequested() {
		return techicalSkillsRequested;
	}
	public void setTechicalSkillsRequested(
			Set<TechnicalSkills> techicalSkillsRequested) {
		this.techicalSkillsRequested = techicalSkillsRequested;
	}
	public int getYearsOfExperience() {
		return yearsOfExperience;
	}
	public void setYearsOfExperience(int yearsOfExperience) {
		this.yearsOfExperience = yearsOfExperience;
	}
	
	

}
