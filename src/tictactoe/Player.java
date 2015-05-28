package tictactoe;
import tictactoe.*;

/**
 * abstract parent class of all players, requires user to implement 
 * <ul>
 * <li>makeMove</li>
 * <li>newGame</li>
 * <li>youWon</li>
 * <li>youLost</li>
 * <li>tie</li>
 * </ul>
 * Nota Bene, Constructor implementation.<br />
 * You need to create a default null constructor, the first line in the
 * null constructor must be super( String realname, String avatar), notethereby calling
 * Player has no default null constructor, Player( String realname, String avatar );
 * Where realname and avatar are set in VitalStatisics.
 * Your <strong>real name is FirstName LastName Term, e.g., Joe Student SP14</strong>
 * <br />
 * You may also want to override these methods...
 * <ul>
 * <li>badMove - or just pay attention to the comments</li>
 * <li>badMoveOpponent - does nothing now</li>
 * </ul>
<br />
 Player class also maintains the
 * current players's token (i.e., Board.XCHAR or Board.OCHAR), but to use this token during
 * game play your inherited class
 * must call setToken whenever a new game begins.<br />
 * You should also override the methods badMove and badMoveOpponent
<br />
 *For <a href="http://cim.saddlebck.edu/~tdedonno/submit">Submit</a> your sub-class and file name must
 * be <span title="Saddleback User Name - first initial last name unique number">SBUsername</span> also make
 * sure all friendly classes have the preface of your sbusername.
 *
@author = Tom DeDonno 3/10/02
 * @version most recent added full set of tictactoe event handler abstract method names 11/24/2010
 */
public abstract class Player extends VitalStatistics {

    // Note tictactoe has a token each board either Board.XCHAR or Board.OCHAR
    private char token;

      /**
     * no default constructor, therefore user must have a default constructor that 
     * sets the Saddleback username, note your class name getClass().getName() and 
     * Saddleback username but both be the same, note vital statistics checks this name
     *   @param realname your realname including the Term
     *   @param avatar avatar aka combat name
     *  
     */
    protected Player( String realName, String avatar ) {
        super( realName, avatar );
    }
    
    /**
     * last set token either Board.OCHAR | Board.XCHAR, XCHAR always moves first
     * @return returns the most recently set token either Board.OCHAR | Board.XCHAR
     */
    public final char getToken() {
        return token;
    }

    /**
     * sets players token
     * @param t set current token for player either Board.OCHAR or Board.XCHAR
     */
    public final void setToken(final char t) {
        token = t;
    }

    /**
     * Should have been a abstract method, but user can override this in the player subclass
     * called when the player has bad a bad move either off the board, onto an occupied square or returned a null coordinate
     * method prints out descriptive warning messages for the first three bad moves.
     * @param move move number bad move occurred on
     * @param brd brd with attempted bad move
     * @param c coordinate representing bad move
     * @param gameState gameState leading up to bad move
     */
    private int count = 0; //a static method variable requires a static method
    public void badMove( int move, Board brd, Coordinate c, long gameState  )
    {
        if( ++count > 3 ) return;  //print out first 3 times only
       System.out.print( "Player Class: " +getClass() + "," + getRealName() +  
                         " on move "  + move );
       System.out.printf( " state=%x\n", gameState );
       if( c == (Coordinate)null )
           System.out.println( " of null coordinate" );
           else if( ! brd.legal(c) )
                System.out.println( " off board:" + c );
            else
                System.out.println( "onto occuppied Square: "  + c );
        return;
    }

    /**
     * Your Opponent has made a bad move, take advantage of it, method does nothing.
     * this should have been an abstract method, but the need was found way after the program was operational
     * @param move move number bad move occurred on
     * @param brd brd with attempted bad move
     * @param c coordinate representing bad move
     * @param gameState gameState leading up to bad move
     */
    public void badMoveOpponent( int move, Board brd, Coordinate c, long gameState  )
    {
          return;
    }

    /**
     * Abstract methods must be declared in all sub-class children
     *        first move is always Board.XCHAR.
     * @param move move number 1..9 or Message.ID1..Message.ID9
     * @param brd current state of tic tac toe board
     * @return the Coordinate that you want to guess
     */
    public abstract Coordinate makeMove(int move, Board brd);

    /**
     * Abstract method return true if you want to play another game
     * @param opponent opponents real name
     * @param t Your peg either XChar or OChar
     * @return true if you want to play another game
     */
    public abstract boolean newGame(final String opponent, final char t);

    /**
     * Call when user has won the Game, you can return a response.
     * @param brd final board with you as winner
     * @return what you want to say to the other user, which for now is ignored
     */
    public abstract String youWon(final Board brd);

    /**
     * Call when tie has occurred, you can return a response.
     * @param brd final board
     * @return what you want to say to the other user, which for now is ignored
     */
    public abstract String tie(final Board brd);

    /**
     * Call when user has Lost the Game, you can return a response.
     * @param brd final board
     * @return what you want to say to the other user, which for now is ignored
     */
    public abstract String youLost(final Board brd);

    /**
     * creates a string representing the current token and player name and avatar name
     * @return string representing the players identity
     */
    @Override
    public String toString() {
        return "Hi I'm using Token: " + token + super.toString();
    }
}
