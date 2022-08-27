package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

public class ArrayDequeTest {

    @Test
    /** Adds a few things to the array, checking isEmpty() and size() are correct,
     * finally printing the results.
     *
     * && is the "and" operation. */
    public void addIsEmptySizeTest() {

        ArrayDeque<String> a1 = new ArrayDeque<>();

        assertTrue("A newly initialized ArrayDeque should be empty", a1.isEmpty());
        a1.addFirst("front");

        // The && operator is the same as "and" in Python.
        // It's a binary operator that returns true if both arguments true, and false otherwise.
        assertEquals(1, a1.size());
        assertFalse("a1 should now contain 1 item", a1.isEmpty());

        a1.addLast("middle");
        assertEquals(2, a1.size());

        a1.addLast("back");
        assertEquals(3, a1.size());

        System.out.println("Printing out deque: ");
        a1.printDeque();
    }

    @Test
    /** Adds an item, then removes an item, and ensures that dll is empty afterwards. */
    public void addRemoveTest() {

        ArrayDeque<Integer> a1 = new ArrayDeque<>();
        // should be empty
        assertTrue("a1 should be empty upon initialization", a1.isEmpty());

        a1.addFirst(10);
        // should not be empty
        assertFalse("a1 should contain 1 item", a1.isEmpty());

        a1.removeFirst();
        // should be empty
        assertTrue("a1 should be empty after removal", a1.isEmpty());

    }

    @Test
    /* Tests removing from an empty deque */
    public void removeEmptyTest() {

        ArrayDeque<Integer> a1 = new ArrayDeque<>();
        a1.addFirst(3);

        a1.removeLast();
        a1.removeFirst();
        a1.removeLast();
        a1.removeFirst();

        int size = a1.size();
        String errorMsg = "  Bad size returned when removing from empty deque.\n";
        errorMsg += "  student size() returned " + size + "\n";
        errorMsg += "  actual size() returned 0\n";

        assertEquals(errorMsg, 0, size);
    }

    @Test
    /* Check if you can create LinkedListDeques with different parameterized types*/
    public void multipleParamTest() {

        ArrayDeque<String>  a1 = new ArrayDeque<>();
        ArrayDeque<Double>  a2 = new ArrayDeque<>();
        ArrayDeque<Boolean> a3 = new ArrayDeque<>();

        a1.addFirst("string");
        a2.addFirst(3.14159);
        a3.addFirst(true);

        String s = a1.removeFirst();
        double d = a2.removeFirst();
        boolean b = a3.removeFirst();
    }

    @Test
    /* check if null is return when removing from an empty LinkedListDeque. */
    public void emptyNullReturnTest() {

        ArrayDeque<Integer> a1 = new ArrayDeque<>();

        assertEquals("Should return null when removeFirst is called on an empty Deque,", null, a1.removeFirst());
        assertEquals("Should return null when removeLast is called on an empty Deque,", null, a1.removeLast());
    }

    @Test
    /* Add large number of elements to deque; check if order is correct. */
    public void bigArrayDequeTest() {

        ArrayDeque<Integer> a1 = new ArrayDeque<>();
        for (int i = 0; i < 10000; i++) {
            a1.addLast(i);
        }

        for (double i = 0; i < 5000; i++) {
            assertEquals("Should have the same value", i, (double) a1.removeFirst(), 0.0);
        }

        for (double i = 9999; i > 5000; i--) {
            assertEquals("Should have the same value", i, (double) a1.removeLast(), 0.0);
        }
    }

    @Test
    public void randomizedTest1() {
        ArrayDeque<Integer> a1 = new ArrayDeque<>();

        // Add 1000 numbers to array
        for (int i = 0; i < 1000; i++) {
            int operation = StdRandom.uniform(0, 2);

            if (operation == 0) {
                a1.addFirst(i);
            } else if (operation == 1) {
                a1.addLast(i);
            }
        }

        // Randomly add/remove 1000 numbers from array
        for (int i = 0; i < 1000; i++) {
            int operation = StdRandom.uniform(0, 4);
            int num = StdRandom.uniform(0, 10);

            if (operation == 0) {
                a1.addFirst(num);
            } else if (operation == 1) {
                a1.removeFirst();
            } else if (operation == 2) {
                a1.addLast(num);
            } else if (operation == 3){
                a1.removeLast();
            }
        }

        // Add a number to the array, ensure get(0) equals that number
        a1.addFirst(100);
        a1.addFirst(200);
        assertTrue(a1.get(0) == 200);
        assertTrue(a1.get(1) == 100);
    }

    @Test
    public void randomizedTest2() {
        ArrayDeque<Integer> a1 = new ArrayDeque<>();

        for (int i = 0; i < 1000; i++) {
            int operation = StdRandom.uniform(0, 4);
            int num = StdRandom.uniform(0, 10);

            if (operation == 0) {
                a1.addFirst(num);
            } else if (operation == 1) {
                a1.removeFirst();
            } else if (operation == 2) {
                a1.addLast(num);
            } else if (operation == 3) {
                a1.removeLast();
            }
        }

        // Add a number to the array, ensure get(0) equals that number
        a1.addFirst(100);
        assertTrue(a1.get(0) == 100);
    }

    @Test
    public void equalsTest() {
        ArrayDeque<Integer> a1 = new ArrayDeque<>();
        ArrayDeque<Integer> a2 = new ArrayDeque<>();
        ArrayDeque<String> a3 = new ArrayDeque<>();
        ArrayDeque<String> a4 = new ArrayDeque<>();

        for (int i = 0; i < 100; i++) {
            a1.addLast(i);
            a2.addLast(i);
            a3.addLast("a");
            a4.addLast("b");
        }
        assertEquals(a1, a2);
        assertNotEquals(a3, a4);
    }

    @Test
    public void printDequeTest() {
        ArrayDeque<Integer> a1 = new ArrayDeque<>();
        for (int i = 0; i < 5; i++) {
            a1.addLast(i);
        }
        System.out.println("Testing printDeque:");
        a1.printDeque();
    }

    @Test
    public void iteratorTest() {
        ArrayDeque<Integer> a1 = new ArrayDeque<>();
        for (int i = 0; i < 5; i++) {
            a1.addLast(i);
        }
        System.out.println("Testing iteratorTest:");
        for (int x : a1) {
            System.out.println(x);
        }
    }
}
