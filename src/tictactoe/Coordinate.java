package tictactoe;
import tictactoe.*;

/**
 *
 * Used to check, create and move the board location.<ul>
 * <li>All board locations are just the coordinate row,column. 
 * <li>Upper left hand corner is point 0,0;
 * <li>lower right hand corner is MAXROWS-1, MAXCOLS-1; 
 * <li>Board.MAXROWS=Board.MAXCOLS=3 define board Limits
 * 
 * <br /><br />
 *  
 * Since board can have multiple coordinate, and since
 * a coordinate may not always be tied to a board, didn't
 * use inheritance - most of This class was copied from Battleship. 
 * Coordinates allows bad moves, could have implement enum type or exceptions
 * to prohibit bad moves, but in general this is a simulator, so bad moves
 * can be done.
 * <br />
 * Problem is that Coordinate and Board are intermingled.
 *
 *
 @author Tom DeDonno
 @see Board
 */

public class Coordinate implements Comparable, Cloneable {

    /**
     * row number, position of Coordinate
     */
    public int row;
    /**
     * column position of Coordinate
     */
    public int col;

    /**
     * Create a new Coordinate object, default is -1, -1
     */
    public Coordinate() { row = col = -1; }

    private static final int MAXROWS = Board.MAXROWS;
    private static final int MAXCOLS = Board.MAXCOLS;

    /**
     * Create  Coordinate set its position to row, column
     * @param r row
     * @param c colum
     */
    public Coordinate(final int r, final int c) { row = r; col = c; }

    /**
     * Create a new coordinate using coordinate c
     * @param c coordinate object to copy initial values of row, col
     */
    public Coordinate(final Coordinate c) { row = c.row; col = c.col; }

    /**
     * Create new coordinate based on singleton where  i = row*MAXCOLS + col
     * @param i singleton for coordinate row*MAXCOLS + col
     */
    public Coordinate( final int i) { set( i ); }

    /**
     * Convert current coordinate to a string (row, col)
     * @return string (row, col) 
     */
    @Override
    public String toString() { return new String( "(" + row + ", " + col + ")" ); }

    /**
     * print coordinate object to standard output
     */
    public void println() { System.out.println( toString() ); }

    /**
     * Clone the object, clone creates new object identical to the this object
     * @return new cloned object
     * @see java.lang.Cloneable
     */
    @Override
    public Object clone() {

        Coordinate c = new Coordinate( this );
        return c;
    }
    
    /**
     * Copy the Coordinate C values into this Coordinate object
     * @param c values to copy into this object
     */

    public void copy(final Coordinate c) { row = c.row; col = c.col; }

    /**
     * sets current Coordinate object to r, c
     * @param r new coordinates row
     * @param c new coordinates column
     * @return this coordinate position
     */
    public Coordinate set(final int r, final int c) { row=r; col=c; return this;}

    /**
     * sets current Coordinate object to location c
     * @return return this pointer
     */
    public Coordinate set(final Coordinate c) { row=c.row; col=c.col; return this; }

    /**
     * sets current Coordinate object to singleton i where
     *             i = row*MAXCOLS + col
     *             @return this Coordinate Object
     *.
     */
    public Coordinate set(final int i) {
        row = i/MAXROWS;
        col = i%MAXROWS;
        return this;
    }

    /**
     *           Make current coordinate a legal value,
     *           negatives become 0; values above Board.MAX become Board.MAX-1
     */
    public Coordinate setLegal() { setLegal( row, col ); return this; }

    /**
     * sets Coordinate object's row and col to the closest boards
     *             legal values of r, c
     * @param r desired row value
     * @param c desired column value
     */
    public Coordinate setLegal(final int r, final int c) {
        row = ( (r<0? 0 : (r>=MAXROWS ? MAXROWS-1 : r ) ) );
        col = ( (c<0? 0 : (c>=MAXCOLS ? MAXCOLS-1 : c ) ) );
        return this;
    }

    /**
     * takes current coordinate object and moves it up one row, iff the new is legal
     *  @return true if new position is legal; false if new position is off board, on false coordinate is not moved
     */
    public boolean up() {
        if( row <= 0 ) return false;
        --row;
        return true;
    }

