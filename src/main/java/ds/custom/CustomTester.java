package ds.custom;

public class CustomTester {

    public static void main(String[] args) {
        LRUCache<Integer, Integer> lruCache = new LRUCache<>(2);
        // ["LRUCache","put","put","put","put","get","get"]
        //[[2],[2,1],[1,1],[2,3],[4,1],[1],[2]]
        lruCache.put(2, 1);
        lruCache.put(1, 1);
        lruCache.put(2, 3);
        lruCache.put(4, 1);
        System.out.println(lruCache.get(1));
        System.out.println(lruCache.get(2));
    }
}
