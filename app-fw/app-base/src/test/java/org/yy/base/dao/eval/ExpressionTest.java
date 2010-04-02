/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.yy.base.dao.eval;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.yy.base.eval.Expression;
import static org.junit.Assert.*;

/**
 *
 * @author yyi
 */
public class ExpressionTest {

    public ExpressionTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    private void eval(String expression, String... args) {
        Map<String, Object> variables = null;
        Expression exp = new Expression(expression);
        // System.out.println(exp.toString());
        for (int i = 0; i < args.length - 1; i++) {
            String[] bits = args[i].split("=");
            if (variables == null) {
                variables = new HashMap<String, Object>();
            }
            Object value = null;
            try {
                value = new BigDecimal(bits[1]);
            } catch (NumberFormatException efex) {
                value = bits[1];
            }
            variables.put(bits[0], value);
        }
        if (variables == null) {
            System.out.println(expression + " = " + exp.eval().toString());
            assertEquals(expression, new BigDecimal(args[args.length - 1]), exp.eval());
        } else {
            System.out.println(expression + " = " + exp.eval(variables).toString());
            assertEquals(expression + " with " + variables, new BigDecimal(
                    args[args.length - 1]), exp.eval(variables));
        }
    }

    @Test
    public final void testEval2() {
        eval("x<='Y' || y < 5", "x=Y", "y=10", "1");
        eval("x<'Y' || y < 5", "x=Y", "y=10", "0");
        eval("x || y < 5", "x=true", "y=10", "1");

    }

    @Test
    public final void testEval3() {
        String expression = "date > '2009/01/02' && date > '2008-01-10'";
        Map<String, Object> variables = new HashMap<String, Object>();
     
        variables.put("date", new Date());
        Expression exp = new Expression(expression);
        System.out.println(expression + " = " + exp.eval(variables).toString());
        assertEquals(expression + " with " + variables, BigDecimal.ONE, exp.eval(variables));
    }

