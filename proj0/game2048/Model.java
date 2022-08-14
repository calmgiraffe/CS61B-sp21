package game2048;

import java.util.Formatter;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author calmgiraffe
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed = false;
        int changes = 0;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.

        // Set tilt direction as temporary north
        board.setViewingPerspective(side);

        // Iterate through columns of Board
        for (int col = 0; col < board.size(); col++) {
            changes += moveTiles(col);
        }

        if (changes > 0) {
            changed = true;
        }

        // Reset board north to NORTH
        board.setViewingPerspective(Side.NORTH);

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /** For a tile in a Board column, calculates the number of spaces a tile must move up
     * in order to result in a correct final position, then applies this move to the tile.
     */
    public int moveTiles(int col) {
        int buffer = 0;
        int topRow = this.board.size() - 1;
        int changes = 0;
        Tile t;

        // For a column, iterate from the second row downwards to row 0
        for (int row = topRow - 1; row >= 0; row--) {
            if (this.board.tile(col, row) != null) {
                int aboveSpace = (topRow - buffer) - row;
                int aboveTiles = findAboveTiles(col, row, (topRow - buffer));
                int numMovesUp = aboveSpace - aboveTiles;

                t = this.board.tile(col, row);
                if (canMerge(col, row, (topRow - buffer))) {
                    numMovesUp++;
                    buffer++;
                    this.score += 2*this.board.tile(col, row).value();
                }

                if (numMovesUp > 0) {
                    changes++;
                }

                this.board.move(col, row + numMovesUp, t);
            }
        }
        return changes;
    }

    /** Returns the number of tiles above (north of) a tile at (col, row), up to topRow */
    public int findAboveTiles(int col, int row, int topRow) {
        int count = 0;
        for (int r = row + 1; r <= topRow; r++) {
            if (this.board.tile(col, r) != null) {
                count++;
            }
        }
        return count;
    }

    /** Returns true if able to merge, false otherwise.
     * A tile is able to merge if there is an uninterrupted path to a tile of the same value above it,
     * or if there are three tiles above it of the same values.
     */
    public boolean canMerge(int col, int row, int topRow) {
        for (int r = row + 1; r <= topRow; r++) {

            // If null, continue to next iteration
            if (this.board.tile(col, r) != null
            && this.board.tile(col, r).value() == this.board.tile(col, row).value()) {
                return true;
            }
        }
        return false;
    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        int size = b.size();

        for (int col = 0; col < size; col++) {
            for (int row = 0; row < size; row++) {
                if (b.tile(col, row) == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        int size = b.size();

        for (int col = 0; col < size; col++) {
            for (int row = 0; row < size; row++) {
                if (b.tile(col, row) != null && b.tile(col, row).value() == MAX_PIECE) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        if (emptySpaceExists(b) || adjacentExists(b)) {
            return true;
        }
        return false;
    }

    public static boolean adjacentExists(Board b) {
        int size = b.size();

        for (int col = 0; col < size; col++) {
            for (int row = 0; row < size; row++) {
                // Check every other tile of the board
                // .tile() returns the current Tile at (col, row); returns null if no tile
                if ((col + row) % 2 == 0 && checkAdjacentTiles(b, col, row, size)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean checkAdjacentTiles(Board b, int col, int row, int size) {
        for (int i = -1; i <= 1; i += 2) {
            if (isValidIndex((col+i), (row), size) &&
            b.tile(col, row).value() == b.tile((col+i), (row)).value()) {
                return true;
            }
        }
        for (int j = -1; j <= 1; j += 2) {
            if (isValidIndex((col), (row+j), size) &&
            b.tile(col, row).value() == b.tile((col), (row+j)).value()) {
                    return true;
            }
        }
        return false;
    }

    public static boolean isValidIndex(int i, int j, int size) {
        if ((i < 0 || i >= size) || (j < 0 || j >= size)) {
            return false;
        }
        return true;
    }

    @Override
    /* Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /* Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /* Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
