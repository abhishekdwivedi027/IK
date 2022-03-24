package ds.custom;

import java.util.HashMap;
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
    private final Map<K, Node<K, V>> map = new HashMap<>(); // can use ConcurrentHashMap but we are using mutex anyway
    private Node<K, V> head = null;
    private Node<K, V> tail = null;
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.size = 0;
    }

    public V get(K key) {

        lock.readLock().lock();

        try {
            V value = null;

            if (map.containsKey(key)) { // 0(1) complexity
                // do this first
                Node<K, V> node = map.get(key);
                deleteNode(node);
                insertAtHead(node);

                value = node.getVal();
            }

            return value;
        } finally {
            lock.readLock().unlock();
        }
    }

	public void put(K key, V value) {

        lock.writeLock().lock();

        try {
            if (key == null) {
                return;
            }

            Node<K, V> newNode = new Node<>(key, value);

            if (map.containsKey(key)) {
                // do this first
                Node<K, V> oldNode = map.get(key);
                deleteNode(oldNode);
                insertAtHead(newNode);
                // size doesn't change
                map.put(key, newNode);
            } else {
                // size will change
                if (size > 0 && size == capacity) {
                    Node<K, V> removed = tail;
                    deleteNode(removed);
                    map.remove(removed.getKey());
                    size--;
                }

                // add new entry
                if (capacity > 0) {
                    insertAtHead(newNode);
                    map.put(key, newNode);
                    size++;
                }
            }
        } finally {
            lock.writeLock().unlock();
        }
    }

    // this would change the head
    // this might change the tail
    private void insertAtHead(Node<K, V> node) {
        if (node == null) {
            return;
        }

        if (head != null) {
            head.setPrev(node);
            node.setNext(head);
        }
        head = node;

        if (tail == null) { // first insert
            tail = head;
        } else if (tail.getPrev() == null) { // second insert
            tail = head.getNext();
        }
    }

    private void deleteNode(Node<K, V> node) {
        if (node == null) {
            return;
        }

        Node<K, V> prev = node.getPrev();
        Node<K, V> next = node.getNext();
        node.setPrev(null);
        node.setNext(null);

        if (prev == null && next == null) { // only node being deleted
            head = null;
            tail = null;
            return;
        }

        if (prev == null) { // head being deleted
            head.setNext(null);
            next.setPrev(null);
            head = next;
            return;
        }

        if (next == null) { // tail being deleted
            tail.setPrev(null);
            prev.setNext(null);
            tail = prev;
            return;
        }

        prev.setNext(next);
        next.setPrev(prev);
    }

    // we can implement size(), isEmpty(), clear() etc.

    // inner class, node for a DLL
    private static class Node<K, V> {
        private K key;
        private V val;
        private Node<K, V> prev;
        private Node<K, V> next;

        public Node(K key, V val) {
            this.key = key;
            this.val = val;
        }

        public K getKey() {
            return key;
        }

        public V getVal() {
            return val;
        }

        public Node<K, V> getPrev() {
            return prev;
        }

        public void setPrev(Node<K, V> prev) {
            this.prev = prev;
        }

        public Node<K, V> getNext() {
            return next;
        }

        public void setNext(Node<K, V> next) {
            this.next = next;
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
