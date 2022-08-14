package game2048;

public class TestAbleToMerge {

    public static boolean ableToMerge(int col, int row, int topRow, Board board) {
        for (int r = row + 1; r <= topRow; r++) {

            // If null, continue to next iteration
            if (board.tile(col, r) != null && board.tile(col, r).value() == board.tile(col, row).value()) {
                return true;
            }
        }
        return false;
    }

    public static void testMerge1() {

        int[][] rawVals = new int[][] {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0},
        };


        Board board = new Board(rawVals, 0);
        boolean output = TestAbleToMerge.ableToMerge(0, 0, 3, board);
        System.out.println((output));
    }

    public static void testMerge2() {

        int[][] rawVals = new int[][] {
                {4, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0},
        };


        Board board = new Board(rawVals, 0);
        boolean output = TestAbleToMerge.ableToMerge(0, 0, 3, board);
        System.out.println((output));
    }

    public static void testMerge3() {

        int[][] rawVals = new int[][] {
                {2, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0},
        };


        Board board = new Board(rawVals, 0);
        boolean output = TestAbleToMerge.ableToMerge(0, 0, 3, board);
        System.out.println((output));
    }

    public static void main(String[] args) {
        testMerge1();
        testMerge2();
        testMerge3();
    }
}
