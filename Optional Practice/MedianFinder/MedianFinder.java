import java.util.Collections;
import java.util.PriorityQueue;

public class MedianFinder {
    PriorityQueue<Integer> l = new PriorityQueue<>(Collections.reverseOrder());
    PriorityQueue<Integer> h = new PriorityQueue<>();

    public void balance() {
        while (l.size() > h.size() + 1) {
            int p = l.poll();
            h.add(p);
        }
        while (h.size() > l.size() + 1) {
            int p = h.poll();
            l.add(p);
        }
    }

    public void insert(int x) {
        if (l.size() == 0 || x <= l.peek())    l.add(x);
        if (h.size() == 0 || x >= h.peek())    h.add(x);
        else                                   l.add(x);
        balance();
    }

    public int getMedian() {
        int p;
        if (l.size() <= h.size())    p = h.poll();
        else                         p = l.poll();
        balance();
        return p;
    }
}
