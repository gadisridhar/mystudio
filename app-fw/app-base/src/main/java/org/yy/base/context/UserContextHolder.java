/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yy.base.context;

import org.yy.base.AppException;

/**
 *
 * @author yyi
 */
public abstract class UserContextHolder {

    private static UserContextHolder holder;

    protected static UserContextHolder getHolder() {
        return holder;
    }

    protected static void setHolder(UserContextHolder holder) {
        UserContextHolder.holder = holder;
    }

    public static UserContext get() {
        if (holder != null) {
            return holder.getUserContext();
        }
        return UserContext.EMPTY;
    }

    public static UserContext get(String userId) {
        if (holder != null) {
            UserContext uc = holder.getUserContext();
            if (uc.equals(UserContext.EMPTY)) {
                uc = new UserContext(userId);
                set(uc);
            } else if(uc.getUserId().equals(userId) == false) {
                throw new AppException("User Context has been set, but user id is not same to your reqeust. Existed: id="
                        + uc.getUserId()
                        + ": your request: id " + userId);
            }

            return uc;
        }
        return UserContext.EMPTY;
    }

    public static void set(UserContext ctx) {
        if (holder != null) {
            holder.setUserContext(ctx);
        }
    }

    protected abstract UserContext getUserContext();

    protected abstract void setUserContext(UserContext ctx);
}
