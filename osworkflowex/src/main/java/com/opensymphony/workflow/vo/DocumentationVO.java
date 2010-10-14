package com.opensymphony.workflow.vo;

/**
 * @author chris.chen
 */
public class DocumentationVO {
    private long wf_id;
    private String title;
    private String content;

    public long getWf_id() {
        return wf_id;
    }

    public void setWf_id(long wf_id) {
        this.wf_id = wf_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
