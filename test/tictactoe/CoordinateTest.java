/*
 * Author Sammy Roberts; CS4B; Coordinate Test; 1/28/2015
 * omg.yeah.wai@hotmail.com will add more tests if you want
 */
package tictactoe;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author sammy
 */
public class CoordinateTest {
    
    public CoordinateTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    
    /**
     * Test of clone method, of class Coordinate.
     */
    @Test
    public void testClone() {
        System.out.println("clone");
        Coordinate instance = new Coordinate();
        instance.set(1);
        Object expResult = instance;
        Object result = instance.clone();
        assertEquals(expResult, result);
    }

    /**
     * Test of copy method, of class Coordinate.
     */
    @Test
    public void testCopy() {
        System.out.println("copy");
        Coordinate c = new Coordinate();
        c.set(1);
        Coordinate instance = new Coordinate();
        instance.copy(c);
        assertEquals(instance, c);
    }

    /**
     * Test of set method, of class Coordinate.
     */
    @Test
    public void testSet_int_int() {
        System.out.println("set");
        int r = 0;
        int c = 0;
        Coordinate instance = new Coordinate();
        Coordinate expResult = new Coordinate();
        expResult.set(0);
        Coordinate result = instance.set(r, c);
        assertEquals(expResult, result);
    }

    /**
     * Test of set method, of class Coordinate.
     */
    @Test
    public void testSet_Coordinate() {
        System.out.println("set");
        ArrayList<Coordinate> c = new ArrayList<Coordinate>();
        ArrayList<Coordinate> r = new ArrayList<Coordinate>();
        
        //Prepare tests
        c.add(new Coordinate());
        c.add(new Coordinate());
        c.add(new Coordinate());
        c.get(0).set(1);
        c.get(1).set(2);
        c.get(2).set(3);
        
        //test
        r.add(new Coordinate());
        r.add(new Coordinate());
        r.add(new Coordinate());
        r.get(0).set(c.get(0));
        r.get(1).set(c.get(1));
        r.get(2).set(c.get(2));
        for(int lcv = 0; lcv < 3; lcv++)
        {
            assertEquals(c.get(lcv), r.get(lcv));
        }
        
    }

    /**
     * Test of set method, of class Coordinate.
     */
    @Test
    public void testSet_int() {
        System.out.println("set");
        int i = 0;
        Coordinate instance = new Coordinate();
        Coordinate expResult = new Coordinate();
        expResult.set(0);
        Coordinate result = instance.set(i);
        assertEquals(expResult, result);

    }

    /**
     * Test of next method, of class Coordinate.
     */
    @Test
    public void testNext() {
        System.out.println("next");
        Coordinate instance = new Coordinate();
        instance.set(1);
        Coordinate expResult = new Coordinate();
        expResult.col = 2;
        expResult.row = 0;
        Object result = instance.next();
        assertEquals(expResult, result);
    }

    /**
     * Test of isLegalRow method, of class Coordinate.
     */
    @Test
    public void testIsLegalRow() {
        System.out.println("isLegalRow");
        Coordinate instance = new Coordinate();
        instance.set(3, 1);
        boolean expResult = false;
        boolean result = instance.isLegalRow();
        assertEquals(expResult, result);
    }

    /**
     * Test of isLegalCol method, of class Coordinate.
     */
    @Test
    public void testIsLegalCol() {
        System.out.println("isLegalCol");
        Coordinate instance = new Coordinate();
        instance.set(1, 3);
        boolean expResult = false;
        boolean result = instance.isLegalCol();
        assertEquals(expResult, result);
    }

    /**
     * Test of isLegal method, of class Coordinate.
     */
    @Test
    public void testIsLegal() {
        System.out.println("isLegal");
        Coordinate instance = new Coordinate();
        instance.set(3, 3);
        boolean expResult = false;
        boolean result = instance.isLegal();
        assertEquals(expResult, result);
    }

    /**
     * Test of singleton method, of class Coordinate.
     */
    @Test
    public void testSingleton() {
        System.out.println("singleton");
        Coordinate instance = new Coordinate();
        instance.set(0,0);
        int expResult = 0;
        int result = instance.singleton();
        assertEquals(expResult, result);
    }



    /**
     * Test of equals method, of class Coordinate.
     */
    @Test
    public void testEquals_Object() {
        System.out.println("equals");
        Coordinate p1 = new Coordinate(1,1);
        Coordinate instance = new Coordinate(1,1);
        boolean expResult = true;
        boolean result = instance.equals(p1);
        assertEquals(expResult, result);

    }

    /**
     * Test of equals method, of class Coordinate.
     */
    @Test
    public void testEquals_Coordinate() {
        System.out.println("equals");
        Coordinate c = new Coordinate();
        c.col = 1;
        c.row = 1;
        Coordinate instance = new Coordinate();
        instance.set(1,1);
        boolean expResult = true;
        boolean result = instance.equals(c);
        assertEquals(expResult, result);
    }   
}
