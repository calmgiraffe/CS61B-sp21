package deque;

import edu.princeton.cs.algs4.StdRandom;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.junit.Assert.assertEquals;

import java.util.Comparator;

class Dog {
    public String name;
    public int size;

    public Dog(String s, int n) {
        name = s;
        size = n;
    }
    private static class SizeComparator implements Comparator<Dog> {
        public int compare(Dog a, Dog b) {
            return a.size - b.size;
        }
    }
    private static class NameComparator implements Comparator<Dog> {
        public int compare(Dog a, Dog b) {
            return a.name.compareTo(b.name);
        }
    }
    public static Comparator<Dog> getNameComparator() {
        return new NameComparator();
    }
    public static Comparator<Dog> getSizeComparator() {
        return new SizeComparator();
    }
}

public class MaxArrayDequeTest {

    @Test
    public void comparatorTest() {
        Dog d1 = new Dog("Willy", 10);
        Dog d2 = new Dog("Fido", 20);
        Dog d3 = new Dog("Lucky", 30);
        Dog d4 = new Dog("Zeta", 40);
        Dog d5 = new Dog("Beta", 50);
        Dog d6 = new Dog("Delta", 60);
        Dog d7 = new Dog("Gamma", 70);
        Dog d8 = new Dog("Epsilon", 80);
        Dog d9 = new Dog("Mu", 90);

        Comparator<Dog> nc1 = Dog.getSizeComparator();
        Comparator<Dog> nc2 = Dog.getNameComparator();

        MaxArrayDeque<Dog> a1 = new MaxArrayDeque<>(nc1);
        a1.addLast(d1);
        a1.addLast(d2);
        a1.addFirst(d3);
        a1.addLast(d4);
        a1.addFirst(d5);
        a1.addFirst(d6);
        a1.addFirst(d7);
        a1.addLast(d8);
        a1.addLast(d9);

        assertEquals("Mu", a1.max().name);
        assertEquals("Zeta", a1.max(nc2).name);
    }
}
