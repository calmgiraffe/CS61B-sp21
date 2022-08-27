package deque;

import java.lang.reflect.Array;
import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    private T[] items;
    private int size;
    private int nextFirst; // index where front item is to be placed
    private int nextLast; // index where last item is to be placed

    public ArrayDeque() {
        items = (T []) new Object[8];
        size = 0; // number of items in array
        nextFirst = items.length - 1;
        nextLast = 0;
    }

    /* tmp stores the results of intermediate step, which is to copy items one-by-one
     * starting from the index of the first item and moving forwards. This shift the
     * items, so they start at index 0. Then, do a regular arraycopy into newItems,
     * where both source and destination positions are i=0 */
    private void resize(int newCapacity) {
        // Can't make tmp array of size 0
        if (size == 0) {
            return;
        }

        // Make a tmp array for the current items, a newItems array for the new resized array
        T[] tmp = (T []) new Object[size];
        T[] newItems = (T []) new Object[newCapacity];

        // Copy current items into tmp
        for (int i = 0; i < size; i++) {
            tmp[i] = get(i);
        }

        // Copy tmp into new array, update invariants
        System.arraycopy(tmp, 0, newItems, 0, size);
        items = newItems;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    private void checkResize() {
        float capacity = (float) size / items.length;

        if (size == items.length) {
            resize(size * 2);
        } else if (capacity < 0.25 && items.length != 8) {
            resize(items.length / 4);
        }
    }

    @Override
    /* Add the item to index nextFirst on 'items', then increment 'size' and 'nextFirst' */
    public void addFirst(T item) {
        items[nextFirst] = item;
        size += 1;
        nextFirst = Math.floorMod(nextFirst - 1, items.length);

        checkResize();
    }

    @Override
    /* Add the item to index nextLast on 'items', then increment 'size' and 'nextLast' */
    public void addLast(T item) {
        items[nextLast] = item;
        size += 1;
        nextLast = (nextLast + 1) % items.length;

        checkResize();
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        for (int i = 0; i < size; i++) {
            System.out.print(get(i) + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T item = get(0);

        // The index of get(0) is the targetIndex + 1, adjusted for items.length using modulo
        int targetIndex = (nextFirst + 1) % items.length;
        items[targetIndex] = null;
        size -= 1;
        nextFirst = targetIndex;

        checkResize();
        return item;
    }

    @Override
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T item = get(size - 1);

        // nextLast is null, so index of last item is nextLast - 1
        int targetIndex = Math.floorMod(nextLast - 1, items.length);
        items[targetIndex] = null;
        size -= 1;
        nextLast = targetIndex;

        checkResize();
        return item;
    }

    @Override
    public T get(int index) {
        return items[Math.floorMod(nextFirst + 1 + index, items.length)];
    }

    @Override
    /* Returns whether the parameter o is equal to the Deque.
     * o is considered equal if it is a Deque and if it contains the same contents in the same order. */
    public boolean equals(Object o) {
        if (o instanceof Deque && ((Deque<?>) o).size() == size) {

            // Iterate through all items and immediately return false if unequal items
            for (int i = 0; i < size; i++) {
                if (((Deque<?>) o).get(i) != get(i)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    /* Returns an iterator that looks into the data type */
    public Iterator<T> iterator() {
        return new ArrayDequeIterator();
    }

    // Recall: Iterator interface has methods defined in it, two of which are hasNext() and next()
    // Other methods are remove(), forEachRemaining()
    private class ArrayDequeIterator implements Iterator<T> {
        int itemsToIterate = size;
        int iteratorPos = 0;

        public boolean hasNext() {
            return itemsToIterate > 0;
        }

        public T next() {
            T item = get(iteratorPos);
            iteratorPos += 1;
            itemsToIterate -= 1;
            return item;
        }
    }
}
