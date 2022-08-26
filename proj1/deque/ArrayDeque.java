package deque;

import java.lang.reflect.Array;
import java.util.Iterator;

public class ArrayDeque<T> implements Deque<T>, Iterable<T> {
    public T[] items;
    public int size;
    public int nextFirst; // index of where the next first item is to be placed
    public int nextLast; // index of where the next last item is to be placed

    public ArrayDeque() {
        items = (T []) new Object[8];
        size = 0; // number of items in array
        nextFirst = items.length - 1;
        nextLast = 0;
    }

    /* tmp stores the results of intermediate step, which is to copy items one-by-one
     * starting from the index of the first item and moving forwards. This shift the
     * items, so they start at index 0. Then, do a regular arraycopy into newItems,
     * where both source and destination positions are i=0
     */
    public void resize(int newCapacity) {
        // Min array size 8, can't make tmp array of size 0
        if (size == 0) {
            return;
        }
        T[] tmp = (T []) new Object[size];
        T[] newItems = (T []) new Object[newCapacity];
        int firstDex = (nextFirst + 1) % items.length;

        for (int i = 0; i < size; i++) {
            tmp[i] = get((firstDex + i) % items.length);
        }
        System.arraycopy(tmp, 0, newItems, 0, size);
        items = newItems;
        nextFirst = items.length - 1;
        nextLast = size;
    }

    @Override
    /* Add the item to index nextFirst on 'items', then increment 'size' and 'nextFirst' */
    public void addFirst(T item) {
        items[nextFirst] = item;
        size += 1;
        nextFirst = Math.floorMod(nextFirst - 1, items.length);

        if (size == items.length) {
            resize(size * 2);
        }
    }

    @Override
    /* Add the item to index nextLast on 'items', then increment 'size' and 'nextLast' */
    public void addLast(T item) {
        items[nextLast] = item;
        size += 1;
        nextLast = (nextLast + 1) % items.length;

        if (size == items.length) {
            resize(size * 2);
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public void printDeque() {
        int firstDex = nextFirst + 1;

        for (int i = 0; i < size; i++) {
            int nextIndex = (firstDex + i) % items.length;
            System.out.print(get(nextIndex) + " ");
        }
        System.out.println();
    }

    @Override
    public T removeFirst() {
        // Return null if no items
        if (size == 0) {
            return null;
        }
        // nextFirst is null, so index of first item is nextFirst + 1
        int targetIndex = (nextFirst + 1) % items.length;

        // Get desired item, update pointer, decrement size, update nextFirst
        T item = get(targetIndex);
        items[targetIndex] = null;
        size -= 1;
        nextFirst = targetIndex;

        // Resize if capacity < 0.25
        if ((float) size / items.length < 0.25) {
            resize(items.length / 4);
        }
        return item;
    }

    @Override
    public T removeLast() {
        // Return null if no items
        if (size == 0) {
            return null;
        }
        // nextLast is null, so index of first item is nextFirst - 1
        int targetIndex = Math.floorMod(nextLast - 1, items.length);

        // Get desired item, update pointer, decrement size, update nextFirst
        T item = get(targetIndex);
        items[targetIndex] = null;
        size -= 1;
        nextLast = targetIndex;

        // Resize if capacity < 0.25
        if ((float) size / items.length < 0.25) {
            resize(items.length / 4);
        }
        return item;
    }

    @Override
    public T get(int index) {
        return items[index];
    }

    public boolean compareItems(ArrayDeque o) {
        for (int i = 0; i < items.length; i++) {

            // If one is null but the other is not null, return false
            // if both are not null and both not are equal, return false
            if (get(i) != null && o.get(i) == null || get(i) == null && o.get(i) != null) {
                return false;
            } else if (get(i) != null && o.get(i) != null && !(get(i).equals(o.get(i)))) {
                return false;
            }
        }
        return true;
    }

    @Override
    /* Returns whether the parameter o is equal to the Deque.
     * o is considered equal if it is a Deque and if it contains the same contents in the same order.
     */
    public boolean equals(Object o) {
        if (o instanceof ArrayDeque) {
            return compareItems((ArrayDeque) o);
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
        int itemsToIterate = size();
        int iteratorPos = (nextFirst + 1) % items.length;

        public boolean hasNext() {
            return itemsToIterate > 0;
        }
        public T next() {
            T item = get(iteratorPos);
            iteratorPos = (iteratorPos + 1) % items.length;
            itemsToIterate -= 1;
            return item;
        }
    }
}
