package algo.common.concurrency.synchronizers;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * used for inter-thread communication (ex: scatter-gather or map-reduce framework)
 *
 * unlike semaphore, the count is not incremented; it's only decremented => no reuse
 * await - block the calling thread - wait() unless count == 0
 * countdown - decrement the count; and unblock all threads once count == 0
 */
public class CountdownLatch {

    private int count;
    private final Lock lock = new ReentrantLock();

    public CountdownLatch(int count) {
        this.count = count;
    }

    public void await() {
        lock.tryLock();
        try {
            while (count > 0) {
                wait();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void countdown() {
        lock.tryLock();
        try {
            if (count > 0) {
                count--;
            }
            if (count == 0) {
                notifyAll();
            }
        } finally {
            lock.unlock();
        }
    }
}
