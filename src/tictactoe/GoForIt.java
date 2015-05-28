package tictactoe;
/**
 * Just a sample system testing program, main function can call any of the
 * tictactoe engine simulators: tournament, headToHead, singleUserTestMode, etc.
 * note it is advantageous to create multiple players for testing. You should first write a random
 * player that doesn't guess an occupied square. <strong>Players used
 * in this file are not available to students.</strong>
 * @author tdedonno
 */
public class GoForIt {


 /**
  * sample main testing method, you need to create TicTacToe engine, a player object
  * and then call one of the  simulator methods, tournament, headToHead or singleUserTestMode
  * You will need to comment out Player you don't have access to
  * @param args
  */
 public static void main(String[] args) {

         //Create Java TicTacToe Engine
        TicTacToe tic = new TicTacToe( );
        /* You don't have these players
	   you need to create your own, the only constructor your player should have is
	   null constructor with a call to base class constructor( String, String )
	   your player name should be your sbUsername */
       /*  Class<Player> c;
        Object obj = (Object)null;
        Player pTest = (Player)null;
        
        if( args.length > 1 )
        
      //Create the Player 13 class
        try {  obj = Class.forName( "playerFriday13").newInstance();
        //p = (Player)Class.forName( "playerFriday13").newInstance(); }
        } catch( Exception e ) { System.err.println( e ); } */
        
        //create constructor
/*        
        Player p[] = {
       
                 //new playerFriday13(), 
                 new PlayerAce3(),
                 new PlayerAce2( ),
                 new PlayerAce3SS( ),
                 new PlayerAAAMASE(),
                 //new PlayerWRK(),
                 new PlayerAce(),
                 new PlayerNext(),
                 new PlayerFQ(),    
                 new PlayerStupid(),
                 new PlayerDZ2(),
                 new PlayerRandomMiddle(),
                 new PlayerRandom(),
                 new PlayerRandom1(),
                 new PlayerRandomBadMove()
	};

       tic.tournament( p, 50000, true ); 

		PlayerRandom pRandom = new PlayerRandom();
		 for( int i = 0; i < p.length ; ++i )
		{
			System.out.println( "Starting " + p[i] );
			tic.headToHead( p[1], p[i], 5000 );
			System.out.println( "Done " );
		}  
*/
	//tic.singleUserTestMode( new dhoffman18() );
        
        //tic.headToHead( new PlayerRandom(), new mdurand1(), 10000 );

        //PlayerRandom Plays Himself

        //tic.headToHead( new PlayerRandom(),  new PlayerNext(), 250000 );
        }
}
