package deque;

public class LinkedListDeque<T> {
    // Nested Node class definition - cannot be accessed from object outside LinkedListDeque
    private class Node {
        public Node back;
        public T item;
        public Node front;

        public Node(Node b, T i, Node f) { // Constructor
            back = b;
            item = i;
            front = f;
        }
    }
    public Node sentinel;
    public int size;

    public LinkedListDeque() { // Constructor
        sentinel = new Node(null, null, null);
        sentinel.front = sentinel;
        sentinel.back = sentinel;
        size = 0;
    }
    public void addFirst(T item) {
        Node newNode = new Node(sentinel, item, sentinel.front);
        sentinel.front.back = newNode;
        sentinel.front = newNode;
        size += 1;
    }
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T removedItem = sentinel.front.item;
        sentinel.front = sentinel.front.front;
        sentinel.front.back = sentinel;
        size -= 1;

        return removedItem;
    }
    public void addLast(T item) {
        Node newNode = new Node(sentinel.back, item, sentinel);
        sentinel.back.front = newNode;
        sentinel.back = newNode;
        size += 1;
    }
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T removedItem = sentinel.back.item;
        sentinel.back = sentinel.back.back;
        sentinel.back.front = sentinel;
        size -= 1;

        return removedItem;
    }
    public int size() {
        return size;
    }
    public boolean isEmpty() {
        if (size == 0) {
            return true;
        }
        return false;
    }
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
}