public class SynchCounterThread extends Thread{

    protected SynchCounter counter = null;

    public SynchCounterThread(SynchCounter counter){
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