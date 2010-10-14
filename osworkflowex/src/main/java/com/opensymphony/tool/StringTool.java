package com.opensymphony.tool;

/**
 * @author chris.chen
 */
public class StringTool {
    public static String checkNull(Object o) {
        if (o == null) {
            return "";
        } else if (o instanceof String) {
            return (String) o;
        } else {
            return String.valueOf(o);
        }
    }
}
