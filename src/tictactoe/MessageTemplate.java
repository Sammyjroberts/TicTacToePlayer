package tictactoe;
/**
 * abstract Message SuperClass adopted from Battleship
 *
 * <p>
 *  Message SuperClass used by Battleship to send Message to Players
 * </p>
 *   Only TicTacToe may create authenicated messages. Has been made a template
 *   to facility the ability to send messages to other classes,
 *  SeriesRating and/or PerformanceRating, has authentication capabilities
 * but current tictactoe engine never passes player pointers.
 @author Tom DeDonno
 @version 3/24/02, 11/26/2010 modified JavaDoc
 @deprecated  used only by TicTacToe simulator engine
 *
 */
public abstract class MessageTemplate {

    protected int msgID;

    /**
     * Details of current message, child must be able to set this on demand
     */
    protected int details;

    /**
     * points to class object relative current message
     */
    protected long longDetails;

    protected Object classPtr;

    /**
     * set to the intended recipient, your player's this pointer
     */
    private Object recipient;

    /**
     * set to the authorized sender
     */
    private String sender;

    public MessageTemplate() {
        msgID = 0; details = -1; classPtr = (Object)null;
        recipient = (Object)null; sender = (String)null;
        // each message constructor must call this setAuthenication( cp, p );
    }

    public final void copy(MessageTemplate msg) { msgID = msg.msgID; details = msg.details;
    classPtr = msg.classPtr; recipient = msg.recipient;
    sender = msg.sender;
    }

    /**
     * @return true if sender and recipient are authorized
     *             @param forMe is your this player pointer
     */
    public final boolean checkAuthenication(Object forMe) { return (sender != null && forMe.equals( recipient )); }

    /**
     * used by battleship simulator to setAuthenication
     *         Set authenication between sender BattleShip$PlayerType and recipient player
     *         Authenicator called by constructor
     */
    protected final boolean setAuthenication(Object cp, Object r) {

        // Can only setAuthenication once
        if( sender != (String)null ) return false;
        //String authorized = "class BattleShip$PlayerType";
        String authorized = new String( (cp.getClass()).toString() );
        //String authorized2 = new String( r.toString() );

        if( authorized.equals( "class TicTacToe$PlayerType" ) ) {
            //System.out.print( "Authenication Verified->" + authorized + "<-" );
            sender = authorized;
            recipient = r;
            //System.out.println( "Between Sender " + sender + " And Recipient " + r.getClass() );
            return true;
        } else {
            System.out.println( "Fraudulent Authenication Attempt->\n" + cp.getClass() +
            "<-\n" + authorized  + "<-");
            sender = (String)null;
            return false;
        }

    }

    /**
     * @return returns signature of message sender
     */
    public final String getSignature() { return sender; }

    /**
     * @return intended recipient of message
     */
    public final Object getRecipient() { return recipient; }

    /**
     * @deprecated done by setMessageID
     */
    public void setClassPointer(Object cp) {
          /* System.out.println( "setting ClassPointer-> " + cp.toString() );
          System.out.println( "Runtime class -> " + cp.getClass() );
          System.out.println( "hash code -> " + cp.hashCode() ); */
        classPtr = cp;
    }

    /**
     * @return returns ClassPointer of current message
     *         note for message IDPlaceShips, classPtr represents the ship class
     *         to place your ships on
     */
    public Object getClassPointer() { return classPtr; }

    /**
     * @return details of current message
     */
    public final int getDetails() { return details; }

    public final long getLongDetails() { return longDetails; }

    /**
     * @return returns descriptive String name of current Message
     */
    public abstract String getName();

    @Override
    public String toString() {
        return( "Message[" + msgID + "]->" + getName() +
        "<- Details:" + getDetails() +
        " Class Pointer is " +
        (classPtr != null ? classPtr.getClass().getName() : "Not set" )
        + " Sender:" + sender + " Recipient:" + recipient );
    }

    public void println() {
        System.out.println( toString( ) );
    }

    public int getMsgID() { return msgID; }

    public int getMessage() { return msgID; }

}

