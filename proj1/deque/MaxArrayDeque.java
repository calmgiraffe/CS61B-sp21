package deque;

import java.util.Comparator;

public class MaxArrayDeque<T> extends ArrayDeque<T> {
    private Comparator<T> comp;

    public MaxArrayDeque(Comparator<T> c) {
        super();
        comp = c;
    }

    public T max() {
        return max(comp);
    }

    public T max(Comparator<T> c) {
        if (size() == 0) {
            return null;
        }
        T maxItem = get(0);

        for (int i = 1; i < size(); i++) {
            T currItem = get(i);

            if (c.compare(currItem, maxItem) > 0) {
                maxItem = currItem;
            }
        }
        return maxItem;
    }
}
