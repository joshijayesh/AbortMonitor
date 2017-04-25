/**
 * Created by Travis on 4/24/2017.
 */
public class LockCounterThread extends Thread {
    protected LockCounter counter = null;

    public LockCounterThread(LockCounter counter){
        this.counter = counter;
    }

    public void run() {
        long startTime = System.nanoTime();
        for(int i=0; i<10; i++){
            counter.add(i);
        }
        long endTime = System.nanoTime() - startTime;
        System.out.println(endTime);
    }
}

