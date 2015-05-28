package tictactoe;

/**
<p>Creates a tictactoe simulator to test a tic tac toe player </p>
 *
 * Program provides these testing simulators...
 * <ol>
 * <li>headToHead( Player p1, Player p2 )
 * <li>singleUserTestMode( Player p1 ); but you need a playerRandom();
 * <li>tournament( Player[] p, int nGames, boolean roundRobin &deg; )
 * </ol>
 * &deg; roundRobin set to true for roundRobin, else only first player, plays all other players. For submit, submitted player, plays against multiple players
 * <br />
 * <h3>Debug Mode</h3>
 * <p>TicTacToe has a debug mode constructor, use it to make the engine more verbose</p>
 * Each mode creates an inner PlayerType class consisting of Player
Pointer, Board Pointer, Performance Rating and Message Pointer. Whenever you
 * you play more than 10 games (default outputPerformance value) system
 * will output a performance rating, else it outputs the final board. For a long
 * series (number of games exceeding property keepQuiet, default 5000) program has limited output.
 *<br /><br />
 * class might have dead code.
 *
 @author  tom dedonno
 @date Not Sure First Version always date and version programs
 @version 1.0 3/10/2002
 @version 2.0 Upgrade from passing messages to multiple Abstract Methods/
 @version 3.0 added badMove to the system
 @version 3.1 2/2014 fixed output from null coordinates on verbose modes
 */
public class TicTacToe {

    /**
     * set to true if you need debug information from the simulator,
     * note you will have to add a main method to this class in order to use debug mode.
     */
    private boolean myDebug = false;
    /**
     * will output Performance rating when ever the user plays as least
     * outputPerformance games, else it outputs final board at end of each game
     */
    private int outputPerformance = 10;
    /**
     * cut off for quiet mode, which is not completely quiet
     */
    private int keepQuiet = 2000;
    // should probably reduce this to just one variable
    private boolean silent = false;

    /** default constructor, used to create simulator/engine */

    public TicTacToe() {  }
    /**
     * Can be used to turn on <strong>Debug Mode</strong> Program can set the three private
     * variables myDebug, outputPerformance, and keepQuiet
     * @param my set to true for to set myDebug - produces verbose output
     * @param output minimum number of games to play to get performance rating, default 10
     * @param keep number of games in which above output is silent,default 2000.
     */
    public TicTacToe( boolean my, int output, int keep )
    { myDebug = my; outputPerformance = output; keepQuiet = keep; }


    private void sendMessage(PlayerType p1, PlayerType p2, int msgID) throws Exception {
        //p1 is winner or tie
         throw new Exception( "sendMessage not updated" );
/*
        if( msgID == Message.IDTIE ) 
        p2.msg.msgID = msgID;
        p2.performance.update( p2.msg );
        if( msgID.id >= msgID.ID1 && msgID.id <= msgID.ID9 )
            p1.p.makeMove( msgID.id, p1.b );

            case Message.IDWON : msgID = Message.IDLOSS; break;
            case Message.IDLOSS : msgID = Message.IDWON; break;
        }
        p2.msg.msgID = msgID;
        p2.performance.update( p2.msg );
        p2.p.makeMove( p2.msg, p2.b );  */

    }

