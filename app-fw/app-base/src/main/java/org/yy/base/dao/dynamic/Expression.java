/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yy.base.dao.dynamic;

/**
 *
 * @author yyi
 */
public abstract class Expression {

    @Override
    public abstract String toString();
    public abstract String eval(Object obj);
}
