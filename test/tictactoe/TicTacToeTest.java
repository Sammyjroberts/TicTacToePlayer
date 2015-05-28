/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tictactoe;

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
public class TicTacToeTest {
    
    public TicTacToeTest() {
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
     * Test of headToHead method, of class TicTacToe.
     */
    @Test
    public void testHeadToHead_Player_Player() {
        System.out.println("headToHead");
        PlayerRandom p1 = new PlayerRandom();
        sroberts39 p2 = new sroberts39();
        TicTacToe instance = new TicTacToe();
        instance.headToHead(p2, p1,4000);

    }

    
}
