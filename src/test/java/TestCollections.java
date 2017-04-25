import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static org.junit.Assert.assertTrue;

/**
 * Created by jayesh on 4/24/17.
 */
@RunWith(Theories.class)
public class TestCollections {
    static ArrayList<Integer> myList = new ArrayList();
    public static @DataPoints ReentrantMonitorWithAbort[] monitors = {
            new SerialMonitorWithAbort(myList),
            new ApacheSerialMonitorWithAbort(myList),
            new KostaMonitorWithAbort(myList),
            new DozerMonitorWithAbort(myList)
    };

    @Theory
    public void listTest(ReentrantMonitorWithAbort monitor) {
        myList.clear();

        monitor.lock();
        myList.add(0);
        monitor.unlock();

        assertTrue("List must have one value", myList.size() == 1);

        monitor.lock();
        myList.add(1);
        monitor.abort();

        assertTrue("List must still have one value", myList.size() == 1);
    }
}
