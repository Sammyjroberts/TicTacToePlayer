package tictactoe;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class sroberts39 extends Player {

    //CONSTANTS
    private char myToken;
    private char enemyToken;
    
    protected sroberts39() {
        super( "Sammy Roberts Sp15", "Kekeoki" );
    }
    /*
    Rule 1: If I have a winning move, take it.
    Rule 2: If the opponent has a winning move, block it.
    Rule 3: If I can create a fork (two winning ways) after this move, do it.
    Rule 4: Do not let the opponent creating a fork after my move. (Opponent may block your winning move and create a fork.)
    Rule 5: Place in the position such as I may win in the most number of possible ways.
    */
    @Override
    public Coordinate makeMove(int move, Board brd) {
        Coordinate finalMove = new Coordinate(4);
        if (move % 2 == 0)
        {
            myToken = brd.OCHAR;
            enemyToken = brd.XCHAR;
        }
        else
        {
            myToken = brd.XCHAR;
            enemyToken = brd.OCHAR;
        }
        ArrayList<winStruct> myWins = getWin(myToken, brd);
        ArrayList<winStruct> enemyWins = getWin(enemyToken,brd);
        
        
        
        
        //Rule 1
        if(!myWins.isEmpty())
        {
            System.out.println("Rule 1");
            finalMove = myWins.get(0).winningMove;
        }
        //Rule 2
        else if(!enemyWins.isEmpty())
        {
            System.out.println("Rule 2");
            System.out.println(enemyWins.size());
            finalMove = enemyWins.get(0).winningMove;
        }
        //Rule 3
        
        else if(!(findFork(myToken,brd).equals(new Coordinate(-1,-1))))
        {
            System.out.println("Rule 3");
            finalMove = findFork(myToken,brd);
        }
        //Rule 4
        else if(!(findFork(enemyToken,brd).equals(new Coordinate(-1,-1))))
        {
            System.out.println("Rule 4");
            finalMove = findFork(enemyToken,brd);
        }
        //Rule 5
        else
        {
            System.out.println("Rule 5");
            Boolean keepLoopin = true;
            int lcv = 0;
            Coordinate badMove = new Coordinate();
            ArrayList<winStruct> check = new ArrayList();
            //Rule 5 part 1 if i can make a move that will lead to winning move, make it
            while(keepLoopin)
            {
                if(brd.emptyToken(lcv))
                {
                    Board temp = new Board();
                    temp.copy(brd);
                    temp.setToken(myToken, new Coordinate(lcv));
                    check = getWin(myToken, temp);

                    if(check.size() >= 1)
                    {
                        if((findFork(enemyToken,temp).equals(new Coordinate(-1,-1))))
                        {
                            badMove = findFork(enemyToken,temp);
                        }
                        else
                        {

                            finalMove = new Coordinate(lcv);
                            keepLoopin = false;
                        }
                    }
                }
                lcv++;
                if(lcv == 9)
                {
                    keepLoopin = false;
                    //Take center > take corner > take whatever
                    if(brd.emptyToken(4) && badMove != (new Coordinate(4)))
                    {

                        finalMove = new Coordinate(4);
                    }
                    else if(brd.emptyToken(0) && badMove != (new Coordinate(0)))
                    {

                        finalMove = new Coordinate(0);
                    }
                    else if(brd.emptyToken(2) && badMove != (new Coordinate(2)))
                    {

                        finalMove = new Coordinate(2);
                    }
                    else if(brd.emptyToken(6) && badMove != (new Coordinate(6)))
                    {

                        finalMove = new Coordinate(6);
                    }
                    else if(brd.emptyToken(8) && badMove != (new Coordinate(8)))
                    {

                        finalMove = new Coordinate(8);
                    }
                    else // TODO make o's AI better at preventing strong forks
                    {
                        int randInt;
                        Random rand = new Random();
                        randInt = rand.nextInt(9);
                        keepLoopin = true;
                        while(keepLoopin)
                        {
                            if(brd.legal(new Coordinate(randInt)))
                            {

                                finalMove = new Coordinate(randInt);
                                keepLoopin = false;
                            }
                        }
                    }
                }
            }

        }

        return finalMove;
    }
    
    
    //Make move functions/classes
    class winStruct
    {
        public final String ROW = "ROW";
        public final String COL = "COL";
        public final String DIAG0 = "DIAG0";
        public final String DIAG2 = "DIAG2";
        public String winType = "";
        public Coordinate winningMove = new Coordinate();
    }
    private Coordinate findFork(char token, Board brd)
    {
        Coordinate fork = new Coordinate();
        ArrayList<winStruct> check = new ArrayList();
        Boolean keepLoopin = true;
        int lcv = 0;
        while(keepLoopin)
        {

            if(brd.emptyToken(lcv))
            {
                Board temp = new Board();
                temp.copy(brd);
                temp.setToken(token, new Coordinate(lcv));
                check = getWin(token, temp);

                if(check.size() > 1)
                {
                    fork = new Coordinate(lcv);
                    keepLoopin = false;
                }
            }
            lcv++;
            if(lcv > 8)
            {

                keepLoopin = false;
            }
        }
        return fork;
    }
    private ArrayList<winStruct> getWin(char token, Board brd)
    {
        ArrayList<winStruct> win = new ArrayList();
        //Row Check 
        //Row 1
        if(brd.getToken(0) == token && brd.getToken(1) == token && brd.getToken(2) == brd.EMPTYCHAR)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.ROW;          // type of win
            temp.winningMove = new Coordinate(2); //Coord of win
            win.add(temp); // add to win array
        }
        if(brd.getToken(0) == token && brd.getToken(1) == brd.EMPTYCHAR && brd.getToken(2) == token)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.ROW;          // type of win
            temp.winningMove = new Coordinate(1); //Coord of win
            win.add(temp); // add to win array
        }
        if(brd.getToken(0) == brd.EMPTYCHAR && brd.getToken(1) == token && brd.getToken(2) == token)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.ROW;          // type of win
            temp.winningMove = new Coordinate(0); //Coord of win
            win.add(temp); // add to win array
        }
        
        
        //Row 2
        if(brd.getToken(3) == token && brd.getToken(4) == token && brd.getToken(5) == brd.EMPTYCHAR)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.ROW;          // type of win
            temp.winningMove = new Coordinate(5); //Coord of win
            win.add(temp); // add to win array
        }
        if(brd.getToken(3) == token && brd.getToken(4) == brd.EMPTYCHAR && brd.getToken(5) == token)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.ROW;          // type of win
            temp.winningMove = new Coordinate(4); //Coord of win
            win.add(temp); // add to win array
        }
        if(brd.getToken(3) == brd.EMPTYCHAR && brd.getToken(4) == token && brd.getToken(5) == token)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.ROW;          // type of win
            temp.winningMove = new Coordinate(3); //Coord of win
            win.add(temp); // add to win array
        }
        
        
        //Row 3
        if(brd.getToken(6) == token && brd.getToken(7) == token && brd.getToken(8) == brd.EMPTYCHAR)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.ROW;          // type of win
            temp.winningMove = new Coordinate(8); //Coord of win
            win.add(temp); // add to win array
        }
        if(brd.getToken(6) == token && brd.getToken(7) == brd.EMPTYCHAR && brd.getToken(8) == token)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.ROW;          // type of win
            temp.winningMove = new Coordinate(7); //Coord of win
            win.add(temp); // add to win array
        }
        if(brd.getToken(6) == brd.EMPTYCHAR && brd.getToken(7) == token && brd.getToken(8) == token)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.ROW;          // type of win
            temp.winningMove = new Coordinate(6); //Coord of win
            win.add(temp); // add to win array
        }
        //END ROW CHECK
        //COL check
        //Col 1
        if(brd.getToken(0) == token && brd.getToken(3) == token && brd.getToken(6) == brd.EMPTYCHAR)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.COL;          // type of win
            temp.winningMove = new Coordinate(6); //Coord of win
            win.add(temp); // add to win array
        }
        if(brd.getToken(0) == token && brd.getToken(3) == brd.EMPTYCHAR && brd.getToken(6) == token)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.COL;          // type of win
            temp.winningMove = new Coordinate(3); //Coord of win
            win.add(temp); // add to win array
        }
        if(brd.getToken(0) == brd.EMPTYCHAR && brd.getToken(3) == token && brd.getToken(6) == token)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.COL;          // type of win
            temp.winningMove = new Coordinate(0); //Coord of win
            win.add(temp); // add to win array
        }
        
        
        //Col 2
        if(brd.getToken(1) == token && brd.getToken(4) == token && brd.getToken(7) == brd.EMPTYCHAR)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.COL;          // type of win
            temp.winningMove = new Coordinate(7); //Coord of win
            win.add(temp); // add to win array
        }
        if(brd.getToken(1) == token && brd.getToken(4) == brd.EMPTYCHAR && brd.getToken(7) == token)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.COL;          // type of win
            temp.winningMove = new Coordinate(4); //Coord of win
            win.add(temp); // add to win array
        }
        if(brd.getToken(1) == brd.EMPTYCHAR && brd.getToken(4) == token && brd.getToken(7) == token)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.COL;          // type of win
            temp.winningMove = new Coordinate(1); //Coord of win
            win.add(temp); // add to win array
        }
        
        // col 3
        if(brd.getToken(2) == token && brd.getToken(5) == token && brd.getToken(8) == brd.EMPTYCHAR)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.COL;          // type of win
            temp.winningMove = new Coordinate(8); //Coord of win
            win.add(temp); // add to win array
        }
        if(brd.getToken(2) == token && brd.getToken(5) == brd.EMPTYCHAR && brd.getToken(8) == token)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.COL;          // type of win
            temp.winningMove = new Coordinate(5); //Coord of win
            win.add(temp); // add to win array
        }
        if(brd.getToken(2) == brd.EMPTYCHAR && brd.getToken(5) == token && brd.getToken(8) == token)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.COL;          // type of win
            temp.winningMove = new Coordinate(2); //Coord of win
            win.add(temp); // add to win array
        }
        //End COL
        
        
        //DIAG0
        if(brd.getToken(0) == token && brd.getToken(4) == token && brd.getToken(8) == brd.EMPTYCHAR)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.DIAG0;          // type of win
            temp.winningMove = new Coordinate(8); //Coord of win
            win.add(temp); // add to win array
        }
        if(brd.getToken(0) == token && brd.getToken(4) == brd.EMPTYCHAR && brd.getToken(8) == token)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.DIAG0;          // type of win
            temp.winningMove = new Coordinate(4); //Coord of win
            win.add(temp); // add to win array
        }
        if(brd.getToken(0) == brd.EMPTYCHAR && brd.getToken(4) == token && brd.getToken(8) == token)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.DIAG0;          // type of win
            temp.winningMove = new Coordinate(0); //Coord of win
            win.add(temp); // add to win array
        }
        
        //DIAG2
        //DIAG0
        if(brd.getToken(2) == token && brd.getToken(4) == token && brd.getToken(6) == brd.EMPTYCHAR)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.DIAG2;          // type of win
            temp.winningMove = new Coordinate(6); //Coord of win
            win.add(temp); // add to win array
        }
        if(brd.getToken(2) == token && brd.getToken(4) == brd.EMPTYCHAR && brd.getToken(6) == token)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.DIAG2;          // type of win
            temp.winningMove = new Coordinate(4); //Coord of win
            win.add(temp); // add to win array
        }
        if(brd.getToken(2) == brd.EMPTYCHAR && brd.getToken(4) == token && brd.getToken(6) == token)
        {
            winStruct temp = new winStruct(); // Temporary obj
            temp.winType = temp.DIAG2;          // type of win
            temp.winningMove = new Coordinate(2); //Coord of win
            win.add(temp); // add to win array
        }
        return win;
    }
    
    
    
    
    
    
    @Override
    public boolean newGame(String opponent, char t) {
        super.setToken(t);
         return true;
    }

    @Override
    public String youWon(Board brd) {
        System.out.println("Won boys: ");
        System.out.println(brd.toString());
        return brd.toString();
    }

    @Override
    public String tie(Board brd) {
        System.out.println("Tie boys: ");
        System.out.println(brd.toString());
        return brd.toString();
    }

    @Override
    public String youLost(Board brd) {
        System.out.println("lose wtf: ");
        System.out.println(brd.toString());
        return brd.toString();
    }
    
    
    public static void main( String[] args )
    {
        sroberts39 instance = new sroberts39();
        Board test = new Board();
        Scanner sc = new Scanner(System.in);
        boolean won = false;
        int move = 1;
        while(!won)
        {
            System.out.println(test.toString());
            System.out.println("Your turn: ");
            test.setToken(test.XCHAR, new Coordinate(sc.nextInt()));
            move++;
            test.setToken(test.OCHAR, instance.makeMove(move, test));
            move++;
        }
    }

}
