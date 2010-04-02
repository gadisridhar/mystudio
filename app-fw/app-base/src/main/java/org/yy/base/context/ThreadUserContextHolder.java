/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yy.base.context;

/**
 *
 * @author yyi
 */
public class ThreadUserContextHolder extends UserContextHolder {

    private static ThreadLocal<UserContext> local = new InheritableThreadLocal<UserContext>() {

        @Override
        protected UserContext initialValue() {
            return UserContext.EMPTY;
        }
    };

    public ThreadUserContextHolder() {
        UserContextHolder.setHolder(this);
    }

    @Override
    protected UserContext getUserContext() {
        return local.get();
    }

    @Override
    protected void setUserContext(UserContext ctx) {
        local.set(ctx);
    }
}
