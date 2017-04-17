import java.io.IOException;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by jayesh on 4/13/17.
 */
public abstract class ReentrantMonitor implements MonitorWithAbort {
    protected ReentrantLock lock;
    protected Condition monitorCondition;
    protected Object object;
    protected Object clone;

    ReentrantMonitor(Object o) {
        this.lock = new ReentrantLock(true);
        this.monitorCondition = this.lock.newCondition();
        this.object = o;
    }

    /**
     * Must be implemented.
     */
    protected abstract void deepCopy();

    public void lock() {
        this.lock.lock();
        deepCopy();
    }

    public void unlock() {
        this.clone = null;
        this.lock.unlock();
    }

    public void abort() {
        this.object = this.clone;
        unlock();
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

    public void debug() {
        System.out.println("Main Object:\n" + this.object.toString());
        if(this.clone != null) {
            System.out.println("Clone Object:\n" + this.clone.toString());
        } else {
            System.out.println("Clone is null");
        }
    }
}