    /**
     * later on start up a separate thread
     *         each time you want to play a game
     */
    private PlayerType[]  playGame(Player p1, Player p2, int nGames) {

        PlayerType p[] = new PlayerType[2];
        int i;
        Board brd = new Board();  //internal board

        //System.out.println( "Playing game" + p1 + "vs" + p2 + "for " + nGames );

        p[0] = new PlayerType( p1 );
        p[1] = new PlayerType( p2 );

        for( int n = 0; n < nGames ; ++n ) {

            brd.reset( );
            i = n%2; //Alternate who goes first.
            if( i == 0 ) {
                p[0].token = Board.XCHAR;
                p[1].token = Board.OCHAR;
            } else {
                p[0].token = Board.OCHAR;
                p[1].token = Board.XCHAR;
            }
            // See if both players want to play another game
            if( ! p[0].p.newGame(  p[1].p.getCombatName(), p[0].token ) ||
                ! p[1].p.newGame( p[0].p.getCombatName(), p[1].token ) )
                    return p;

            /* we will be checking for winner on each turn;
             * once you find winner send loser message to loser
             * and winner message to winner
             *
             * note tie occurs when you have all 9 played and no winner
             */
            { //make move and c scoped to local
                int move = Message.ID1;
                Coordinate c = (Coordinate)null;

                boolean badMoveState = false;
               
                while( move <= Message.ID9 ) {
                    
                    //This may be inefficient but, this allows the user to
                     
                    p[i].b.copy( brd );
                    p[i].moveTime = System.nanoTime();
                    try {
                      c = p[i].p.makeMove( move, p[i].b );
                      }
                    catch( Exception e ) {
                         // Note null doesn't turn on bad board state, in the past it was caused by a previous badMove
                        // have been getting NullPointerException from trying to encode a null c pointer
                        // or ArrayIndexOutOfBoundsException from makeMove
                        if( myDebug ) {
                            System.out.println( "Exception in makeMove:" + e );
                            e.printStackTrace();
                        }
                    }
   
                    p[i].moveTime = System.nanoTime() - p[i].moveTime;
                    
                    p[i].performance.update( p[i].moveTime );

                     // Game state is recorded even bad guesses
                    brd.encodeGameState( c, move );
                    
                  
                    if( myDebug )
                    System.out.println( "Move,Coordinate,Token:" + move + ", " +
                    c + ", " + p[i].token );

                    if(  c != (Coordinate)null &&  brd.legal( c ) && brd.emptyToken( c ) )
                        brd.setToken( p[i].token, c );
                    else {
			if( ! silent ) {
                            System.out.print(  p[i].p.getCombatName() + " Error: " );
                            if( c == (Coordinate)null ) System.out.print( "you returned null coordinate" );
                            else  {
                                System.out.print( c.toString() );
                                if( ! brd.emptyToken( c )  ) System.out.print( " is not empty");
                            }
                            System.out.println( "\n" + brd.toString( ) );
			    }
                        p[i].p.badMove(  move, p[i].b, c, brd.getGameState() );
                        p[ (i+1)%2 ].p.badMoveOpponent( move, p[i].b, c, brd.getGameState()  );
                        if( ! badMoveState ) p[i].performance.updateBadMove(  p[i].b,  c  );
                        badMoveState = true;
                    }
                    
                    if( myDebug )
                       System.out.printf( "State: %x, Coordinate %s\n",  brd.getGameState(    ), c  );
                    if( brd.winner() ) {
                        if( !silent && (nGames < 1000 ||
                        (nGames > 1000 && n%100 == 0)) ) {
                            System.out.println( "Game " + n +
                            " Has a a winner: " + p[i].token + p[i].p.getCombatName() );
                            if( nGames < 10 ) System.out.println( "board..." + brd );
                        }
                        //sendMessage( p[i], p[ (i+1)%2], Message.IDWON );
                        p[i].b.copy( brd );
                        p[i].p.youWon(  p[i].b );
                        p[i].performance.update( Message.IDWON, 0L );
                        int i1 = (i+1)%2;
                        p[ i1 ].b.copy( brd );
                        p[ i1 ].p.youLost( p[ i1 ].b );
                        p[ i1 ].performance.update( Message.IDLOSS, brd.getGameState() );
                        break;
                    }

                    if( myDebug )
                        System.out.format( "%s %x\n", "Move " + move + "Player " +
                        p[i].token + p[i].p.getCombatName() + "Board -> " + brd +
                        "\nint = ", brd.toInt() );
                    i = (i+1)%2;
                    ++move;


                } //end while( move 0 );

                // premature break kicks out a winner
                if( move > Message.ID9 ) {
                    p[0].b.copy( brd ); p[1].b.copy( brd );
                    p[0].p.tie( p[0].b );
                    p[1].p.tie( p[1].b );
                    p[0].performance.update( Message.IDTIE, 0L );
                    p[1].performance.update( Message.IDTIE,  0L );
                    if( ! silent &&
                    (nGames < 1000 || (nGames > 1000 && n%100 == 0)) ){
                        System.out.println( "Game " + n + " is a Tie" );
                        if( nGames < outputPerformance ) System.out.println( "brd=\n" + brd );
                        }
                }
            } //move and c scope ends

        } //end of for

        if( nGames > outputPerformance ) {
            System.out.println( "p0: " + p[0] );
            System.out.println( "vs  " +  p[1] );
        }
        return p;
    }

    private int count = 1;
    /**
     * You need to create a PlayerRandom,
     * this will play 1 game against playerRandom
     * 
     * @param p1 plays 1 game against PlayerRandom
     */
    public void singleUserTestMode(final Player p1) {

        playGame( p1, new PlayerRandom(), 1 );
        ++count;

    }
    
