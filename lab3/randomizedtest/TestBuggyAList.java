package randomizedtest;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;
import timingtest.AList;

import static org.junit.Assert.*;

/**
 * Created by hug.
 */
public class TestBuggyAList {

    @Test
    public void testThreeAddThreeRemove() {
        BuggyAList<Integer> buggyList = new BuggyAList<>();
        AListNoResizing<Integer> regularList = new AListNoResizing<>();
        for (int n = 1; n <= 3; n++) {
            buggyList.addLast(n);
            regularList.addLast(n);
        }
        for (int i = 0; i < 3; i++) {
            int removedItemBuggy = buggyList.removeLast();
            int removedItem = regularList.removeLast();
            assertEquals(removedItemBuggy, removedItem);
        }
    }
    @Test
    public void randomizedTest() {
        AListNoResizing<Integer> L = new AListNoResizing<>();
        BuggyAList<Integer> buggyList = new BuggyAList<>();

        int N = 5000;
        for (int i = 0; i < N; i += 1) {
            int operationNumber = StdRandom.uniform(0, 4);
            if (operationNumber == 0) {
                // addLast
                int randVal = StdRandom.uniform(0, 100);
                L.addLast(randVal);
                buggyList.addLast(randVal);
            } else if (operationNumber == 1) {
                // size
                int size = L.size();
                int buggySize = buggyList.size();
                assertEquals(size, buggySize);
            } else if (operationNumber == 2) {
                // getLast
                if (L.size() > 0) {
                    int n = L.getLast();
                    int b = buggyList.getLast();
                    assertEquals(n, b);
                }
            } else if (operationNumber == 3) {
                // removeLast
                if (L.size() > 0) {
                    int n = L.removeLast();
                    int b = buggyList.removeLast();
                    assertEquals(n, b);
                }
            }
        }
    }
}
