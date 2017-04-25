import java.util.HashMap;


/**
 * Created by Scott Fennell
 *
 * An example implementation of
 * wait that throws an exception if it
 * is called within a nested monitor;
 * Call safeAcquire() and safeRelease()
 * just inside any synchronized block
 */
public class ExceptionOnNestedWaitExample {
    private static class myClass implements Runnable {

        private ThreadLocal<HashMap<Object, Integer>> monitors
                = new ThreadLocal<HashMap<Object, Integer>>();

        public void safeWait(Object monitor) throws NestedWaitException {
            if (monitors.get().keySet().size() > 1) {
                throw new NestedWaitException();
            }
            try {
                monitor.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void safeAcquire(Object a){
            monitors.get().putIfAbsent(a, 0);
            monitors.get().put(a, monitors.get().get(a) + 1);
        }

        public void safeRelease(Object a){
            monitors.get().put(a, monitors.get().get(a) + -1);
            if(monitors.get().get(a) == 0){
                monitors.get().remove(a);
            }
        }

        @Override
        public void run() {

            /*
            Code here
            */
            Object a = new Object();
            Object b = new Object();

            synchronized(a){ safeAcquire(a);

                synchronized(b) { safeAcquire(b);
                    // etc...
                    try {
                        safeWait(b); // throws exception
                    } catch (NestedWaitException e) {
                        e.printStackTrace();
                    }

                    safeRelease(b);}

                try {
                    safeWait(a); // does not throw exception
                } catch (NestedWaitException e) {
                    e.printStackTrace();
                }

            safeRelease(a);}

        }

        class NestedWaitException extends Exception {
            public NestedWaitException() {
            }

            public NestedWaitException(String message) {
                super(message);
            }
        }
    }
}
