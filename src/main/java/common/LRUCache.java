package common;

import java.util.HashMap;
import java.util.Map;

public class LRUCache {
    
    private int capacity;
    private Map<Integer, Node<Integer>> map;
    private Node<Integer> head;
    private Node<Integer> tail;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.map = new HashMap<Integer, Node<Integer>>();
    }
    
    public int get(int key) {
    	
        if (!map.containsKey(key)) {
            return -1;
        }
        
        return getNode(key).value;
    }
    
    private Node<Integer> getNode(int key) {
        Node<Integer> node = map.get(key);
        // make this head - if not already
        if (node != head) {
        	detachNode(node);
        	attachNodeToHead(node);
        }
        return node;
    }

	public void put(int key, int value) {
        
        if (map.containsKey(key)) {
            // get will make this head - no need to do it here
            Node<Integer> node = getNode(key);
            node.value = value;    
        } else {
            // add new entry
            Node<Integer> node = new Node<>(key, value);
            // edge case
            if (capacity == 1) {
            	head = node;
            	tail = node;
            	map.clear();
            	map.put(key, node);
            	return;
            }
            
            // check capacity
            if (map.size() == capacity) {
                map.remove(tail.key);
                detachTailNode();
            }
            
            map.put(key, node);
            if (map.size() == 1) {
                head = node;
                tail = node;
            } else {
                attachNodeToHead(node);
            }
        } 
    }
    
    private void detachNode(Node<Integer> node) {
        
    	Node<Integer> nextNode = node.next; 
        Node<Integer> prevNode = node.prev;
		
        // node is head
        if (nextNode == null) {
        	head = prevNode;
        	head.next = null;
        } else {
        	nextNode.prev = prevNode;      	
        }
        
        // node is tail
        if (prevNode == null) {
        	tail = nextNode;
        	tail.prev = null;
        } else {
        	prevNode.next = nextNode;
        }
	}
    
	private void attachNodeToHead(Node<Integer> node) {
        if (node == head) {
            return;
        }
        head.next = node;
        node.next = null;
        node.prev = head;
        head = node;
	}
    
    private void detachTailNode() {
        Node<Integer> nextNode = tail.next;
        tail.next = null;
        tail = nextNode;
        tail.prev = null;
    }
    
    private class Node<E> {
    	E key;
        E value;
        Node<E> next;
        Node<E> prev;
        
        Node(E key, E value) {
        	this.key = key;
            this.value = value;
        }
    }
}

/**
 * Your LRUCache object will be instantiated and called as such:
 * LRUCache obj = new LRUCache(capacity);
 * int param_1 = obj.get(key);
 * obj.put(key,value);
 */
