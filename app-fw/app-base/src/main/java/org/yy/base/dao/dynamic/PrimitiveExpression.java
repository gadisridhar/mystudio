package org.yy.base.dao.dynamic;

/**
 *
 * @author yyi
 */
public class PrimitiveExpression extends Expression {
    private String exp;

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    @Override
    public String toString() {
        return "{" + exp + "}";
    }

    @Override
    public String eval(Object obj) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}