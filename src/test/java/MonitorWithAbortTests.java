import java.util.ArrayList;

/**
 * Created by jayesh on 4/18/17.
 */
public class MonitorWithAbortTests {
    public static void main(String args[]) {
        ArrayList<Integer> testList = new ArrayList();
        ReentrantMonitorWithAbort mMonitor = new KostaMonitorWithAbort(testList);
        mMonitor.lock();
        testList.add(0);
        mMonitor.debug();
        mMonitor.unlock();
        mMonitor.debug();
        mMonitor.lock();
        testList.add(1);
        mMonitor.debug();
        mMonitor.abort();
        mMonitor.debug();
    }
}
