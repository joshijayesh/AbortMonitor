import java.io.Serializable;

public class LockCounter implements Serializable{
    private Monitor monitor;
    long count;


    public LockCounter() {
        monitor = null;
        count = 0;
    }

    public void setMonitor(Monitor m) {
        monitor = m;
    }

    public void add(long value){
        monitor.lock();
        this.count += value;
        monitor.unlock();
    }

    public void reset() {
        count = 0;
    }
}