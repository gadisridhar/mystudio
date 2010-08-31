package org.yy.studyjbpm.model;

import java.io.Serializable;

public class Job implements Serializable {
	private String description;
	private int years;
	public Job(String description, int years) {
		this.description = description;
		this.years = years;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public int getYears() {
		return years;
	}
	public void setYears(int years) {
		this.years = years;
	}
	
	
}
