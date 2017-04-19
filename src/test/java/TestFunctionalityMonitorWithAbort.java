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
    public void arrayTest(MonitorWithAbort monitor) {
        System.out.println(monitor.getClass());
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
}
