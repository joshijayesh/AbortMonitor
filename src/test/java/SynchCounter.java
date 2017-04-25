public class SynchCounter{
    long count = 0;

    public synchronized void add(long value){
        this.count += value;
    }
}