    /**
     * takes current coordinate object and moves is down one row, iff the new col is legal
     * @return true if position is still on board; false if new position is off board, 
     * coordinate is not moved to illegal location
     */
    public boolean down() {
        if( row >= MAXROWS-1 ) return false;
        ++row;
        return true;
    }

    /**
     * takes current coordinate object and moves it left one coordinate; doesn't move the 
     * Coordinate to a new location if it is off board, returns false when position would be
     * illegal.
     *  @return true if new position exists on board; false if new position is off board, coordinate is not moved
     */
    public boolean left() {
        if( col <= 0 ) return false;
        --col;
        return true;
    }

    /**
     * takes current coordinate object and moves it right one coordinate
     *@return true if new position exists on board;
     false if new position is off board, coordinate is not moved illegal position.
     */
    public boolean right() {
        if( col >= MAXCOLS-1) return false;
        ++col;
        return true;
    }

    /**
     * Takes current coordinate object and moves it to the next location
     * when coordinate reaches end of board is starts over at 0,0
     * @return this new Coordinate
     */
    public Coordinate next() {
        if( ++col >= MAXCOLS ) {
            col = 0;
            if( ++row >= MAXROWS ) row = 0;
        }
        return this;
    }

    /**
     * @return true if row is legal, i.e., exists on board
     */
    public boolean isLegalRow() {
        return( ( row >= 0 && row < MAXROWS ? true : false ) );
    }

    /**
     * 
     * @return true if col is legal, i.e., exists on board
     */
    public boolean isLegalCol() {
        return( (col >= 0 && col < MAXCOLS ? true : false ) );
    }

    /**
     * Both row and col have to be legal positions
     * @return true if coordinate is a legal board position
     */
    public boolean isLegal() {

        return( isLegalRow() && isLegalCol() );
    }

    /**
     * returns a single integer representing current coordinate
     * @return integer representing row*MAXCOLS + col
     */
    public int singleton() { //returns a single int with both values
        return( row*MAXCOLS + col );
    }

    /**
     * Compares this to formal parameter, returns
     * Max singleton of the two Coordinate objects
     * @param c formal parameter to compare
     * @return max singleton of both objects
     */
    public Coordinate max( final Coordinate c) {

        return( c.singleton() > singleton() ? c : this );
    }

    /**
     * Compares this to formal parameter, returns 
     * min singleton of the two Coordinate objects
     * @param c formal parameter to compare
     * @return min singleton of both objects
     */
    public Coordinate min(final Coordinate c) {

        return( c.singleton() < singleton() ? c : this );
    }

    /**
     * computes the horizontal or vertical distance between two points
     * Limitation Doesn't compute diagonal distance
     * @param c compute distance from coordiante  this to c
     * @return distance between the parameter c and the calling object
     */
    public int distance(final Coordinate c) {

        Coordinate d = new Coordinate( row-c.row, col-c.col );

        if( d.row == 0 ) return Math.abs( d.col );
        if( d.col == 0 ) return Math.abs( d.row );

        //Else must have a diagonal so return -1
        return -1;
    }

    /**
     * @return true if both Coordinate are at the same location
     * @param  p1 Coordinate to test equality with
     * @see java.lang.Comparable
     */
    @Override
   public boolean equals(final java.lang.Object p1)  {
        if(  p1 instanceof Coordinate ) {
        Coordinate c = (Coordinate)p1;
        return ( (c.row==row&&c.col==col)? true : false );
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + this.row;
        hash = 71 * hash + this.col;
        return hash;
    }

    /**
     * @return true if both Coordinate are at the same location
     * @param  p1 Coordinate to test equality with
     * @see java.lang.Comparable
     */
    public boolean equals(final Coordinate c) { return equals( (Object)c ); }

    /**
     * Standard compareTo subtract this.singleton() - actualArgument.singleton()
     * @param p1 Coordinate to compare calling this object to
     * @return coordinate count between object and p1,
     *              if coordinate is at 0,0 and p1 is at 0,2 returns -2
     *              if coordinate is at 0,0 and p1 is at 1,0 returns -MAXCOLS.
     * @see java.lang.Comparable
     */
    public int compareTo(final java.lang.Object p1) {
        return( singleton() - ((Coordinate)p1).singleton() );
    }

}

