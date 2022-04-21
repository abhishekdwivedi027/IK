package ds.custom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * add, remove, find, getRandom - any random number from the stored numbers - at constant time
 * assumption - no duplicates
 */
public class NumberBox {

    private final Map<Integer, Integer> map = new HashMap<>();
    private final List<Integer> list = new ArrayList<>();

    public void add(int num) {
        if (map.containsKey(num)) {
            return;
        }

        int index = list.size();
        list.add(index, num);
        map.put(num, index);
    }

    public void remove(int num) {
        if (!map.containsKey(num)) {
            return;
        }

        int index = map.get(num);
        int lastIndex = list.size()-1;
        int lastNum = list.get(lastIndex);
        map.remove(num);
        map.put(lastNum, index);
        list.add(index, lastNum);
        list.remove(lastIndex);
    }

    public boolean isPresent(int num) {
        return map.containsKey(num);
    }

    public int getRandom() {
        int minIndex = 0;
        int maxIndex = list.size()-1;
        int randomIndex = (int)((Math.random() * (maxIndex - minIndex)) + minIndex);
        return list.get(randomIndex);
    }
}
