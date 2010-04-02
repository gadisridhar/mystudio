/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.yy.base.dao.dynamic;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author yyi
 */
public class ParserTest {

    public ParserTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @Test
    public void testParse() {
        System.out.println("parse");
        String where = "SELECT * FROM person where {IF(a>0){ C LIKE :C } AND {A like :A} AND {{lastName = :lastName} OR {firstName = :firstName} OR {B = :B}}}";
        Parser instance = new Parser();
       
        Expression result = instance.parse(where);
        assertNotNull( result);
        System.out.println(result.toString());
        SequenceExpression lexp = (SequenceExpression) result;
        assertNotNull( lexp);
        assertEquals(2, lexp.getExpressions().size());
        

    }

        @Test
    public void testParse2() {
        System.out.println("parse");
        String where = "SELECT * FROM person where p.id exists (select id from user where {IF(a>0){ C LIKE :C } AND {A like :A} AND {{lastName = :lastName} OR {firstName = :firstName} OR {B = :B}}})";
        Parser instance = new Parser();

        Expression result = instance.parse(where);
        assertNotNull( result);
        System.out.println(result.toString());
//        LogicExpression lexp = (LogicExpression) result;
//        assertNotNull( lexp);
//        assertEquals(3, lexp.getExpressions().size());


    }

}