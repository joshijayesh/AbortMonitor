import java.io.Serializable;

import static java.lang.Thread.yield;

/**
 * Created by jayesh on 4/22/17.
 */
public class SynchronousCustomer implements Serializable {
    private Monitor mMonitor;
    private boolean waitingInLine;
    private boolean isWaiting;
    private String result;

    public SynchronousCustomer() {
        setMonitor(null);
        this.waitingInLine = false;
        this.isWaiting = false;
    }

    public void setMonitor(Monitor monitor) {
        this.mMonitor = monitor;
    }

    public void readyToLeave() {
        this.mMonitor.lock();
        if(!waitingInLine) {
            try {
                isWaiting = true;
                mMonitor.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        result = "Done";
        mMonitor.unlock();
    }

    public void gotItems() {
        while(!isWaiting) {
            yield();
        }
        mMonitor.lock();
        waitingInLine = true;
        mMonitor.signalAll();
        mMonitor.unlock();
    }

    public String getResult() {
        return result;
    }
}

