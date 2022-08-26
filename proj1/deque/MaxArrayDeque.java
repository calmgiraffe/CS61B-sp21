package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    public Comparator<T> comp;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        comp = c;
    }
    public T max() {
        return max(comp);
    }
    public T max(Comparator<T> c) {
        if (items == null) {
            return null;
        }
        T maxItem = items[nextFirst + 1];
        for (int i = 0; i < size; i++) {
            int index = (nextFirst + 1) % items.length;
            T currItem = get(index + i);

            if (c.compare(currItem, maxItem) > 0) {
                maxItem = currItem;
            }
        }
        return maxItem;
    }
}
