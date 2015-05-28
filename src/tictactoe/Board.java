package tictactoe;
import java.util.Enumeration;
import java.util.ArrayList;
/**
 *
 *
 *
 *  Used to define and create the tic tac toe board; Your player should never have
 * the number 3, or 'X' in its code always user the Board constants which are...
 * <ol>
 * <li>Constants MAXROWS and MAXCOLS
 * <li>Board tokens: EMPTYCHAR, XCHAR and OCHAR
 * <li>char Board[ MAXROWS ][ MAXCOLS ] - not a constant
 * </ol>
 * <br />Class also defines a full set of methods....
 * <ol>
 * <li>Board state can be represented as a single integer
 * <li>Entire game state is represented as a single long integer
 * <li>Check for board winner
 * <li>Copy and clone board
 * <li>Check for legal moves
 * <li>Get & Set board token by either Coordinate or row,col.
    </ol>
 * <hr />
*
 * Board is just a char matrix board[ MAXROWSxMAXCOLS ] in which its contents are
 * EMPTYCHAR, XCHAR and OCHAR, Class does
 * include code for intBoard and boardInt which can be used to
 * represents the entire  3x3 tictactoe board state as a single integer
 *(i.e.,  9Cells * 2 = 18 Bits; 14 bits left over).
 *
 *
 @author  tom dedonno
 @version last modified 11/26/2010 added javaDoc Comments, 1/15/2012 Added GameState methods
 */
public class Board implements Cloneable {

    /** maximum number of rows default is 3 */
    public static final int MAXROWS = 3;

    /** maximum number of cols default is 3 */
    public static final int MAXCOLS = 3;

    /** default EMPTYCHAR used in board */
    public static final char EMPTYCHAR = '_';

    /**  X always goes first */
    public static final char XCHAR = 'X';
    /** 2nd turn default character '0' */
    public static final char OCHAR = 'O';

    /**
     * User has full access to the board
     */
    public char[][] board;

    private int r;  //used internally row
    private int c;  //column
    private long state; //current state of the board
    /**
     * Creates a new Board instance
     */
    public Board() {

        board = new char[ MAXROWS ][ MAXCOLS ];
        reset( );
    }

    /**
     * Used to encode game State, first 4 bits are first move, second 4 bits second move,
     * (4 * 9 = 36 bits) bits 33-36 are the last Move. Each move is the coordinate singleton + 1, 
     * therefore the tictactoe board is recorded as...
     * <pre>
     *  1 |  2 | 3
     *  4 |  5 | 6
     *  7 |  8 | 9
     * </pre>
     * Normal equation for singleton is row*3+col, but 
     * you cannot record a state as 0, therefore game state moves are 
     * row*3+col + 1, note difference Coordinate singleton is 0..8,
     * board game state position is 1..9;<pre>
     * 1 | 2 | 3
     * 4 | 5 | 6
     * 7 | 8 | 9
     * <ul>
     * <li>The game state 0x159, X first move 9; O move 2 is 5;move 3 X is 1</li>
     * <pre>
     * X _ _ 
     * _ O _
     * _ _ 9
     * </pre>
     * <li> Sets off board set all 4 bits (aka 0xf).</li>
     * <li>e.g., 0x12f45, On X's second move (game move 3)
     * X picked a Coordinate outside the tictactoe range.</li>
     * <li>Duplicate guesses onto occupied square are just saved</li>
     * <li>e.g., 0x121 implies X has used position 1 on both his
     * first and second move</li>
     * <li>Null coordinate usually caused by exception is saved as 0xE</li>
     * <li>e.g., 0x1E3; implies on game move 2, O first move, O throw an exception
     * most likely causes index array out of bounds</li>
     * @param c coordinate of current move
     * @param move move number of game first move is Message.ID1, last is Message.ID9  (9 squares)
     * @return current gameState as long
     * @see Coordinate
     */
    public long encodeGameState( final Coordinate c, int move )
    {
        move -= Message.ID1;
        /* move should be between 0..8, but ID1 is 1,  */
        if( move == 0 ) state = 0; //reset game state
        //not want to store 0 values as singleton
        long singleton;
        if( c == (Coordinate)null )
            singleton = 0xe;
        else {
            if( c.row >= MAXCOLS || c.row < 0 || c.col >= MAXCOLS || c.col < 0 )
                singleton = 0xf;
            else singleton = c.row*MAXCOLS + c.col +1;
            }
        // can have situations where this fails if( singleton > 9 || singleton <= 0 ) singleton = 0xf;
       return state |=  singleton<<(4*move);
    }

