package tictactoe;
import tictactoe.*;
/**
 * Contains Vital Statistics on Player which for TicTacToe 
 * consists of real name (real name has to be Real Name and then Term)
 * .e.g., <br />
 * Joe Student Spring14 or Joe Student SP14<br />
 * Your avata name can be anything you want. When creating a new players one must
 * always call the Player constructor super( String, String ), this constructor
 * in term calls the only VitalStatistics constructor( String real, String combat)
 *
 *<br />
 * <pre>
 * Notes:
 *         All abstract classes must be inherited
 *         Variables can not be abstract only classes and methods
 * </pre>
 * <p>
 *         VitalStatistics String variables: real name w term, and alterEgo avatar,
 *         can only be set once
 *</p>
 *
 @author Tom DeDonno 3/10/02
 @version 11/2010 dropped missing string, 1/14 Updated real name
 */
public abstract class VitalStatistics {

    /**
     * actual real name of player
     */
    /**
     * Maximum length allowed for real name
     */
    public static final int REALNAMEMAX = 22;
    private String realName;  //real name which is your Saddleback Username

    /**
     * Note needed decided to go with public static constant
     * @return maximum length allowed for realName
     */
    public int getRealNameMax( ) { return REALNAMEMAX; }
        
    private String avatar; //aka combatName, avatar

    /**
     * set to true when VitalStatistics are set
     *         you may only set variables realName, alterEgo, mission once
     */
    private boolean fStatsSet = false;

    /**
     * Note this is private, which means no one outside can call it,
     * however once you define one constructor, default is no longer available
     */
    private VitalStatistics() {
        fStatsSet = false;
        System.err.println( "You need to call VitalStatistics Constructor" +
        "With Your real name, alter Ego, & mission in life" );
        System.exit( 0 );
        /*System.out.println( "Im in Vital Statistics" );*/
    }

    /**
     * Set players real name  and avatar, combat name alter-ego c
     * constructors helper method checks for legal Real names
     * @param rn is your Real namefollowed by Sp Last2Digits of Year you are taking cs4b
     * @param a avatar combat name of player
     */
    public VitalStatistics(final String rn, final String a ) { setVitalStatistics( rn, a ); }

    /**
     * @param rn Real name and term your taking CS4B
     * @param a avatar aka combat name
      */
    private void setVitalStatistics(final String rn, final String a ) {

         if( fStatsSet ) return; //Can only set realName... once
        fStatsSet = true;
        /* deprecated no longer support only user names
        if( rn.matches( "^[a-z]{2,}[0-9]*$" ) ) {
            realName=rn;
            System.out.print( "Deprecated used Real Name Term");
        } */
        if( rn.length() > REALNAMEMAX ) {
            System.out.println( "Real name with term has a " 
                    + REALNAMEMAX +              
                    " length limit yours " + rn + " is " +
                    rn.length() + " characters" +
                    "\nClass Name: " + getClass() );
            System.exit( 6 );
        }
        if( rn.matches( "^[A-Z][a-zA-Z]*[ -]?[A-Z][a-zA-Z]*[ -]?(F[aA].*10|S[pP].*[0-4][0-9])$" ) )
            realName = rn;
        else {
            System.out.print( "Your real name, needs to be set to your Real name w term\n");
            System.out.print( "For Example, Joe Student SP14\n");
            System.out.print(  "This string: " + rn + " is not a legal real name w term\n");
            System.out.print( "The submitted class must be your Saddleback Username\n" );
            System.out.print( "For random/test player set the name JS Random SP14\n" );
            System.out.print( "Class name: " + getClass( ) );
            System.exit( 5 );
        }
            
        avatar = a;
        }

    /**
     * @return True if VitalStatistics have been set
     */
    public boolean vitalStatisticsSet() { return fStatsSet; }


    /**
     * alias to getRealName (i.e., Real Name w term)
     * @return real name w term for player
     */
    public final String getCreator()  { return realName; }
    /**
     *
     * @return real name of player  w term
     */
    public final String getRealName() { return realName; }

    /**
     * alias to getAlterEgo, after movie its now called avatar
     * @return alterEgo combat name of player
     */
    public final String getCombatName() { return avatar; }
    /**
     * Alter Ego, after movie its now called avatar
     * @return alterEgo (aka avatar) combat name of player
     */
    public final String getAlterEgo() { return avatar; }

     /**
     * @return programmer's real name, and avatar 
     */
    @Override
    public String toString() {

        return String.format(
        "Real Name:%"+REALNAMEMAX+"s Avatar:%15s\n",
        realName, avatar );

    }

}

