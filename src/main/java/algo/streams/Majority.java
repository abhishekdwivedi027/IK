package algo.streams;

import java.util.HashMap;
import java.util.Map;

/**
 * here majority is defined as a num > n/2
 * assuming that the majority does exist in the stream
 * if it's defined differently - say num > n/3 then there will be slight tweak in the solutions
 * like we will keep 2 keys in map, increase the freq when added again
 * and reduce/remove both by one if a new key is added
 */
public class Majority {

    private final Map<Integer, Integer> map = new HashMap<>();

    public void addNumber(int num) {
        if (map.isEmpty()) {
            // because map size <= 1
            map.put(num, 1);
        } else {
            if (map.containsKey(num)) {
                int freq = map.get(num);
                map.put(num, ++freq);
            } else {
                for (int key: map.keySet()) {
                    int val = map.get(key);
                    map.put(key, --val);
                }
                map.values().removeIf(val -> val == 0);
            }
        }
    }

    /*
        map.keySet().stream().forEach(key -> map.put(key, 0));
        Set<Integer> set = map.keySet();
        for (int i=0; i<n; i++) {
            int num = nums[i];
            if (set.contains(num)) {
                int freq = map.get(num);
                map.put(num, ++freq);
            }

        }

        return map.keySet().stream().filter(k -> map.get(k) > n/3).toList();
     */
    public int getMajority() {
        // sole survivor is the majority number
        return map.keySet().iterator().next();
    }
}
