package algo.common.concurrency.synchronizers;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * state = counter = number of threads accessing a resource
 * if limit == 1 => can be used like a mutex
 * acquire - increment counter. block if/while counter == limit.
 * release - decrement counter. block if/while counter == 0.
 * there are two conditions => use explicit lock
 */
public class CountingSemaphore {

    private int limit;
    private int counter;
    private final Lock lock = new ReentrantLock();
    private final Condition allTaken = lock.newCondition();
    private final Condition noneTaken = lock.newCondition();


    public CountingSemaphore(int limit) {
        this.limit = limit;
        this.counter = 0;
    }

    public synchronized void acquire() {
        lock.tryLock();
        try {
            while (counter == limit) {
                allTaken.await();
            }

            counter++;

            if (counter == 1) {
                noneTaken.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public synchronized void release() {
        lock.tryLock();
        try {
            while (counter == 0) {
                noneTaken.await();
            }

            counter--;

            if (counter == limit - 1) {
                allTaken.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
