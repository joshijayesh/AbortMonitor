
import java.io.*;
import java.util.ArrayList;

/**
 * Created by jayesh on 4/13/17.
 */
public class SerialMonitorWithAbort extends ReentrantMonitor {

    SerialMonitorWithAbort(Object o) {
        super(o);
    }

    /**
     * Basis: https://gist.github.com/neuro-sys/3798357
     */
    @Override
    protected void deepCopy() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this.object);
            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            this.clone = ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public static void main(String args[]) {
        ArrayList<Integer> testList = new ArrayList();
        SerialMonitorWithAbort mMonitor = new SerialMonitorWithAbort(testList);
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
