/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yy.base.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.Version;
import org.yy.base.context.UserContextHolder;

/**
 *
 * @author yyi
 */
@MappedSuperclass
public abstract class BaseEntity<PK extends Serializable> extends BaseObject {

    @Column(nullable = true, name = "created_by", length = 50)
    private String createdBy;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "created_time", nullable = true)
    private Date createdTime;
    @Column(nullable = true, name = "updated_by", length = 50)
    private String updatedBy;
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "updated_time", nullable = true)
    private Date updatedTime;
    @Version
    @Column(name = "version", nullable = true)
    private Long version;

    @PrePersist
    public void updateBaseFieldsBeforePersist() {
        createdTime = new Date(System.currentTimeMillis());
        createdBy = UserContextHolder.get().getUserId();
        updatedTime = new Date(System.currentTimeMillis());
        updatedBy = UserContextHolder.get().getUserId();
    }

    @javax.persistence.PreUpdate
    public void updateBaseFieldsBeforUpdate() {
        updatedTime = new Date(System.currentTimeMillis());
        updatedBy = UserContextHolder.get().getUserId();
    }

//    @javax.persistence.PostLoad
//    public void fixVersion() {
//        if (version == null) {
//            version = 0L;
//        }
//    }
    public Long getVersion() {
        return version;
    }

    public void setVersion(Long version) {
        this.version = version;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public String getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(String updatedBy) {
        this.updatedBy = updatedBy;
    }

    public Date getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(Date updatedTime) {
        this.updatedTime = updatedTime;
    }

    public abstract PK getId();

    /**
     * Returns a multi-line String with key=value pairs.
     * @return a String representation of this class.
     */
    @Override
    public String toString() {
        PK id = this.getId();
        if (id == null) {
            return "";
        } else {
            return getId().toString();
        }
    }

    /**
     * Compares object equality. When using Hibernate, the primary key should
     * not be a part of this comparison.
     * @param o object to compare to
     * @return true/false based on equality tests
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final BaseEntity<PK> other = (BaseEntity<PK>) obj;
        PK id = this.getId();
        PK otherId = other.getId();
        if (id == null && otherId == null) {
            //todo  get all properties to check.
        }
        if (id != otherId && (id == null || !id.equals(otherId))) {
            return false;
        }
        return true;
    }

    /**
     * When you override equals, you should override hashCode. See "Why are
     * equals() and hashCode() importation" for more information:
     * http://www.hibernate.org/109.html
     * @return hashCode
     */
    @Override
    public int hashCode() {
        int hash = 7;
        PK id = this.getId();
        if (id == null) {
            //todo get all properties to calcuate hashcode.
        } else {
            hash = 23 * hash + id.hashCode();
        }
        return hash;
    }
}
