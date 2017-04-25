

public class SynchMain {

    public static void join(Thread a, Thread b, Thread c, Thread d) {
        try {
            a.join();
            b.join();
            c.join();
            d.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void runTest(ReentrantMonitor m, LockCounter lockCounter) {
        System.out.println();
        System.out.println(m.getClass().toString());
        lockCounter.reset();
        lockCounter.setMonitor(m);
        Thread threadA = new LockCounterThread(lockCounter);
        Thread threadB = new LockCounterThread(lockCounter);
        Thread threadC = new LockCounterThread(lockCounter);
        Thread threadD = new LockCounterThread(lockCounter);

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        join(threadA, threadB, threadC, threadD);
    }

    public static void main(String[] args){
        SynchCounter counter = new SynchCounter();
        Thread threadA = new SynchCounterThread(counter);
        Thread threadB = new SynchCounterThread(counter);
        Thread threadC = new SynchCounterThread(counter);
        Thread threadD = new SynchCounterThread(counter);
        System.out.println("Synchronized Block");
        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        join(threadA, threadB, threadC, threadD);
        LockCounter lockCounter = new LockCounter();

        runTest(new ReentrantMonitor(), lockCounter);
        runTest(new ApacheSerialMonitorWithAbort(lockCounter), lockCounter);
        runTest(new DozerMonitorWithAbort(lockCounter), lockCounter);
        runTest(new SerialMonitorWithAbort(lockCounter), lockCounter);



/*        System.out.println();
        System.out.println("KostaMonitorWithAbort");
        lockCounter = new LockCounter();
        m = new KostaMonitorWithAbort(lockCounter);
        lockCounter.setMonitor(m);
        threadA = new LockCounterThread(lockCounter);
        threadB = new LockCounterThread(lockCounter);
        threadC = new LockCounterThread(lockCounter);
        threadD = new LockCounterThread(lockCounter);

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();

        join(threadA, threadB, threadC, threadD);
        */

    }
}