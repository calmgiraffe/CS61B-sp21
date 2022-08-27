package deque;

import java.util.Iterator;

public class LinkedListDeque<T> implements Deque<T>, Iterable<T> {
    // Nested Node class definition - cannot be accessed from object outside LinkedListDeque
    private class Node {
        private Node back;
        private T item;
        private Node front;

        Node(Node b, T i, Node f) {
            back = b;
            item = i;
            front = f;
        }
    }
    private Node sentinel;
    private int size;

    public LinkedListDeque() { // Constructor
        sentinel = new Node(null, null, null);
        sentinel.front = sentinel;
        sentinel.back = sentinel;
        size = 0;
    }

    @Override
    public void addFirst(T item) {
        Node newNode = new Node(sentinel, item, sentinel.front);
        sentinel.front.back = newNode;
        sentinel.front = newNode;
        size += 1;
    }

    @Override
    public T removeFirst() {
        if (isEmpty()) {
            return null;
        }
        T removedItem = get(0);
        sentinel.front = sentinel.front.front;
        sentinel.front.back = sentinel;
        size -= 1;

        return removedItem;
    }

    @Override
    public void addLast(T item) {
        Node newNode = new Node(sentinel.back, item, sentinel);
        sentinel.back.front = newNode;
        sentinel.back = newNode;
        size += 1;
    }

    @Override
    public T removeLast() {
        if (isEmpty()) {
            return null;
        }
        T removedItem = sentinel.back.item;
        sentinel.back = sentinel.back.back;
        sentinel.back.front = sentinel;
        size -= 1;

        return removedItem;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public T get(int index) {
        Node tmp = sentinel.front;

        for (int i = 0; i < size; i++) {
            if (i == index) {
                return tmp.item;
            }
            tmp = tmp.front;
        }
        return null;
    }

    public T getRecursive(int index) {
        // helper class that allows easy recursion
        class Helper {
            T helper(int index, Node n) {
                if (index == 0) {
                    return n.item;
                }
                return helper(index - 1, n.front);
            }
        }
        Helper h = new Helper();
        return h.helper(index, sentinel.front);
    }

    @Override
    public void printDeque() {
        Node tmp = sentinel.front;

        for (int i = 0; i < size; i++) {
            if (tmp.equals(sentinel)) {
                System.out.println();
            }

            System.out.print(tmp.item + " ");
            tmp = tmp.front;
        }
    }

    private boolean checkEquals(Deque o) {
        // Iterate through all items and immediately return false if unequal items
        for (int i = 0; i < size(); i++) {

            if (!get(i).equals(o.get(i))) {
                return false;
            }
        }
        return true;
    }
    @Override
    public boolean equals(Object o) {
        // If both objects are non-empty Deque
        if (o instanceof Deque && ((Deque<?>) o).size() == size() && !isEmpty()) {
            return checkEquals((Deque) o);
        }
        return false;
    }

    @Override
    /* Returns an iterator that looks into the data type */
    public Iterator<T> iterator() {
        return new LinkedListIterator();
    }

    /* Recall: Iterator interface has methods defined in it, two of which are hasNext() and next()
     * Other methods are remove(), forEachRemaining() */
    private class LinkedListIterator implements Iterator<T> {
        int itemsToIterate = size();
        Node iteratorNode = sentinel.front;

        public boolean hasNext() {
            return itemsToIterate > 0;
        }
        public T next() {
            T item = iteratorNode.item;
            itemsToIterate -= 1;
            iteratorNode = iteratorNode.front;
            return item;
        }
    }
}
