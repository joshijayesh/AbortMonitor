
import java.io.*;

/**
 * Created by jayesh on 4/13/17.
 */
public class SerialMonitorWithAbort extends ReentrantMonitorWithAbort {

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
}
