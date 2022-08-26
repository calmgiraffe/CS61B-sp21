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
        int currIndex = (nextFirst + 1) % items.length;
        T maxItem = get(currIndex);

        for (int i = 0; i < size; i++) {
            int index = (currIndex + i) % items.length;
            T currItem = get(index);

            if (c.compare(currItem, maxItem) > 0) {
                maxItem = currItem;
            }
        }
        return maxItem;
    }
}
