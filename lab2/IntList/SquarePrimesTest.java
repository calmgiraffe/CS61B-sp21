package IntList;

import static org.junit.Assert.*;
import org.junit.Test;

public class SquarePrimesTest {

    /**
     * Here is a test for isPrime method. Try running it.
     * It passes, but the starter code implementation of isPrime
     * is broken. Write your own JUnit Test to try to uncover the bug!
     */
    @Test
    public void testSquarePrimesSimple() {
        IntList lst = IntList.of(14, 15, 16, 17, 18);
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("14 -> 15 -> 16 -> 289 -> 18", lst.toString());
        assertTrue(changed);
    }

    @Test
    public void testSquarePrimesNoPrimes() {
        IntList lst = IntList.of(1, 4, 65, 100); // No primes, including the special case of 1
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("1 -> 4 -> 65 -> 100", lst.toString());
        assertFalse(changed);
    }

    @Test
    public void testSquarePrimesAllPrimes() {
        IntList lst = IntList.of(2, 5, 7, 11, 13, 17); // No primes, including the special case of 1
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("4 -> 25 -> 49 -> 121 -> 169 -> 289", lst.toString());
        assertTrue(changed);
    }
    @Test
    public void testSquarePrimes3PrimesEvenIndexed() {
        IntList lst = IntList.of(2, 6, 7, 12, 13, 18); // No primes, including the special case of 1
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("4 -> 6 -> 49 -> 12 -> 169 -> 18", lst.toString());
        assertTrue(changed);
    }
    @Test
    public void testSquarePrimes3PrimesOddIndexed() {
        IntList lst = IntList.of(4, 5, 6, 7, 12, 13); // No primes, including the special case of 1
        boolean changed = IntListExercises.squarePrimes(lst);
        assertEquals("4 -> 25 -> 6 -> 49 -> 12 -> 169", lst.toString());
        assertTrue(changed);
    }
}
