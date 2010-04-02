/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yy.base.dao;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author yyi
 */
public interface FinderExecutor<T>
{
    /**
     * Execute a finder method with the appropriate arguments
     */
    List<T> executeFinder(Method method, Object[] queryArgs);

   // Iterator<T> iterateFinder(Method method, Object[] queryArgs);

//    ScrollableResults scrollFinder(Method method, Object[] queryArgs);
}