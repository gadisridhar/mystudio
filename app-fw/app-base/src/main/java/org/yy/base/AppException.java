/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yy.base;

/**
 *
 * @author yyi
 */
public class AppException extends RuntimeException {

    public AppException(Throwable cause) {
        super(cause);
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
    }

    public AppException(String message) {
        super(message);
    }

    public AppException() {
    }

}
