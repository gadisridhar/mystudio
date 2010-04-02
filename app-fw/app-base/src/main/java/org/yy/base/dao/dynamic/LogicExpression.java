/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yy.base.dao.dynamic;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yyi
 */
public class LogicExpression extends Expression {

    private LogicOperator operator;
    private List<Expression> expressions = new ArrayList<Expression>();

    public List<Expression> getExpressions() {
        return expressions;
    }

    public LogicOperator getOperator() {
        return operator;
    }

    public void setOperator(LogicOperator operator) {
        this.operator = operator;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (expressions.size() > 0) {
            sb.append('{');
            for (int i = 0; i < this.expressions.size() - 1; i++) {
                Expression ex = this.expressions.get(i);
                sb.append(ex.toString());
                sb.append(" ");
                sb.append(this.operator.name());
                sb.append(" ");
            }
            Expression ex = this.expressions.get(this.expressions.size() - 1);
            sb.append(ex.toString());
             sb.append('}');
        }
        return sb.toString();
    }

    @Override
    public String eval(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
