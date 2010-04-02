/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yy.base.dao.dynamic;

/**
 *
 * @author yyi
 */
public class IfExpression extends Expression {
    private String condition;
    private Expression expression;

    public String getCondition() {
        return condition;
    }

    public void setCondition(String condition) {
        this.condition = condition;
    }

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    @Override
    public String toString() {
         return "IF(" + this.condition +") " + this.expression.toString();
    }

    @Override
    public String eval(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
