import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;

import static junit.framework.TestCase.assertTrue;

/**
 * Created by jayesh on 4/18/17.
 */
@RunWith(Theories.class)
public class TestFunctionalityMonitorWithAbort {
    static Customer customer = new Customer(2, "Sarah");
    public static @DataPoints ReentrantMonitorWithAbort[] monitors = {
            new SerialMonitorWithAbort(customer),
            new KostaMonitorWithAbort(customer),
            new DozerMonitorWithAbort(customer)
    };

    @Theory
    public void customerFunctionalityTest(MonitorWithAbort monitor) {
        customer.setId(2);
        customer.setName("Sarah");

        assertTrue("Customer must be initial values", customer.getId() == 2 && customer.getName().equals("Sarah"));
        monitor.lock();
        assertTrue("Monitor must be locked", monitor.isLocked());
        customer.setId(5);
        monitor.unlock();
        assertTrue("Customer ID must be set to 5", customer.getId() == 5);
        assertTrue("Monitor Must be unlocked", !monitor.isLocked());


        monitor.lock();
        customer.setName("Jessica");
        monitor.abort();
        assertTrue("Customer Name Must be Sarah", customer.getName().equals("Sarah"));
        assertTrue("Monitor must be unlocked", !monitor.isLocked());
    }

    @Theory
    public void customerThreadTest(final MonitorWithAbort monitor) {
        customer.setId(2);
        customer.setName("Sarah");

        Thread t1 = new Thread() {
            public void run() {
                monitor.lock();
                customer.setId(customer.getId() + 3);
                customer.setName("Hannah");
                monitor.unlock();
            }
        };

        Thread t2 = new Thread() {
            public void run() {
                monitor.lock();
                customer.setId(customer.getId() + 1);
                customer.setName("Christine");
                monitor.abort();
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

        assertTrue("Customer must be Hannah with ID 5",
                    customer.getId() == 5 && customer.getName().equals("Hannah"));
    }
}