    @Test
    public final void testEval() {
        eval("(1+2)*(1 + x)/2", "x=3", "" + ((1 + 2) * (1 + 3) / 2));
        eval("1 + 2*3 - x/2", "x=8", "" + (1 + 2 * 3 - 8 / 2));
        eval("1 + 2*3 - x/2 + 7", "x=8", "" + (1 + 2 * 3 - 8 / 2 + 7));
        eval("1 + 2 + 3 * 4 * 5 / y - x + 8 - 9", "y=6", "x=7", "" + (1 + 2 + 3 * 4 * 5 / 6 - 7 + 8 - 9));
        eval("(((1 + (2 + 3)) * 4)) * 5 / (y - x + (8 - 9))", "y=6", "x=7", "" + ((((1 + (2 + 3)) * 4)) * 5 / (6 - 7 + (8 - 9))));
        eval("1", "" + 1);
        eval("-1", "" + (-1));
        eval("2 + -1", "" + (2 + -1));
        eval("+2++1", "" + (+2 + +1));
        eval("1000 + (10 + 200) + 300", "" + (1000 + (10 + 200) + 300));

        /* Examples of values from BigDecimal java doc */
        eval("0", "0");
        eval("123", "123");
        eval("-123", "-123");
        eval("1.23E3", "1.23E3");
        eval("1.23E+3", "1.23E+3");
        eval("12.3E+7", "12.3E+7");
        eval("12.0", "12.0");
        eval("12.3", "12.3");
        eval("0.00123", "0.00123");
        eval(".00123", "0.00123");
        eval("-1.23E-12", "-1.23E-12");
        eval("1234.5E-4", "1234.5E-4");
        eval(".5E-4", ".5E-4");
        eval("-.5E-4", "-.5E-4");
        eval("+.5E-4", "+.5E-4");
        eval("0E+7", "0E+7");
        eval("-0", "-0");

        eval("x ? y : z", "x=1", "y=2", "z=3", "2");
        eval("x ? y : z", "x=0", "y=2", "z=3", "3");
        eval("x>y ? x*4-(2*x) : y*(3+1)", "x=3", "y=2", "z=3", "6");
        eval("1000 + (10 > 1 ? 100 : 200) + 300", "" + (1000 + (10 > 1 ? 100 : 200) + 300));
        eval("1000 + (10 > 1 ? 323+100 : 542-200) + 300", "" + (1000 + (10 > 1 ? 323 + 100 : 542 - 200) + 300));
        eval("1000+(10>1?(323+100):(542-200))+300", "" + (1000 + (10 > 1 ? (323 + 100) : (542 - 200)) + 300));
        eval("(1000+(10>1?(323+100):(542-200))+300)", "" + ((1000 + (10 > 1 ? (323 + 100) : (542 - 200)) + 300)));
        eval("1000 + 10 > 11 - 2 ? 100 : 200 + 300", "" + (1000 + 10 > 11 - 2 ? 100 : 200 + 300));
        eval("22/7", new BigDecimal("22").divide(new BigDecimal("7"),
                MathContext.DECIMAL128).toPlainString());
        eval("22%7", new BigDecimal("22").remainder(new BigDecimal("7"),
                MathContext.DECIMAL128).toPlainString());
        eval("15%4", "" + (15 % 4));
        eval("(-15)%4", "" + ((-15) % 4));
        eval("-x", "x=4", "-4");
        eval("y * -x + 1", "y=2", "x=4", "-7");
        eval("-abs(x)", "x=2.5", "-2.5");
        eval("-abs x", "x=-2.5", "-2.5");
        eval("abs-x", "x=2.5", "2.5");
        eval("int x", "x=2.5", "2");
        eval("int y * int x", "y=4.2", "x=2.5", "8");
        eval("y pow x", "y=4", "x=2", "16");
        eval("y", "y=4", "4");

        try {
            eval("y pow x", "y=4", "x=2.5", "16");
            fail("can only use integer operand to pow");
        } catch (RuntimeException re) {
            assertEquals("pow argument: Rounding necessary", re.getMessage());
        }
        eval("y pow int x", "y=4", "x=2.5", "16");

        eval("1 > 2 ? 100 : 200", "200");
        eval("2 > 1 ? 100 : 200", "100");

        eval("1 >= 2 ? 100 : 200", "200");
        eval("2 >= 1 ? 100 : 200", "100");
        eval("2 >= 2 ? 100 : 200", "100");

        eval("2 < 1 ? 100 : 200", "200");
        eval("1 < 2 ? 100 : 200", "100");

        eval("2 <= 1 ? 100 : 200", "200");
        eval("1 <= 2 ? 100 : 200", "100");
        eval("2 <= 2 ? 100 : 200", "100");

        eval("2 <> 2 ? 100 : 200", "200");
        eval("2 <> 1 ? 100 : 200", "100");

        eval("2 != 2 ? 100 : 200", "200");
        eval("2 != 1 ? 100 : 200", "100");

        eval("2 == 1 ? 100 : 200", "200");
        eval("2 == 2 ? 100 : 200", "100");

        eval("x > y && x != 4 ? x : y", "x=2", "y=1", "2");
        eval("x > y && x != 4 ? x : y", "x=4", "y=1", "1");
        eval("x > y && x != 4 ? x : y", "x=1", "y=3", "3");

        eval("x > y || x != 4 ? x : y", "x=2", "y=1", "2");
        eval("x > y || x != 4 ? x : y", "x=3", "y=1", "3");
        eval("x > y || x != 4 ? x : y", "x=4", "y=1", "4");
        eval("x > y || x != 4 || x<y? x : y", "x=2", "y=3", "2");

        /*
         * Bug in 0.4 - where a bracket (or ':' in ternary) terminates more than
         * one expression.
         */
        eval("(1 + 2 * 3)", "" + (1 + 2 * 3));
        eval("(1 + 2 * 3 ) ", "" + (1 + 2 * 3));
        eval("2*(1 + 2 * 3 ) + 1", "" + (2 * (1 + 2 * 3) + 1));
        eval("9 + 2*(1 + 2 * 3 ) + 1", "" + (9 + 2 * (1 + 2 * 3) + 1));

        eval("(1 + 2 * 3) * 2", "" + ((1 + 2 * 3) * 2));
        eval("((1 + 2 * 3) * 2)", "" + ((1 + 2 * 3) * 2));
        eval("(2 > 1 + 2 * 3)", (2 > 1 + 2 * 3) ? "1" : "0");
        eval("(2 > 1 + 2 * 3) ", (2 > 1 + 2 * 3) ? "1" : "0");
        eval("2 > 1 + 2 * 3", (2 > 1 + 2 * 3) ? "1" : "0");
        eval("2 > 1 + 2 * 3 ", (2 > 1 + 2 * 3) ? "1" : "0");
        eval("0 ? 1 : (2 < 1 + 2 * 3)", (2 < 1 + 2 * 3) ? "1" : "0");
        eval("0 ? 1 : 2 < 1 + 2 * 3", (2 < 1 + 2 * 3) ? "1" : "0");
        eval("1 ? (2 < 1 + 2 * 3):0", (2 < 1 + 2 * 3) ? "1" : "0");
        eval("1 ? 2 < 1 + 2 * 3:0", (2 < 1 + 2 * 3) ? "1" : "0");
        eval("2 < 1 ? 1 : 1 + 2 * 3", "" + (2 < 1 ? 1 : 1 + 2 * 3));
        eval("2 > 1 ? 1 + 2 * 3 : 0", "" + (2 > 1 ? 1 + 2 * 3 : 0));

        eval("(c > 0 || d > 0)", "a=3", "b=2", "c=1", "d=4",
                (1 > 0 || 4 > 0) ? "1" : "0");

        eval("(a > 0 || b > 0) && (c > 0 || d > 0)", "a=3", "b=2", "c=1",
                "d=4", ((3 > 0 || 2 > 0) && (1 > 0 || 4 > 0)) ? "1" : "0");
    }
}