    /**
     * use current game state and creates the 9 ArrayList Coordinates representing all 9 moves,
     * note if game ended in fewer than 9 moves, then ArrayList will have less elements, note it
     * is using the standard Coordinate singleton row*3+col. Normal gamestate uses the value 0
     * to represent a guess out of bounds. 
     * @return ArrayList Pointer of game state up to 9 array elements
     * @see ArrayList
     */
    public ArrayList<Coordinate> decodeGameState(  )
    {
        java.util.ArrayList<Coordinate> ca = new java.util.ArrayList<Coordinate>( );

        for( int i=0; i < 9 && state > 0 ; ++i ) {
            int singleton = (int)(state&0xf) - 1;
            state >>= 4;
            ca.add(  new Coordinate( singleton  ) );
        }
         return ca;
    }

    /**
     * print current game  state,
     * @see setCurrentGameState
     */
    public void printGameState(  )
    {
        ArrayList<Coordinate> a = decodeGameState(  );
        printGameState( a );
    }

    /**
     * sets current Game state and then calls printGameState no arguments
     * @param s value of game state you wish to use, note modifies private data state
     */
    public void printGameState( final long s )
    {
        state = s; printGameState( );
    }

    /**
     * Displays the Boards representing the game state
     * @param a arrayList of up to  9 Coordinates
     */
    public void printGameState( ArrayList<Coordinate> a )
    {
        for( int row=0; row < MAXROWS ; ++row )
            for( int col=0; col < MAXCOLS ; ++col ) board[row][col] = EMPTYCHAR;

        int i;
        //no threads use StringBuilder
        StringBuilder[] line =new StringBuilder[ 7 ];
        for( i=0; i < line.length ; ++i ) line[i] = new StringBuilder( );

       for(  i=0; i < a.size() ; ++i ) {
            Coordinate ca = (Coordinate)a.get( i );
            board[ ca.row ][ ca.col ] = (i%2 == 0 ? XCHAR : OCHAR );
            String[] str = String.format( "%c%s  \n%s",   (i%2==0?XCHAR:OCHAR) , ca, this ).split( "\n", line.length );
            for( int j=0 ; j < str.length ; ++j ) {
                    //if( j == 1 )continue; //skip Print Board...
                    line[j].append(  String.format( "%7.7s ", str[j] )  );
                    }
            }
        for( i=0; i < line.length ; ++i )
            System.out.println(  line[i].toString()  );
        }
    /**
     * Sets game state, so that printGameState will operate on current game State,
     * since you are changing private data gameState didn't want to write printGameState( state ),
     * @param state long representing the game state
     */
    public void setGameState( final long state )    {   this.state = state;    }
    public long getGameState( ) { return state; }

    /**
     *  Converts entire board to a single 18 bit unsigned int (9 Squares 2 Bits/Square)
     *
     *  first 2 LSbits are board location 0;
     *  bits  16 and 17 represent last board location, (i.e., lower right hand corner);
     *  values at location are 0 for blank;  1 for x;  2 for  O<br />
     * <pre>
     *  so a _X_                XO_   X is 0x1, O is 8
     *       ___                _O_    O is 2^8 = 64
     *       ___  is 0x00004   _ _ _  is 73 binary 1000 1001
     * </pre>
     * Note gameState is used to represented all 9 moves, this just represents current board
     * @return single integer representing the entire board
     */
    public int toInt() {

        int n = 0;

        for( r = MAXROWS-1; r >= 0 ; --r )
            for( c = MAXCOLS-1; c >= 0 ; --c ) {
                n <<= 2;
                n |= ( board[r][c] == XCHAR ? 1 :
                    (  board[r][c] == OCHAR ? 2 : 0 ));

            }

        return n;

    }

    /**
     * converts board to decimal integer
     *  0 blank  1 is XCHAR  2 is OCHAR
     * @return entire board translated to one integer
     */
    public int toInt10( )
    {
      int n = 0;

        for( r = 0; r < Board.MAXROWS ; ++r )
            for( c = 0; c < Board.MAXCOLS ; ++c ) {
                n *= 10;
                n += ( board[r][c] == Board.XCHAR ? 1 :
                     ( board[r][c] == Board.OCHAR ? 2 : 0 ) );
               }
      return n;
    }
    /**
     *
     *   LSB is last square so work backwards
     * @param i set board contents to singleton i
     * @return  always returns true;
     */
    public boolean toBoard10( int i )
    {
        //Note could set board to empty and quite when n is 0
        for( r = Board.MAXROWS-1;  r >= 0 ; --r )
            for( c = Board.MAXCOLS-1; c >= 0; --c ) {
                board[ r ][ c ] = ( i%10 == 1 ? Board.XCHAR :
                                  ( i%10 == 2 ? Board.OCHAR :
                                                Board.EMPTYCHAR) );
                i /= 10;
                }
       return true;
    }

