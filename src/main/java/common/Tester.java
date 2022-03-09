package common;

public class Tester {
	
	public static void main(String[] args) {
		LRUCache cache = new LRUCache(2);
		cache.put(2, 1);
		cache.put(1, 1);
		cache.put(2, 3);
		cache.put(4, 1);
		cache.get(1);
		cache.get(2);
		// ["LRUCache","put","put","put","put","get","get"]
		// [[2],[2,1],[1,1],[2,3],[4,1],[1],[2]]
	}

}
