import java.io.IOException;

/**
 * Created by jayesh on 4/13/17.
 */
public interface Monitor {
    // TODO: Idea: make most of this just a Reentrant Monitor interface and allow ReentrantMonitorWithAbort to be extensible to it
    void lock();
    void unlock();
    void await() throws InterruptedException;
    void signal();
    void signalAll();
    boolean isLocked();
}
