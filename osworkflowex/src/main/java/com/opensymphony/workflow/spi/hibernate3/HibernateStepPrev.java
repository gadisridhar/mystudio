package com.opensymphony.workflow.spi.hibernate3;

import java.io.Serializable;

/**
 * @author <a href="mailto:cucuchen520@yahoo.com.cn">Chris Chen</a>
 */
public class HibernateStepPrev implements Serializable {
    long id;
    long previousId;


    public long getPreviousId() {
        return previousId;
    }

    public void setPreviousId(long previousId) {
        this.previousId = previousId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
}
