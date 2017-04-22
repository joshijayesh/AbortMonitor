import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jayesh on 4/22/17.
 */
public class ReentrantMonitor implements Monitor {
    protected ReentrantLock lock;
    protected Condition monitorCondition;

    public ReentrantMonitor() {
        this.lock = new ReentrantLock();
        monitorCondition = this.lock.newCondition();
    }

    public void lock() {
        this.lock.lock();
    }

    public void unlock() {
        this.lock.unlock();
    }

    public void await() throws InterruptedException {
        this.monitorCondition.await();
    }

    public void signal() {
        this.monitorCondition.signal();
    }

    public void signalAll() {
        this.monitorCondition.signalAll();
    }

    public boolean isLocked() {
        return this.lock.isLocked();
    }
}
