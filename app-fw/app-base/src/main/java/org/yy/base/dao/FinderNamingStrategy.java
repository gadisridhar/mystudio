/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yy.base.dao;

import java.lang.reflect.Method;

/**
 *
 * @author yyi
 */
public interface FinderNamingStrategy
{
    public String queryNameFromMethod(Class findTargetType, Method finderMethod);
}
