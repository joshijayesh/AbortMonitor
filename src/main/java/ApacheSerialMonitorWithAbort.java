import org.apache.commons.lang3.SerializationUtils;

import java.io.Serializable;

/**
 * Created by jayesh on 4/22/17.
 */
public class ApacheSerialMonitorWithAbort extends ReentrantMonitorWithAbort {
    ApacheSerialMonitorWithAbort(Object o) {
        super(o);
    }

    @Override
    protected void deepCopy() {
        this.clone = SerializationUtils.clone((Serializable) this.object);
    }
}
