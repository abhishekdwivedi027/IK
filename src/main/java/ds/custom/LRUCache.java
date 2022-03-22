package ds.custom;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LRUCache<K, V> {

    /**
     * LRU Cache => LinkedHashMap - capacity, load factor, access order = true (false by default - insertion order by default)
     *
     * https://www.baeldung.com/java-lru-cache
     *
     * we need one hashtable for the map/cache part, and
     * we need one doubly linked list or a deque for the LRU part
     * FYI - LinkedList is an implementation of List and Deque interface
     */

    private int size;
    private int capacity;
    private final Map<K, V> map = new HashMap<>(); // can use ConcurrentHashMap but we are using mutex anyway
    private final LinkedList<K> list = new LinkedList<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
    }

    // 0(n)
    public V get(K key) {

        lock.readLock().lock();

        try {
            V value = null;

            if (map.containsKey(key)) { // 0(1) complexity
                // do this first
                list.remove(key); // 0(n) complexity - search
                list.addFirst(key); // 0(1) complexity

                value = map.get(key);
            }

            return value;
        } finally {
            lock.readLock().unlock();
        }
    }

    // 0(n)
	public void put(K key, V value) {

        lock.writeLock().lock();

        try {
            if (key == null) {
                return;
            }

            if (map.containsKey(key)) {
                // do this first
                list.remove(key); // 0(n) complexity - search
                list.addFirst(key); // 0(1) complexity
                // size doesn't change
                map.put(key, value); // 0(1)
            } else {
                // size will change
                if (size > 0 && size == capacity) {
                    K removed = list.removeLast(); // 0(1)
                    map.remove(removed); // 0(1)
                    size--;
                }

                // add new entry
                if (capacity > 0) {
                    map.put(key, value); // 0(1)
                    list.addFirst(key); // 0(1)
                    size++;
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    // we can implement size(), isEmpty(), clear() etc.
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
