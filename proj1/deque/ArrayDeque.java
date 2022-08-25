package deque;

import java.lang.reflect.Array;

public class ArrayDeque<T> {
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
        T[] tmp = (T []) new Object[size];
        T[] newItems = (T []) new Object[newCapacity];
        int firstItem = nextFirst + 1;

        for (int i = 0; i < size; i++) {
            tmp[i] = items[(firstItem + i) % items.length];
        }
        System.arraycopy(tmp, 0, newItems, 0, size);
        items = newItems;
        nextFirst = items.length - 1;
        nextLast = size;
    }
    // Add the item to index nextFirst on 'items', then increment 'size' and 'nextFirst'
    public void addFirst(T item) {
        items[nextFirst] = item;
        size += 1;
        nextFirst -= 1;

        if (nextFirst < 0) {
            nextFirst = items.length - 1;
        }
        if (size == items.length) {
            this.resize(size * 2);
        }
    }
    // Add the item to index nextLast on 'items', then increment 'size' and 'nextLast'
    public void addLast(T item) {
        items[nextLast] = item;
        size += 1;
        nextLast += 1;

        if (nextLast >= items.length) {
            nextLast = 0;
        }
        if (size == items.length) {
            this.resize(size * 2);
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
        // Return null if no items
        if (size == 0) {
            return null;
        }
        // nextFirst is null, so index of first item is nextFirst + 1
        int targetIndex = nextFirst + 1;
        if (targetIndex >= items.length) {
            targetIndex = 0;
        }
        // Get desired item, update pointer, decrement size, update nextFirst
        T item = items[targetIndex];
        items[targetIndex] = null;
        size -= 1;
        nextFirst = targetIndex;

        // Resize if capacity < 0.25
        if ((float) size / items.length < 0.25) {
            this.resize(items.length / 4);
        }
        return item;
    }
    public T removeLast() {
        // Return null if no items
        if (size == 0) {
            return null;
        }
        // nextFirst is null, so index of first item is nextFirst + 1
        int targetIndex = nextLast - 1;
        if (targetIndex < 0) {
            targetIndex = items.length - 1;
        }
        // Get desired item, update pointer, decrement size, update nextFirst
        T item = items[targetIndex];
        items[targetIndex] = null;
        size -= 1;
        nextLast = targetIndex;

        // Resize if capacity < 0.25
        if ((float) size / items.length < 0.25) {
            this.resize(items.length / 4);
        }
        return item;
    }
    public T get(int index) {
        return items[index];
    }
    public boolean compareItems(ArrayDeque o) {
        for (int i = 0; i < items.length; i++) {
            // If one is null but the other is not null, return false
            // if both are not null and both not are equal, return false
            if ((this.items[i] != null && o.items[i] == null) || (this.items[i] == null && o.items[i] != null)) {
                return false;
            } else if (this.items[i] != null && o.items[i] != null && !(this.items[i]).equals(o.items[i])) {
                return false;
            }
        }
        return true;
    }
    public boolean equals(Object o) {
        if (o instanceof ArrayDeque) {
            return this.compareItems((ArrayDeque) o);
        }
        return false;
    }

    // Todo: iterator
}
