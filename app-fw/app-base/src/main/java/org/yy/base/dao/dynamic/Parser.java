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
public class Parser {

    public Expression parse(String where) {
        Expression exp = null;
        if (where.startsWith("IF")) {
            exp = parseIf(where);
        } else if (where.startsWith("FOR")) {
            exp = parseFor("FOR");
        } else {
            exp = parseOr(where);
        }

        return exp;
    }

    private Expression parseIf(String where) {
        where = removeBracket(where);
        IfExpression exp = new IfExpression();
        int left = where.indexOf("(");
        int right = where.indexOf(")");
        if (left >= right) {
            throw new SynaxException("syntax is wrong: " + where);
        }
        exp.setCondition(where.substring(left + 1, right));
        exp.setExpression(parse(where.substring(right + 1)));
        return exp;
    }

    private Expression parseFor(String where) {
        where = removeBracket(where);
        ForExpression exp = new ForExpression();
        int left = where.indexOf("(");
        int right = where.indexOf(")");
        if (left >= right) {
            throw new SynaxException("syntax is wrong: " + where);
        }
        String condition = where.substring(left + 1, right);
        if (condition.length() < 6) {
            throw new SynaxException("syntax is wrong: is [IN] key missing? : " + where);
        }

        int inPosition = condition.indexOf(" IN ");
        if (condition.length() < 1) {
            throw new SynaxException("syntax is wrong: [IN] key is missing. : " + where);
        }
        exp.setVar(condition.substring(0, inPosition).trim());
        exp.setVar(condition.substring(inPosition + 1).trim());
        exp.setExpression(parse(where.substring(right + 1)));
        return exp;
    }

    private Expression parseOr(String where) {

        where = removeBracket(where);

        List<String> expStrs = parseList(LogicOperator.OR, where);
        if (expStrs.size() != 1) {
            LogicExpression exp = new LogicExpression();
            exp.setOperator(LogicOperator.OR);
            for (String expStr : expStrs) {
                exp.getExpressions().
                        add(parseAnd(expStr));
            }
            return exp;
        } else {
            return parseAnd(expStrs.get(0));
        }


        //return exp;
    }

    private Expression parseAnd(String where) {



        where = removeBracket(where);

        List<String> expStrs = parseList(LogicOperator.AND, where);
        if (expStrs.size() != 1) {
            LogicExpression exp = new LogicExpression();
            exp.setOperator(LogicOperator.AND);
            for (String expStr : expStrs) {
                exp.getExpressions().
                        add(parsePrimitive(expStr));
            }
            return exp;
        } else {
            return parsePrimitive(expStrs.get(0));
        }
    }

    private Expression parsePrimitive(String where) {
        Expression exp = null;
        where = where.trim();

        if (where.startsWith("IF")) {
            exp = parseIf(where);
        } else if (where.startsWith("FOR")) {
            exp = parseFor("FOR");
        } else if (where.charAt(0) == '{' && where.charAt(where.length() - 1) == '}') {
            exp = parse(where);
        } else {
            List<String> exprStrs = parseList(where);
            if (exprStrs.size() == 1) {
                PrimitiveExpression pexp = new PrimitiveExpression();
                pexp.setExp(exprStrs.get(0));
                return pexp;
            } else {
                SequenceExpression sexp = new SequenceExpression();
                for (String expStr : exprStrs) {
                    if (expStr.charAt(0) == '{') {
                        sexp.getExpressions().add(parse(expStr));
                    } else {
                        LiteralExpression pexp = new LiteralExpression();
                        pexp.setLiteral(expStr);
                        sexp.getExpressions().add(pexp);
                    }
                }
                return sexp;
            }

        }

        return exp;
    }

    private String removeBracket(String where) {

        where = where.trim();

        if ("".equals(where) || where.length() <= 2) {
            throw new SynaxException("syntax is wrong: " + where);
        } else {
            char first = where.charAt(0);
            char end = where.charAt(where.length() - 1);
            if (first == '{' && end != '}' || first != '{' && end == '}') {
                //throw new SynaxException("syntax is wrong. missing '(' or ')'. " + where);
            } else if (first == '{' && end == '}') {
                int bracketNum = 0;

                for (int index = 1; index < where.length() - 1; index++) {
                    if (where.charAt(index) == '{') {
                        bracketNum++;
                    } else if (where.charAt(index) == '}') {
                        bracketNum--;
                        if (bracketNum < 0) {//can not truncat the { and }
                            break;
                        }
                    }
                }
                if (bracketNum == 0) {
                    where = where.substring(1, where.length() - 1);
                }
            }

        }
        return where;
    }

    private List<String> parseList(LogicOperator operator, String where) {
        List<String> expStrs = new ArrayList<String>();
        int lenOfOperator = operator.name().length();
        int index = 0;
        int bracketNum = 0;
        int startPostion = 0;
        while (index < where.length()) {


            if (where.charAt(index) == '{') {
                bracketNum++;
            } else if (where.charAt(index) == '}') {
                bracketNum--;
                if (bracketNum < 0) {
                    throw new SynaxException("syntax is wrong, [bracket] : " + where);
                }
            }

            if (index == where.length() - 1) {
                expStrs.add(where.substring(startPostion, where.length()));
                break;
            }

            if (bracketNum == 0 && (index + lenOfOperator) < where.length() - 2 && index > 0) {
                String logicStr = where.substring(index, index + lenOfOperator);
                if (logicStr.equals(operator.name()) && where.charAt(index - 1) == ' ' && where.charAt(index + lenOfOperator) == ' ') {
                    expStrs.add(where.substring(startPostion, index - 1));
                    index = index + lenOfOperator;
                    startPostion = index;
                }
            }
            index++;
        }
        if (bracketNum != 0) {
            throw new SynaxException("syntax is wrong, [bracket] : " + where);
        }
        return expStrs;
    }

    private List<String> parseList(String where) {
        List<String> expStrs = new ArrayList<String>();
        int index = 0;
        int bracketNum = 0;
        int startPostion = 0;
        while (index < where.length()) {

            if (where.charAt(index) == '{') {
                if (bracketNum == 0 && index > startPostion) {
                    expStrs.add(where.substring(startPostion, index));
                    startPostion = index;
                }
                bracketNum++;
            } else if (where.charAt(index) == '}') {
                bracketNum--;
                if (bracketNum < 0) {
                    throw new SynaxException("syntax is wrong, [bracket] : " + where);
                }

                if (bracketNum == 0 && index > startPostion) {

                    expStrs.add(where.substring(startPostion, index + 1));
                    startPostion = index + 1;
                }
            }

            if (index == where.length() - 1 && index >= startPostion) {
                expStrs.add(where.substring(startPostion, where.length()));
                break;
            }


            index++;
        }
        if (bracketNum != 0) {
            throw new SynaxException("syntax is wrong, [bracket] : " + where);
        }
        return expStrs;
    }
}
