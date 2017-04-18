import com.rits.cloning.Cloner;

/**
 * Created by jayesh on 4/18/17.
 */
public class KostaMonitorWithAbort extends ReentrantMonitorWithAbort {
    public KostaMonitorWithAbort(Object o) {
        super(o);
    }

    @Override
    protected void deepCopy() {
        Cloner cloner = new Cloner();

        this.clone = cloner.deepClone(this.object);
    }

}
