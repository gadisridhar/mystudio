/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yy.base.context;

import java.io.Serializable;
import java.util.Properties;
import org.springframework.util.Assert;
import org.yy.base.AppException;

/**
 *
 * @author yyi
 */
public class UserContext implements Serializable{
    public static UserContext EMPTY = new UserContext("$$EMPTY_USERCONTEXT"){

        @Override
        public Properties getProperties() {
            throw new AppException("No allow to access properties of EMPTY UserContext.");
        }

    };

    private String userId;
    private Properties properties = new Properties();

    public UserContext(String userId) {
        Assert.hasText(userId, "[userId] must be non-empty string.");
        this.userId = userId;
    }


    public Properties getProperties() {
        return properties;
    }


    public String getUserId() {
        return userId;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final UserContext other = (UserContext) obj;
        if ((this.userId == null) ? (other.userId != null) : !this.userId.equals(other.userId)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + (this.userId != null ? this.userId.hashCode() : 0);
        return hash;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("UserId=");
        sb.append(userId);
        sb.append("Properties:");
        sb.append(this.properties.toString());
        return sb.toString();
    }


}
