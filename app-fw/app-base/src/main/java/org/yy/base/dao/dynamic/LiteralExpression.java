/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yy.base.dao.dynamic;

/**
 *
 * @author yyi
 */
public class LiteralExpression  extends Expression{
    private String literal;

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    @Override
    public String toString() {
        return literal;
    }

    @Override
    public String eval(Object obj) {
        return literal;
    }

}