   /**
    * Runs single user testmode with myDebug set to true; Whenon
    * set to true tictactoe engine is very verbose.
    * @param p1 Player to test against PlayerRandom()
    * @param myDebug setting for internal myDebug variable 
    */
    public void singleUserTestMode(final Player p1, final Boolean myDebug ) {

        this.myDebug = myDebug;
        playGame( p1, new PlayerRandom(), 1 );
        ++count;

    }

    private String getMessage( )
    {
        return "Last Message" + count;
    }

    /**
     * Player p1 plays 5 games against Player p2
     * @param p1 is player 1
     * @param p2 is player 2
     */
    public void headToHead(Player p1, Player p2) {

        playGame( p1, p2, 5 );

    }
    /**
     * Player P1 against P2 in nGames, if nGames > keepQuiet,
     * methods produces limited output, consisting of only final stats.
     *
     * @param p1 plays against player p2
     * @param p2 second player
     * @param nGames number of games they will play
     */
    public void headToHead( Player p1, Player p2, int nGames ) {

        silent = (nGames > keepQuiet ? true : false );
        playGame( p1, p2, nGames );
    }

    /**
     * Array of players is used to create a Round-robin tournament,
     * Round-Robin is  all-play-all tournament using headToHead w nGames.
     * If roundRobin is false then player, plays all other players
     * @param players array of players
     * @param nGames number of games in each head to head confrontation
     * @param roundRobin if true play a round round, else player[0] plays each player
     */
    public void tournament( Player[] players, int nGames, boolean roundRobin ) {

        PlayerType[] pt = new PlayerType[ players.length ];
        PlayerType[] ph;
        int i, j;
        silent = (nGames >= keepQuiet ? true :  false);
        for( i=0; i < pt.length ; ++ i ) pt[i] = new PlayerType( players[i] );

        for( i=0; i < (roundRobin ? players.length - 1 : 1) ; ++i )
            for(  j=i+1; j < players.length ; ++j ) {
                ph = playGame( players[i], players[j], nGames );
                pt[i].performance.update( ph[0].performance );
                pt[j].performance.update( ph[1].performance );
            }
        System.out.println( "Final Results----\n" );
	java.util.Arrays.sort( pt );
        for( i=0; i < pt.length ; ++i )
            System.out.println( pt[i] );

        if( pt[0].performance.getLostState() > 0L ) {
            System.out.printf( "%s Last Game state Loss: %d  0x%x\n",
                        pt[0].p.getCombatName(), pt[0].performance.getLostState(), pt[0].performance.getLostState() );
            pt[0].b.setGameState( pt[0].performance.getLostState() );
            pt[0].b.printGameState();
        }
    }

    /**
     * user TicTacToe main method or goForIt to test your tictactoe player
     * @param args
     */
    public static void main(String[] args) {
        TicTacToe tic = new TicTacToe( );
        //tic.singleUserTestMode( new PlayerNext() );

        //tic.headToHead( new PlayerRandom(), new PlayerAce(), 50000 );
        //tic. myDebug = true;
       // tic.headToHead(    new PlayerAce2(), new PlayerRandom(), 10000 );
       // tic.headToHead(    new PlayerFQ(), new PlayerRandom(), 100 );
/*
        Player[] p = {
            new PlayerAce2(),
            new PlayerNext(),
            new PlayerFQ(),
            new PlayerAAAMASE(),
            new PlayerAce()
        };

        tic.tournament( p , 5000, false );
*/
        //tic.headToHead( new PlayerAce(), new PlayerAce() );
    }

    //inner private class
    private class PlayerType implements Comparable {

        //if class is private inner access modifier don't matter
        private Message msg;

        private Player p;

        private Board b;

        private  char token;

        private long moveTime;

        private PerformanceRating performance;

	public int compareTo( Object p )
	{
		return (int)(((PlayerType)p).performance.performance()*100) - (int)(performance.performance()*100);
	}

        private PlayerType(Player p1) {
            this.p = p1;
            b = new Board();
            msg = new Message( this, p1 );
            performance = new PerformanceRating( );
        }



        @Override
        public String toString( )
        {          
            return( String.format( "%-" + VitalStatistics.REALNAMEMAX + "." +
                    VitalStatistics.REALNAMEMAX + "s %-15.15s %s",
                    p.getRealName(),  p.getCombatName(),  performance ) );
        }

    }

}

