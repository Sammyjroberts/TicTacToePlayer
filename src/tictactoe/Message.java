package tictactoe;
/**
 *  TicTacToe engine used internally, Engine calls player eventHandlers
 * on Events. This should become a class called TicTacToeEvent which extends
 * java.util.EventObjects, Note MessageTemplate is deprecated
 * but Message.ID1 ... are set in stone
 *

 @author  tom dedonno
  */
public class Message extends MessageTemplate {

    private static final String[] names = {
        "1st Move", "2nd Move", "3rd Move", "4th Move", "5th Move",
        "6th Move", "7th Move", "8th Move", "9th Move", "You Won",
        "You Lost", "Tie Game", "Another Game?", "Yes", "No" };

        /**
         * game players can assume ID1..ID9
         *         will always be in sequential order 1..9, you can also
         * assume that ID1..ID9 will always be available.
         */
        public static final int ID1 = 1;
        /** ID for move 2 */
        public static final int ID2 = 2;
        /** ID for move 3 */
        public static final int ID3 = 3;
        /** ID for move 4 */
        public static final int ID4 = 4;
        /** ID for move 5 */
        public static final int ID5 = 5;
        /** ID for move 6 */
        public static final int ID6 = 6;
        /** ID for move 7 */
        public static final int ID7 = 7;
        /** ID for move 8 */
        public static final int ID8 = 8;
        /** ID for move 9 */
        public static final int ID9 = 9;
        /** @deprecated ID for WON */
        public static final int IDWON = 10;
        /** @deprecated ID for LOSS */
        public static final int IDLOSS = 11;

        public static final int IDTIE = 12;

	/** details is your token either Board.XCHAR or OCHAR */
        public static final int IDNEWGAME = 13;

        public static final int IDYES = 14;

        public static final int IDNO = 15;

        public static final int IDGAMEOVER = 16;


        /**
         * To setAuthentication calls this constructor
         * @param cp is TicTacToe@PlayerType
         * @param p is player pointer
         */
        public Message(Object cp, Object p) {
            setAuthenication( cp, p );
        }

        public void setMove1() { msgID = ID1; }

        public void setMove2() { msgID = ID2; }

        public void setMove3() { msgID = ID3; }

        /**
         *  sets nextMove but does not check for winner, losser or tie;
         *  on move ID9 next move becomes IDNEWGAME
         */
        public int setNextMove() {

            if( msgID >= ID1 && msgID < ID9 ) msgID++;
            else switch( msgID ) {
                case IDYES:
                case IDNEWGAME: msgID = ID1;        break;
                case ID9      : msgID = IDGAMEOVER; break;
                case IDWON: case IDLOSS : case IDTIE:
                case IDGAMEOVER: msgID = IDNEWGAME; break;
            }
            return msgID;
        }
        /**
         * return ture if gameover, tie, won or loss
         */
        public boolean gameOver( )
        {
            return( msgID == IDGAMEOVER || msgID == IDTIE ||
            msgID == IDWON || msgID == IDLOSS );
        }

        public int getMove() { return msgID; }

        public void setWin() { msgID = IDWON; }

        public void setLoss() { msgID = IDLOSS; }

        public void setTie() { msgID = IDTIE; }

        public void setYes() { msgID = IDYES; }

        public void setNo() { msgID = IDNO; }

        /**
         * details is set to your token XChar or OChar
         */
        public void setNewGame(final char token) {
            msgID = IDNEWGAME;
            details = token;
        }

        public String getName() {
            return( (msgID>=0&&msgID<names.length?
            names[msgID] : "Message Error" ) );
        }

    @Override
        public String toString( ) {
            return( getName() );
        }

        /**
         * converts a string with \n into an HTML String <br \>
         */
        public String toHTML( String s )
        {
            return s.replaceAll( "\n", "<br \\>" );
        }

}

