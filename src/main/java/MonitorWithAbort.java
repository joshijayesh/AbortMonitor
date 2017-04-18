import java.io.IOException;

/**
 * Created by jayesh on 4/13/17.
 */
public interface MonitorWithAbort {
    public void lock();
    public void unlock();
    public void abort();
    public void await() throws InterruptedException;
    public void signal();
    public void signalAll();
}
