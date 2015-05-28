/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package tictactoe;

/**
 *
 * @author edougherty1
 */
public class PlayerRandom  extends Player{

    public PlayerRandom() {
        super("Player Random Sp15", "Typical IVC");
    }
    
    
    
    @Override
    public Coordinate makeMove(int move, Board brd) {
        System.out.println("Move #: "+ move);
        Coordinate c = new Coordinate( (int)(Math.random()*Board.MAXROWS), 
                                       (int)(Math.random()*Board.MAXCOLS) );
       
        while( !brd.emptyToken(c))
        {
            c.next();
        }
        
        return c;      
    }

    @Override
    public boolean newGame(String opponent, char t) { return true; }

    @Override
    public String youWon(Board brd) { return "You Won"; }

    @Override
    public String tie(Board brd) { return "Tie Game"; }

    @Override
    public String youLost(Board brd) { return "You Lost"; }
    
}
