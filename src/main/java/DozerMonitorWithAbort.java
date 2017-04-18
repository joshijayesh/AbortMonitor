import org.dozer.DozerBeanMapper;
import org.dozer.Mapper;

/**
 * Created by jayesh on 4/18/17.
 */
public class DozerMonitorWithAbort extends ReentrantMonitorWithAbort {

    DozerMonitorWithAbort(Object o) {
        super(0);
    }

    /**
     * TODO: Fix this problem ~ gives a ClassNotFound Exception
     */
    @Override
    protected void deepCopy() {
        Mapper mapper = new DozerBeanMapper();
        this.clone = mapper.map(this.object, this.object.getClass());
    }
}
