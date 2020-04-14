package com.cherry.thomas.app.scripter.model;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class ReversePolishNotationTest extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ReversePolishNotationTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( ReversePolishNotationTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testRPN()
    {
        ReversePolishNotation rpn = new ReversePolishNotation();
        
        rpn.clear();
        rpn.calculate ("3:3 1:2 5 +:2 - / *");
        helper_check_two_values(rpn, "[-2.25]");

        rpn.clear();
        rpn.calculate("+ print");
        helper_check_two_values(rpn, "[]");
        
    }
    void helper_check_two_values(ReversePolishNotation rpn, String expected)
    {
        String ans = rpn.toString();
        assertTrue(expected.equals(ans));
    }
}
