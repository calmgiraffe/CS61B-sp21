package game2048;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestAbleToMerge {

    @Test
    public void testMerge1() {
        int[][] rawVals = new int[][] {
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0},
        };
        Model m = new Model(rawVals, 0, 0, false);
        assertFalse(m.canMerge(0, 0, 3));
    }

    @Test
    public void testMerge2() {
        int[][] rawVals = new int[][] {
                {4, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0},
        };
        Model m = new Model(rawVals, 0, 0, false);
        assertFalse(m.canMerge(0, 0, 3));
    }

    @Test
    public void testMerge3() {
        int[][] rawVals = new int[][] {
                {2, 0, 0, 0},
                {0, 0, 0, 0},
                {0, 0, 0, 0},
                {2, 0, 0, 0},
        };
        Model m = new Model(rawVals, 0, 0, false);
        assertTrue(m.canMerge(0, 0, 3));
    }
}
