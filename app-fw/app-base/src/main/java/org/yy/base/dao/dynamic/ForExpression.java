/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yy.base.dao.dynamic;

/**
 *
 * @author yyi
 */
public class ForExpression extends Expression {
    private String var;
    private String queryVar;
    private Expression expression;

    public Expression getExpression() {
        return expression;
    }

    public void setExpression(Expression expression) {
        this.expression = expression;
    }

    public String getQueryVar() {
        return queryVar;
    }

    public void setQueryVar(String queryVar) {
        this.queryVar = queryVar;
    }

    public String getVar() {
        return var;
    }

    public void setVar(String var) {
        this.var = var;
    }

    @Override
    public String toString() {
        return "FOR(" + this.var + " IN " + this.queryVar +") " + this.expression.toString();
    }

    @Override
    public String eval(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }


}