    /**
     *  copies integer i (18 bit Unsigned int) into the calling board assumes i was set by toInt
     *@param i is the singleton representing the board
     * @return false on errors
     */
    public boolean toBoard( int i )
    {
        boolean nReturn = true;

        for( r = 0; r < MAXROWS ; ++r )
            for( c = 0 ; c < MAXCOLS ; ++c ) {
                int n = i&0x3;
                //Note I'm setting bad values to EMPTYCHAR
                board[r][c] = ( n==2 ? OCHAR  :
                              ( n==1 ? XCHAR : EMPTYCHAR ));
                if( n >= 3 ) nReturn = false;
                i >>= 2;
            }
        return nReturn;
    }
    /**
     *  Copies Board b into Calling Board this pointer
     * @param b copy board b contents into this
     */
    public void copy(Board b) {

        state = b.state;
        for( r = 0; r < MAXROWS ; ++r )
            for( c = 0; c < MAXCOLS ; ++c )
                board[r][c] = b.board[r][c];
    }

    /**
     * Creates a board clone
     */

    @Override
    public Object clone() {

        Board b = new Board();
        b.copy( this );
        b.state = state;
        b.r =r; b.c =c;
        return b;
    }

    /**
     *  Set entire board to EMPTYCHAR and state to 0L
     */
    public final void reset() {

        state = 0L;
        for( r = 0; r < MAXROWS ; ++r )
            for( c = 0; c < MAXCOLS ; ++c )
                board[r][c] = EMPTYCHAR;
    }

    /**
     * @param c character to check
     *  @return true if C is a legal board character
     */
    public boolean legalToken(final char c) {

        return( ( c == EMPTYCHAR || c == XCHAR || c == OCHAR)? true : false );
    }

    /**
     * Determine if board has a winner
     * @return true if we have a winner
     */
    public boolean winner() {


        //check rows
        for( r = 0; r < MAXROWS ; ++r )
            if( board[r][0] == board[r][1] && board[r][1] != EMPTYCHAR &&
            board[r][2] == board[r][1] ) return true;

        //check cols
        for( c = 0; c < MAXCOLS ; ++c )
            if( board[0][c] == board[1][c] && board[0][c] != EMPTYCHAR &&
            board[2][c] == board[1][c] ) return true;

        //check diagonals
        if( board[1][1] != EMPTYCHAR ) {
            if( board[1][1] == board[2][2] && board[2][2] == board[0][0] )return true;
            if( board[1][1] == board[0][2] && board[1][1] == board[2][0] )return true;
        }

        return false;
    }

    /**
     * check to see if c is in a legal location
     * @return true if Coordinate is in a legal location
     * @param c Coordinate to check for legality
     */
    public boolean legal( final Coordinate c )
    {
       if( c.row >= 0 && c.row < MAXROWS && c.col >= 0 && c.col < MAXCOLS )
           return true;
       else
           return false;
    }
    /**
     *   uses a singleton value where i = r*MAXROWS + c;
     *   @param i singleton position on board
     *   @return the token at singleton location i
     */
    public char getToken(final int i) {
        return( board[ i/MAXROWS ][ i%MAXCOLS ] );
    }

    /**
     * @param p coordinate on board
     * @return token at Coordinate p
     */
    public char getToken(final Coordinate p) {
        return( board[p.row][p.col] );
    }

    /**
     * Get token ar row,column
     * @param r row
     * @param c column
     * @return token at board[r][c]
     */
    public char getToken( final int r, final int c ) {
        return( board[r][c] );
    }

    /**
     * @param i singleton point on board i = row*MAXCOLS + col
     * @return true if empty Token at singleton location i
     */
    public boolean emptyToken(final int i) {
        return( board[i/MAXROWS][i%MAXCOLS] == EMPTYCHAR );
    }

    /**
     * @param r row,
     * @param c col
     * @return true if emptyTokey at r, c
     */
    public boolean emptyToken(final int r, final int c) {
        return( board[r][c] == EMPTYCHAR );
    }

    /**
     * @param p board coordinate
     * @return true if emptyToken at Coordinate p
     */
    public boolean emptyToken(final Coordinate p) {
        return( emptyToken( p.row, p.col ) );
    }

    /**
     *
     *  could make char c an enum type;
     *  enum types exist in J2SE5
     *
     *  set specified Token at position p in board,
     *
     *  @param t Token( EMPTYCHAR, XCHAR or OCHAR)
     *  @param c must be a legal Coordinate
     *  @return true if you can set token t at coordinate c
     */
    public boolean setToken(final char t, final Coordinate c) {

        if( c.isLegal() && legalToken( t ) ) {
            board[ c.row ][ c.col ] = t;
            return true;
        }
        else return false;

    }

    /**
     *  3by3 Representation of current board state extracted from char[][] board
     *  @return String representing the board
     */
    @Override
    public String toString() {
        String str = "Print Board...\n";

        for( r = 0; r < MAXROWS ; ++ r ) {

            for( c = 0 ; c < MAXCOLS ; ++ c)
                str += board[r][c];
            str += "\n";
        }
        return str;
    }
/**
 * just does a println of Board.toString()
 */
    public void println() {
        System.out.println( toString() );
    }

    public static void main( String[] args )
    {
        Board b = new Board( );

     
        b.setGameState( 0x6321 );
        b.printGameState(  );
        b.setGameState( 0x530871246L );
        b.printGameState(  );
    }

}

