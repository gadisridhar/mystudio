/*
 * Copyright 2008  Reg Whitton
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.yy.base.eval;

import java.math.BigDecimal;
import java.math.MathContext;
import org.apache.commons.beanutils.ConvertUtils;
import org.yy.base.util.ConverterHelper;

public abstract class Operator {

    /**
     * End of string reached.
     */
   public static Operator END = new Operator(-1, 0, null, null, null) {

        @Override
        Object perform(Object value1, Object value2,
                Object value3) {
            throw new RuntimeException("END is a dummy operation");
        }
    };
    /**
     * condition ? (expression if true) : (expression if false)
     */
     public static Operator TERNARY = new Operator(0, 3, "?", null, null) {

        @Override
        Object perform(Object value1, Object value2,
                Object value3) {
            if (value1 instanceof BigDecimal) {
                return (BigDecimal) ((((BigDecimal) value1).signum() != 0) ? value2 : value3);
            } else {
                Boolean bool = ConverterHelper.getInstance().convert(Boolean.class, value1);
                return (bool ? value2 : value3);
            }
        }
    };
    /**
     * &amp;&amp;
     */
    public static Operator AND = new Operator(0, 2, "&&", Type.BOOLEAN, Type.BOOLEAN) {

        @Override
        Object perform(Object value1, Object value2, Object value3) {

            Boolean bool1 = ConverterHelper.getInstance().convert(Boolean.class, value1);
            Boolean bool2 = ConverterHelper.getInstance().convert(Boolean.class, value2);


            return bool1 && bool2 ? BigDecimal.ONE : BigDecimal.ZERO;
        }
    };
    /**
     * ||
     */
   public static Operator  OR = new Operator(0, 2, "||", Type.BOOLEAN, Type.BOOLEAN) {

        @Override
        Object perform(Object value1, Object value2, Object value3) {
            Boolean bool1 = ConverterHelper.getInstance().convert(Boolean.class, value1);
            Boolean bool2 = ConverterHelper.getInstance().convert(Boolean.class, value2);
            return bool1 || bool2 ? BigDecimal.ONE : BigDecimal.ZERO;
        }
    };
    /**
     * &gt;
     */
    public static Operator  GT = new Operator(1, 2, ">", Type.BOOLEAN, Type.ARITHMETIC) {

        @Override
        Object perform(Object value1, Object value2, Object value3) {
            if (value1 == value2 || value1 == null) {
                return BigDecimal.ZERO;
            }
            if (value2 == null) {
                return BigDecimal.ONE;
            }

            Object obj2 = ConverterHelper.getInstance().convert(value1.getClass(), value2);
            return ((Comparable) value1).compareTo(obj2) > 0 ? BigDecimal.ONE : BigDecimal.ZERO;
        }
    };
    /**
     * &gt;=
     */
    public static Operator GE = new Operator(1, 2, ">=", Type.BOOLEAN, Type.ARITHMETIC) {

        @Override
        Object perform(Object value1, Object value2, Object value3) {
            if (value1 == value2 || value2 == null) {
                return BigDecimal.ONE;
            }
            if (value1 == null) {
                return BigDecimal.ZERO;
            }

            Object obj2 = ConverterHelper.getInstance().convert(value1.getClass(), value2);
            return ((Comparable) value1).compareTo(obj2) >= 0 ? BigDecimal.ONE : BigDecimal.ZERO;

        }
    };
    /**
     * &lt;
     */
   public static Operator LT = new Operator(1, 2, "<", Type.BOOLEAN, Type.ARITHMETIC) {

        @Override
        Object perform(Object value1, Object value2, Object value3) {
            if (value1 == null && null != value2) {
                return BigDecimal.ONE;
            }
            if (value2 == null || value1 == value2) {
                return BigDecimal.ZERO;
            }

            Object obj2 = ConverterHelper.getInstance().convert(value1.getClass(), value2);
            return ((Comparable) value1).compareTo(obj2) < 0 ? BigDecimal.ONE : BigDecimal.ZERO;

        }
    };
    /**
     * &lt;=
     */
   public static Operator LE =  new Operator(1, 2, "<=", Type.BOOLEAN, Type.ARITHMETIC) {

        @Override
        Object perform(Object value1, Object value2, Object value3) {

            if (value1 == value2 || value1 == null) {
                return BigDecimal.ONE;
            }
            if (value2 == null) {
                return BigDecimal.ZERO;
            }

            Object obj2 = ConverterHelper.getInstance().convert(value1.getClass(), value2);
            return ((Comparable) value1).compareTo(obj2) <= 0 ? BigDecimal.ONE : BigDecimal.ZERO;

        }
    };
    /**
     * ==
     */
   public static Operator EQ =  new Operator(1, 2, "==", Type.BOOLEAN, Type.ARITHMETIC) {

        @Override
        Object perform(Object value1, Object value2,
                Object value3) {
            if (value1 == value2) {
                return BigDecimal.ONE;
            }
            if (value2 == null || value1 == null) {
                return BigDecimal.ZERO;
            }

            Object obj2 = ConverterHelper.getInstance().convert(value1.getClass(), value2);
            return ((Comparable) value1).compareTo(obj2) == 0 ? BigDecimal.ONE : BigDecimal.ZERO;

        }
    };
    /**
     * != or &lt;&gt;
     */
   public static Operator NE = new Operator(1, 2, "!=", Type.BOOLEAN, Type.ARITHMETIC) {

        @Override
        Object perform(Object value1, Object value2, Object value3) {
            if (value1 == null && null == value2) {
                return BigDecimal.ZERO;
            }
            if (value1 == null || value2 == null) {
                return BigDecimal.ONE;
            }

            Object obj2 = ConverterHelper.getInstance().convert(value1.getClass(), value2);
            return ((Comparable) value1).compareTo(obj2) != 0 ? BigDecimal.ONE : BigDecimal.ZERO;

        }
    };
    /**
     * +
     */
   public static Operator ADD = new Operator(2, 2, "+", Type.ARITHMETIC, Type.ARITHMETIC) {

        @Override
        Object perform(Object value1, Object value2, Object value3) {
            value2 = ConverterHelper.getInstance().convert(value1.getClass(), value2);
            if (value1 instanceof BigDecimal) {
                return ((BigDecimal) value1).add((BigDecimal) value2);
            } else if (value1 instanceof String) {
                return ((String) value1) + ((String) value2);
            } else {
                throw new RuntimeException("Only plus can be applied to String and Nubmer type.");
            }

        }
    };
    /**
     * -
     */
    public static Operator SUB = new Operator(2, 2, "-", Type.ARITHMETIC, Type.ARITHMETIC) {

        @Override
        Object perform(Object value1, Object value2, Object value3) {
            BigDecimal val1 = ConverterHelper.getInstance().convert(BigDecimal.class, value1);
            BigDecimal val2 = ConverterHelper.getInstance().convert(BigDecimal.class, value2);
            return (val1).subtract(val2);
        }
    };
    /**
     * /
     */
   public static Operator DIV = new Operator(3, 2, "/", Type.ARITHMETIC, Type.ARITHMETIC) {

        @Override
        Object perform(Object value1, Object value2, Object value3) {
            BigDecimal val1 = ConverterHelper.getInstance().convert(BigDecimal.class, value1);
            BigDecimal val2 = ConverterHelper.getInstance().convert(BigDecimal.class, value2);
            return (val1).divide(val2, MathContext.DECIMAL128);
        }
    };
    /**
     * %
     */
   public static Operator REMAINDER = new Operator(3, 2, "%", Type.ARITHMETIC, Type.ARITHMETIC) {

        @Override
        Object perform(Object value1, Object value2, Object value3) {
            BigDecimal val1 = ConverterHelper.getInstance().convert(BigDecimal.class, value1);
            BigDecimal val2 = ConverterHelper.getInstance().convert(BigDecimal.class, value2);
            return (val1).remainder(val2, MathContext.DECIMAL128);
        }
    };
    /**
     * *
     */
   public static Operator MUL = new Operator(3, 2, "*", Type.ARITHMETIC, Type.ARITHMETIC) {

        @Override
        Object perform(Object value1, Object value2, Object value3) {
            BigDecimal val1 = ConverterHelper.getInstance().convert(BigDecimal.class, value1);
            BigDecimal val2 = ConverterHelper.getInstance().convert(BigDecimal.class, value2);
            return (val1).multiply(val2);
        }
    };
    /**
     * -negate
     */
   public static Operator NEG = new Operator(4, 1, "-", Type.ARITHMETIC, Type.ARITHMETIC) {

        @Override
        Object perform(Object value1, Object value2, Object value3) {
            BigDecimal val1 = ConverterHelper.getInstance().convert(BigDecimal.class, value1);
            return (val1).negate();
        }
    };
    /**
     * +plus
     */
   public static Operator PLUS = new Operator(4, 1, "+", Type.ARITHMETIC, Type.ARITHMETIC) {

        @Override
        Object perform(Object value1, Object value2, Object value3) {
            BigDecimal val1 = ConverterHelper.getInstance().convert(BigDecimal.class, value1);
            return val1;
        }
    };
    /**
     * abs
     */
   public static Operator ABS = new Operator(4, 1, " abs ", Type.ARITHMETIC, Type.ARITHMETIC) {

        @Override
        Object perform(Object value1, Object value2, Object value3) {
            BigDecimal val1 = ConverterHelper.getInstance().convert(BigDecimal.class, value1);
            return (val1).abs();
        }
    };
    /**
     * pow
     */
   public static Operator POW = new Operator(4, 2, " pow ", Type.ARITHMETIC, Type.ARITHMETIC) {

        @Override
        Object perform(Object value1, Object value2, Object value3) {
            try {
                BigDecimal val1 = ConverterHelper.getInstance().convert(BigDecimal.class, value1);
                BigDecimal val2 = ConverterHelper.getInstance().convert(BigDecimal.class, value2);
                return (val1).pow((val2).intValueExact());
            } catch (ArithmeticException ae) {
                throw new RuntimeException("pow argument: " + ae.getMessage());
            }
        }
    };
    /**
     * int
     */
  public static Operator  INT = new Operator(4, 1, "int ", Type.ARITHMETIC, Type.ARITHMETIC) {

        @Override
        Object perform(Object value1, Object value2, Object value3) {
            BigDecimal val1 = ConverterHelper.getInstance().convert(BigDecimal.class, value1);
            return new BigDecimal((val1).toBigInteger());
        }
    };
    /**
     * No operation - used internally when expression contains only a reference
     * to a variable.
     */
   public static Operator NOP = new Operator(4, 1, "", Type.ARITHMETIC, Type.ARITHMETIC) {

        @Override
        Object perform(Object value1, Object value2, Object value3) {
            BigDecimal val1 = ConverterHelper.getInstance().convert(BigDecimal.class, value1);
            return val1;
        }
    };

    public static Operator createFunction(String functionName) {

        return new Operator(4, 1, functionName, Type.ARITHMETIC, Type.ARITHMETIC) {

            @Override
            Object perform(Object value1, Object value2, Object value3) {
                if(this.string.equalsIgnoreCase("NOW")){
                    return new java.util.Date();
                }
                return BigDecimal.ONE;
            }
        };

    }
    final int precedence;
    final int numberOfOperands;
    final String string;
    final Type resultType;
    final Type operandType;

    Operator(final int precedence, final int numberOfOperands,
            final String string, final Type resultType, final Type operandType) {
        this.precedence = precedence;
        this.numberOfOperands = numberOfOperands;
        this.string = string;
        this.resultType = resultType;
        this.operandType = operandType;
    }

    abstract Object perform(Object value1, Object value2, Object value3);
}
