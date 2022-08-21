package deque;

import java.lang.reflect.Array;

public class ArrayDeque<T> {
    public T[] items;
    public int size;
    public int nextFirst;
    public int nextLast;

    public ArrayDeque() {
        items = (T []) new Object[8];
        size = 0; // number of items in array
        nextFirst = 0;
        nextLast = 1;
    }
    public void addFirst(T item) {
        items[nextFirst] = item;
        size += 1;
        nextFirst -= 1;
        if (nextFirst < 0) {
            nextFirst = items.length - 1;
        }
    }
    public void addLast(T item) {
        items[nextLast] = item;
        size += 1;
        nextLast += 1;
        if (nextLast >= items.length) {
            nextLast = 0;
        }
    }
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }
    public int size() {
        return size;
    }
    public void printDeque() {
        int nextIndex = nextFirst + 1;
        for (int i = 0; i < size; i++) {
            if (nextIndex >= items.length) {
                nextIndex = (items.length + i) % items.length;
            }
            System.out.print(items[nextIndex] + " ");
        }
        System.out.println();
    }
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        int targetIndex = nextFirst + 1;
        if (targetIndex >= items.length) {
            targetIndex = 0;
        }
        T item = items[targetIndex];
        items[targetIndex] = null;
        size -= 1;
        nextFirst = targetIndex;

        return item;
    }
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        int targetIndex = nextLast - 1;
        if (targetIndex < 0) {
            targetIndex = items.length - 1;
        }
        T item = items[targetIndex];
        items[targetIndex] = null;
        size -= 1;
        nextLast = targetIndex;

        return item;
    }
    public T get(int index) {
        return items[index];
    }
}
