package com.opensymphony.workflow.vo;

import java.util.Date;

/**
 * @author chris.chen
 */
public class DocumentationOpinionVO {
    private long id;
    private long entry_id;
    private int step_id;
    private int action_id;
    private String caller;
    private String opinion;
    private Date opinion_time;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getEntry_id() {
        return entry_id;
    }

    public void setEntry_id(long entry_id) {
        this.entry_id = entry_id;
    }

    public int getStep_id() {
        return step_id;
    }

    public void setStep_id(int step_id) {
        this.step_id = step_id;
    }

    public int getAction_id() {
        return action_id;
    }

    public void setAction_id(int action_id) {
        this.action_id = action_id;
    }

    public String getCaller() {
        return caller;
    }

    public void setCaller(String caller) {
        this.caller = caller;
    }

    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
    }

    public Date getOpinion_time() {
        return opinion_time;
    }

    public void setOpinion_time(Date opinion_time) {
        this.opinion_time = opinion_time;
    }
}
