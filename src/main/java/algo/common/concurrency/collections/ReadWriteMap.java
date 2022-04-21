package algo.common.concurrency.collections;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ReadWriteMap<K, V> {

    private Map<K, V> map = new HashMap<>();
    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Lock readLock = lock.readLock();
    private Lock writeLock = lock.writeLock();

    public V get(K key) throws InterruptedException {
        V value = null;
        readLock.lock();
        try {
            value = map.get(key);
        } finally {
            readLock.unlock();
        }

        return value;
    }

    public void put(K key, V value) throws InterruptedException {
        writeLock.lock();
        try {
            map.put(key, value);
        } finally {
            writeLock.unlock();
        }
    }
}
