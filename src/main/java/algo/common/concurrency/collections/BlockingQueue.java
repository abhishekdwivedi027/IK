package algo.common.concurrency.collections;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockingQueue<E> {

    private final Queue<E> q = new LinkedList<>();
    private int limit;
    private final Lock lock = new ReentrantLock();
    private final Condition isEmpty = lock.newCondition();
    private final Condition isFull = lock.newCondition();

    public BlockingQueue() {
        this(10);
    }

    public BlockingQueue(int limit) {
        this.limit = limit;
    }

    public boolean offer(E e) {
        boolean added = false;
        lock.tryLock();
        try {
            while (q.size() == limit) {
                isFull.await();
            }

            q.offer(e);
            added = true;

            if (q.size() == 1) {
                isEmpty.signalAll();
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        } finally {
            lock.unlock();
        }

        return added;
    }

    public boolean poll() {
        boolean removed = false;
        lock.tryLock();
        try {
            while (q.isEmpty()) {
                isEmpty.await();
            }

            q.poll();
            removed = true;

            if (q.size() == limit - 1) {
                isFull.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }

        return removed;
    }

    public E peek() {
        E element = null;
        lock.tryLock();
        try {
            element = q.peek();
        } finally {
            lock.unlock();
        }

        return element;
    }
}
