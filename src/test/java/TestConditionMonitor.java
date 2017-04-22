import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by jayesh on 4/22/17.
 */
@RunWith(Theories.class)
public class TestConditionMonitor {
    static SynchronousCustomer customer = new SynchronousCustomer();
    public static @DataPoints ReentrantMonitor[] monitors = {
            new SerialMonitorWithAbort(customer),
            new KostaMonitorWithAbort(customer),
            new DozerMonitorWithAbort(customer),
            new ReentrantMonitor()
    };

    @Theory
    public void testCondition(ReentrantMonitor monitor) {
        customer.setMonitor(monitor);

        Thread t1 = new Thread() {
            public void run() {
                customer.gotItems();
            }
        };

        Thread t2 = new Thread() {
            public void run() {
                customer.readyToLeave();
            }
        };

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue("Result must be done", customer.getResult().equals("Done"));
    }
}
