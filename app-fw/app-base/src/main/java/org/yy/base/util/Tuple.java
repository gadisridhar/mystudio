/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yy.base.util;

/**
 *
 * @author yyi
 */
public class Tuple {
    private Object value1;
    private Object value2;
    private Object value3;

    public Tuple(Object value1, Object value2, Object value3) {
        this.value1 = value1;
        this.value2 = value2;
        this.value3 = value3;
    }

    public Object getValue1() {
        return value1;
    }

    public Object getValue2() {
        return value2;
    }

   public Object getValue3() {
        return value3;
    }

    
}
