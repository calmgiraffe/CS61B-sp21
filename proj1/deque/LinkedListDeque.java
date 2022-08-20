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
    public Node sentFront;
    public Node sentBack;
    public int size;

    public LinkedListDeque() { // Constructor
        sentFront = new Node(null, null, null);
        sentBack = new Node(null, null, null);
        sentFront.front = sentBack;
        sentBack.back = sentFront;
        size = 0;
    }
    public void addFirst(T item) {
        Node newNode = new Node(sentFront, item, sentFront.front);
        sentFront.front.back = newNode;
        sentFront.front = newNode;
        size += 1;
    }
    public T removeFirst() {
        if (size == 0) {
            return null;
        }
        T removedItem = sentFront.front.item;
        sentFront.front = sentFront.front.front;
        sentFront.front.back = sentFront;
        size -= 1;

        return removedItem;
    }
    public void addLast(T item) {
        Node newNode = new Node(sentBack.back, item, sentBack);
        sentBack.back.front = newNode;
        sentBack.back = newNode;
        size += 1;
    }
    public T removeLast() {
        if (size == 0) {
            return null;
        }
        T removedItem = sentBack.back.item;
        sentBack.back = sentBack.back.back;
        sentBack.back.front = sentBack;
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
        Node tmp = sentFront.front;

        for (int i = 0; i < size; i++) {
            if (i == index) {
                return tmp.item;
            }
            tmp = tmp.front;
        }
        return null;
    }
    public void printDeque() {
        Node tmp = sentFront.front;

        for (int i = 0; i < size; i++) {
            if (tmp.equals(sentBack)) {
                System.out.println();
            }

            System.out.print(tmp.item + " ");
            tmp = tmp.front;
        }
    }
}
