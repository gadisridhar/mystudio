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
public class SequenceExpression extends Expression{
    
 private List<Expression> expressions = new ArrayList<Expression>();

    public List<Expression> getExpressions() {
        return expressions;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (expressions.size() > 0) {
           
            for (int i = 0; i < this.expressions.size() - 1; i++) {
                Expression ex = this.expressions.get(i);
                sb.append(ex.toString());
                sb.append(" ");
            }
            Expression ex = this.expressions.get(this.expressions.size() - 1);
            sb.append(ex.toString());

        }
        return sb.toString();
    }

    @Override
    public String eval(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
