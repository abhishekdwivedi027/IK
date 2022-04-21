package algo.common.concurrency.collections.mutex;

/**
 * Read Access   	If no threads are writing, and no threads have requested write access.
 * Write Access   	If no threads are reading or writing.
 * Assumption       Writes << Reads
 */
public class ReadWriteLock {

    /**
     * lock = blocking wait and unblocking notify based on conditions
     * we can implement this using implicit as well as explicit lock
     * since this lock is not reentrant, one thread can have max one
     */
    private int readers       = 0;
    private int writers       = 0;
    private int writeRequests = 0;

    public synchronized void lockRead() throws InterruptedException {
        // if no threads are writing, and no threads have requested write access.
        while(writers > 0 || writeRequests > 0){
            wait();
        }
        readers++;
    }

    public synchronized void unlockRead(){
        readers--;
        notifyAll();
    }

    public synchronized void lockWrite() throws InterruptedException {
        writeRequests++;

        // if no threads are reading or writing.
        while(readers > 0 || writers > 0){
            wait();
        }

        writeRequests--;
        writers++;
    }

    public synchronized void unlockWrite() throws InterruptedException {
        writers--;
        notifyAll();
    }
}
