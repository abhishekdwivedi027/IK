package ds.custom;

import java.util.*;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class LFUCache<K, V> {

    /**
     * LFU Cache => LFU Map
     * we need one hashtable for the map/cache part, and
     * we need one hashtable for the LFU part
     * when you put an entry, and size == capacity, least frequently used entry is removed
     * when there are more than one entry with equal frequencies then LRU entry is removed
     * we need to track both frequency and recency
     *
     */

    private int size;
    private int capacity;
    private final Map<K, V> map = new HashMap<>();
    private final Map<K, Integer> frequencyMap = new HashMap<>();
    private final TreeMap<Integer, LinkedHashSet<K>> frequencyToKeysMap = new TreeMap<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public LFUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
    }

    // 0(1)
    public V get(K key) {

        lock.readLock().tryLock();

        try {
            V value = null;

            if (map.containsKey(key)) {
                updateFrequency(key);
                value = map.get(key); // 0(1)
            }

            return value;
        }
        finally {
            lock.readLock().unlock();
        }
    }

    // 0(1)
    public void put(K key, V value) {

        lock.writeLock().tryLock();

        try {
            if (key == null) {
                return;
            }

            if (map.containsKey(key)) {
                updateFrequency(key);
                // size doesn't change
                map.put(key, value); // 0(1)
            } else {
                if (size > 0 && size == capacity) {
                    removeLeastFrequentItem();
                    size--;
                }

                // add new entry - frequency 1
                if (capacity > 0) {
                    addNewItem(key, value);
                    size++;
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    private void updateFrequency(K key) {
        int frequency = frequencyMap.get(key);
        frequencyMap.put(key, frequency+1);

        frequencyToKeysMap.get(frequency).remove(key);
        if (frequencyToKeysMap.get(frequency).isEmpty()) {
            frequencyToKeysMap.remove(frequency);
        }

        frequencyToKeysMap.putIfAbsent(frequency+1, new LinkedHashSet<>());
        frequencyToKeysMap.get(frequency+1).add(key);
    }

    private void removeLeastFrequentItem() {
        // find the key to be removed
        Integer minFrequency = frequencyToKeysMap.firstKey(); // tree map sorted by natural order
        LinkedHashSet<K> minFrequencyKeys = frequencyToKeysMap.get(minFrequency);
        // min element of LinkedHashSet - first element
        K removeKey = minFrequencyKeys.stream().findFirst().get();
        minFrequencyKeys.remove(removeKey);
        if (minFrequencyKeys.isEmpty()) {
            frequencyToKeysMap.remove(minFrequency);
        }

        // remove this key from both maps
        frequencyMap.remove(removeKey);
        map.remove(removeKey);
    }

    private void addNewItem(K key, V value) {
        frequencyMap.put(key, 1);

        frequencyToKeysMap.putIfAbsent(1, new LinkedHashSet<>());
        frequencyToKeysMap.get(1).add(key);

        map.put(key, value);
    }
}
