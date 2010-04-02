/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yy.base.dao.dynamic;

/**
 *
 * @author yyi
 */
public class SynaxException extends RuntimeException {

    /**
     * Creates a new instance of <code>SynaxException</code> without detail message.
     */
    public SynaxException() {
    }


    /**
     * Constructs an instance of <code>SynaxException</code> with the specified detail message.
     * @param msg the detail message.
     */
    public SynaxException(String msg) {
        super(msg);
    }
}
