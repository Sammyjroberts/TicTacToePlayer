package tictactoe;
import tictactoe.*;
/*
 * PerformanceRating.java
 *
 * 
 */

/**
 * Keeps track of won, loss and ties, game state of last loss, number of moves,
 * and nanotime used to make all moves. TicTacToe engine automatically creates
 * a performance rating for each player. System doesn't maintain performance
 * in a file/Database. TicTacToe engine will print out performance stats but
 * doesn't return them to the calling program.
 * 
 * @author  tom dedonno
 * @version 2005, 11/2010 added time to PerformanceRating, 1/12 added lostState

 */
public class PerformanceRating {

    private int won = 0;
    private int loss = 0;
    private int tie = 0;
    private long moves = 0;
    private long moveTime = 0L;
    // Used to normalize performance relative different machines (is only set once)
    static private double weightMoveTime = -1.0;
    
    // this is not working
    // need to send in all players and use fastest +1 .. ace=0 .. slowest -1 as a normalized range
    
    private double normalizeMoveTime( )
    {/*
        long startTest = System.nanoTime();
        double z = 0;
        for( int i=0; i < 320000; ++i ) {
            double x = Math.random();
            double y = Math.pow( x, 3.2 );
           x = Math.sqrt(  y );
           z += x;
        }
    */       /*  ace2 move times,   startTest...
         *  linux cim2   354
         *  athlon linux 8700   1.7e8   1100
         *  windows home 580     4,426,332.0
         */

        //double timeLag = System.nanoTime() - startTest;
        //weightMoveTime = 1100 * timeLag/1.7e8;
        weightMoveTime = 1100*.4;
        //System.out.println( "timeLag, weightMoveTime is " + timeLag + ", " + weightMoveTime );
        return weightMoveTime;
    }
    //  updateNormalize
    
    //  normalize
    
    private class BadMove {
        long offBoard = 0;
        long occuppied = 0;
        long nullCoordinate = 0;
        public void update( Board brd, Coordinate c  ) {
            if( c == (Coordinate)null ) ++nullCoordinate;
            else if( !brd.legal( c ) ) ++offBoard;
            else ++occuppied;
        }
        public void update( BadMove b ) {
            offBoard += b.offBoard;  occuppied += b.occuppied; nullCoordinate += b.nullCoordinate;
        }
        @Override
        public String toString( )
        {
            return "OffBoard-Occuppied-Null: " + offBoard + "," + occuppied + "," + nullCoordinate;
        }

        public double  performance( double games ) { return (double)-1000.0*( ( offBoard + occuppied + nullCoordinate )/games ); }
    }

    private BadMove badMove = new BadMove( );
    /** I'm just saving the lastState, but would be easy
     * to modify to a Set, and save last 10 losses
     * @see  java.util.abstractSet
     */
    private long lostState = 0L; //Game state of last loss

    /**
     * take a PerformanceRating object and adds it to this object
     * @param pr object values to be added to this
     */
    public void update( final PerformanceRating pr )
    {
        won += pr.won;
        loss += pr.loss;
        tie += pr.tie;
        moves += pr.moves;
        moveTime += pr.moveTime;
        badMove.update( pr.badMove );
        if( pr.lostState > 0L ) lostState =pr.lostState;
    }

   /**
    * penalize a player who has bad a move off the board or
    * onto an occupied space, or returned a null Coordinate
    */
    public void updateBadMove( final Board brd, final Coordinate c )
    {
        badMove.update( brd, c );
    }
    /**
     * update time length of most recent move
     * @param mt time in nanoseconds
     */
    public void update( long mt )
    {
        ++moves;
        moveTime += mt;
    }

    /** Creates a new instance of PerformanceRating */
    public PerformanceRating() {
        
        if( weightMoveTime < 0.0 ) {
            weightMoveTime = normalizeMoveTime( );
            System.out.println( "weightMoveTime is " + weightMoveTime );
        }
    }

    /**
     * @deprecated
     * @param msg
     */
    public void update( Message msg )
    {
        update( msg.msgID );
    }

     /**
      * Used to update win, loss, tie and lostState on loss
      *       @param msgID set to either Message.IDWON, IDLOSS or IDTIE
      */
    public void update( final int msgID, final long state )
    {
       switch( msgID ) {
            case Message.IDLOSS : ++loss; lostState = state; break;
            case Message.IDWON  : ++won;  break;
            case Message.IDTIE  : ++tie;  break;
        }
    }

    /**
     * Performance should be relative a playerNext or playerRandom.
     * @return current performance rating
     */
    @SuppressWarnings("CallToThreadDumpStack")
    public double performance( ) {

        double nGames = won + loss + tie;

       try {
            if( nGames < 1000 ) return 0.0; 
            //throw new Exception( "nGames: " + nGames + " Need at least 1000 games to compute performance ");
                  
            if( moves == 0 )throw new Exception( "Zero Moves Doesn't make Sense - probably too many bad moves ");
            if( moveTime == 0 ) throw new Exception( "Move Time is Zero ");
            if( nGames == 0 ) throw new Exception( "no Games ");
      
        //too big of a hit on time
        //return 10.0*(won-3*loss)/nGames - m/500 + tie/nGames + (loss==0?1:0);
        if( weightMoveTime/(moveTime/moves) > 1.0 ) 
            System.out.println( "Performance Weight:" +  weightMoveTime/(moveTime/moves) + " moveTime/moves:" + moveTime/moves );
        /** need to record all moveTime/moves then normalize all values between 0..3 */
       
        return 11.0*(won-10*loss)/nGames + weightMoveTime/(moveTime/moves)  +  tie/nGames  +  (loss==0?1:0)  +  badMove.performance(  nGames );
        } catch( Exception e ) {
            System.out.println( "Returning -999.99 Error in performance: " + e.getMessage() );
            e.printStackTrace();
        }
        return -999.99;
        }
    /**
     * output a full set of performance stats
     * @return performance stats as a string
     */
    @Override
    public String toString( ) {

        double m = (double)moveTime/(double)moves;
        String str = String.format( "Won-Loss-Tie: %-20s nanoSec/Move=%8.1f%11.2f  %s",
        String.format("%d-%d-%d", won, loss, tie ),  m, performance( ),  badMove.toString()  );
        return( str );
    }
    
    /**
     * returns game state of last loss, class Board creates game State
     * @return game state of last Lost 
     * @see Board 
     */
    public long getLostState( ) { return lostState; }

}

