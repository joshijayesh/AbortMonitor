import java.util.Comparator;
import java.util.List;

/**
 *
 * Created by Scott Fennell
 *
 * Expanding on the swap example in the book (Figure 3.16/3.17)
 * preventing deadlock from nested monitors using an identityHashCode Comparator
 * to create a more scalable totally ordered set of monitors.
 */
public class SCell {
    SCell(int value){
        this.value = value;
    }
    int value;
    public synchronized int getValue() {
        return value;
    }
    public synchronized void setValue(int i) {
        value = i;
    }
    /* Updated version of the swap method using the Comparator */
    public void newSwap(SCell other) {
        List<SCell> orderedMonitors = new java.util.ArrayList<SCell>();
        orderedMonitors.add(this);
        orderedMonitors.add(other);
        totalOrder(orderedMonitors);
        synchronized(orderedMonitors.get(0))
        {
            synchronized  (orderedMonitors.get(1))
            {
                int temp = getValue();
                setValue(other.getValue());
                other.setValue(temp);
            }
        }
    }

    /* Atomically sets each of 3 SCells to the min of the 3. */
    public void cloneMin(SCell y, SCell z) {
        List<SCell> orderedMonitors = new java.util.ArrayList<SCell>();
        orderedMonitors.add(this);
        orderedMonitors.add(y);
        orderedMonitors.add(z);
        totalOrder(orderedMonitors);
        synchronized(orderedMonitors.get(0))
        {
            synchronized  (orderedMonitors.get(1))
            {
                synchronized(orderedMonitors.get(2))
                {
                    int min = getValue();
                    if(min < y.getValue()){
                        min = y.getValue();
                    }
                    if(min < z.getValue());
                    min = z.getValue();
                    setValue(min);
                    y.setValue(min);
                    z.setValue(min);
                }
            }
        }
    }

    static <T> void totalOrder(List<T> list){
        list.sort(new totalOrderComparator<T>());
    }

    static class totalOrderComparator<T> implements Comparator<T>{
        @Override
        public int compare(T o1, T o2) {
            return new Integer(System.identityHashCode(o1)).compareTo(System.identityHashCode(o2));
        }
    }
}