package ds.arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class SlidingWindowFixed {

    /*
        steps -

        1. initialize variables for the leftmost window
        2. for i = k to n-1
            a. update window and its variables
            b. update global result based on local result - counting or optimization

        types -

        1. fixed length
        2. variable length
    */

    /*
        FIXED SIZE WINDOW
    */

    // increment count if avg(window) >= threshold
    // sum(window) >= threshold * window width
    public int windowAverageThresholdCount(int[] nums, int threshold, int k) {
        if (k > nums.length) {
            return 0;
        }

        // initialization
        int sum = 0;
        for (int i=0; i<k; i++) {
            sum += nums[i];
        }

        int minSum = threshold * k;
        int count = sum >= minSum ? 1 : 0;

        // sliding window
        int n = nums.length;;
        for (int i=k; i<n; i++) {
            // add nums[i]
            // remove nums[i-k]
            sum += nums[i] - nums[i-k];
            if (sum >= minSum) {
                count++;
            }
        }

        return count;
    }

    // how do we know if a character is repeated?
    // char and char count => map
    public int noRepeatedCharactersCount(String s, int k) {
        if (k > s.length()) {
            return 0;
        }

        int count = 0;
        
        // initialization
        Map<Character, Integer> letters = new HashMap<>();
        for (int i=0; i<k; i++) {
            addChar(letters, s.charAt(i));
        }

        // if all characters are distinct then map size == window size
        if (letters.size() == k) {
            count++;
        }

        // slide window
        for (int i=k; i<s.length(); i++) {
            // add nums[i]
            // remove nums[i-k]
            removeChar(letters, s.charAt(i - k));
            addChar(letters, s.charAt(i));
            if (letters.size() == k) {
                count++;
            }
        }

        return count;
    }

    // add new entry - map size increases
    private void addChar(Map<Character, Integer> letters, char in) {
        if (letters.get(in) == null) {
            letters.put(in, 1);
        } else {
            letters.put(in, letters.get(in) + 1);
        }
    }

    // remove last entry - map size decreases
    private void removeChar(Map<Character, Integer> letters, char out) {
        if (letters.get(out) == 1) {
            letters.remove(out);
        } else {
            letters.put(out, letters.get(out) - 1);
        }
    }

    // permutation => anagram
    // don't compare strings; compare map/set of characters
    // in fact compare map - compare both keys (chars) and values (num of each char)
    // string => Map<Character, Integer>
    public boolean isPermutationPresent(String s1, String s2) {
        if (StringUtils.isBlank(s1) || StringUtils.isBlank(s2) || s1.length() > s2.length()) {
            return false;
        }

        int k = s1.length();
        int n = s2.length();

        Map<Character, Integer> letters1 = new HashMap<>();
        Map<Character, Integer> letters2 = new HashMap<>();

        // init window
        for (int i=0; i<k; i++) {
            addChar(letters1, s1.charAt(i));
            addChar(letters2, s2.charAt(i));
        }

        // map equality - both keys and values
        if (letters1.equals(letters2)) {
            return true;
        }

        // slide window
        for (int i=k; i<n; i++) {
            addChar(letters2, s2.charAt(i));
            removeChar(letters2, s2.charAt(i-k));
            if (letters1.equals(letters2)) {
                return true;
            }
        }

        return false;
    }

    public int maxTotalPointsFromSides(int[] cardPoints, int k) {
        int maxPoints = 0;
        if (cardPoints == null || cardPoints.length == 0 || k == 0) {
            return maxPoints;
        }

        int n = cardPoints.length;
        int m = cardPoints.length - k; // size of sliding window
        int points = 0;
        int totalPoints = 0;
        int minPoints = Integer.MAX_VALUE; // maxPoints = sum - minPoints

        // init
        for (int i=0; i<m; i++) {
            points += cardPoints[i];
        }

        minPoints = Math.min(points, minPoints);
        totalPoints = points;

        for (int i=m; i<n; i++) {
            points -= cardPoints[i-m];
            points += cardPoints[i];
            minPoints = Math.min(points, minPoints);
            totalPoints += cardPoints[i];
        }

        return totalPoints - minPoints;
    }

    /*
        a data structure that gives max elements - heap
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>(Collections.reverseOrder());

        we don't need to keep everything in the window
        just keep max and second max
    */
    public int[] windowMax(int[] nums, int k) {
        if (nums == null || k > nums.length) {
            return null;
        }

        int n = nums.length;
        int[] result = new int[n-k+1];

        int resultCount = 0;

        // LinkedList is an implementation of List and Deque interface
        Deque<Integer> deck = new LinkedList<>();

        // init
        for (int i=0; i<k; i++) {
            int num = nums[i];
            // don't keep unnecessary elements - smaller than num will be removed from right/tail/last
            // ideally max and (maybe) sec max will remain in the deck
            while (!deck.isEmpty() && num > deck.getLast()) {
                deck.removeLast();
            }
            deck.addLast(num);
        }

        result[resultCount++] = deck.getFirst();

        // slide
        for (int i=k; i<n; i++) {
            int out = nums[i-k];
            // this number may or may not have been in the deck
            // it must be the max (at the left/head/first) if it has been there - not even sec max
            if (out == deck.getFirst()) {
                deck.removeFirst();
            }

            int in = nums[i];
            // all nums smaller than num must be purged
            while (!deck.isEmpty() && in > deck.getLast()) {
                deck.removeLast();
            }
            deck.addLast(in);

            result[resultCount++] = deck.getFirst();
        }

        return result;
    }

    /*
        lot of search/add/remove taking place in heap
        should be implemented by BST under the hood
     */
    public float[] windowMedian(int[] nums, int k) {
        if (nums == null || k > nums.length) {
            return null;
        }

        int n = nums.length;
        PriorityQueue<Integer> minHeapOfMaxHalf = new PriorityQueue<>();
        PriorityQueue<Integer> maxHeapOfMinHalf = new PriorityQueue<>(Collections.reverseOrder());
        float[] result = new float[n-k+1];
        int resultCount = 0;

        // init
        for (int i=0; i<k; i++) {
            int num = nums[i];
            addNumber(num, maxHeapOfMinHalf, minHeapOfMaxHalf);
        }
        result[resultCount++] = getMedian(maxHeapOfMinHalf, minHeapOfMaxHalf);

        // slide
        for (int i=k; i<n; i++) {
            int out = nums[i-k];
            int in = nums[i];
            removeNumber(out, maxHeapOfMinHalf, minHeapOfMaxHalf);
            addNumber(in, maxHeapOfMinHalf, minHeapOfMaxHalf);

            result[resultCount++] = getMedian(maxHeapOfMinHalf, minHeapOfMaxHalf);
        }

        return result;
    }

    private void addNumber(int in, PriorityQueue<Integer> maxHeapOfMinHalf, PriorityQueue<Integer> minHeapOfMaxHalf) {
        if (!maxHeapOfMinHalf.isEmpty() && in < maxHeapOfMinHalf.peek()) {
            maxHeapOfMinHalf.add(in);
        } else if (!minHeapOfMaxHalf.isEmpty() && in >= minHeapOfMaxHalf.peek()) {
            minHeapOfMaxHalf.add(in);
        } else {
            maxHeapOfMinHalf.add(in);
        }

        rebalanceHeaps(maxHeapOfMinHalf, minHeapOfMaxHalf);
    }

    private void removeNumber(int out, PriorityQueue<Integer> maxHeapOfMinHalf, PriorityQueue<Integer> minHeapOfMaxHalf) {
        if (!maxHeapOfMinHalf.isEmpty() && out < maxHeapOfMinHalf.peek()) {
            maxHeapOfMinHalf.remove(out);
        } else if (!minHeapOfMaxHalf.isEmpty() && out >= minHeapOfMaxHalf.peek()) {
            minHeapOfMaxHalf.remove(out);
        }

        rebalanceHeaps(maxHeapOfMinHalf, minHeapOfMaxHalf);
    }

    private void rebalanceHeaps(PriorityQueue<Integer> maxHeapOfMinHalf, PriorityQueue<Integer> minHeapOfMaxHalf) {
        if (maxHeapOfMinHalf.size() > minHeapOfMaxHalf.size() + 1) {
            minHeapOfMaxHalf.add(maxHeapOfMinHalf.poll());
        } else if (minHeapOfMaxHalf.size() > maxHeapOfMinHalf.size() + 1) {
            maxHeapOfMinHalf.add(minHeapOfMaxHalf.poll());
        }
    }

    public float getMedian(PriorityQueue<Integer> maxHeapOfMinHalf, PriorityQueue<Integer> minHeapOfMaxHalf) {
        int maxOfMinHalf = maxHeapOfMinHalf.isEmpty() ? 0 : maxHeapOfMinHalf.peek();
        int minOfMaxHalf = minHeapOfMaxHalf.isEmpty() ? 0 : minHeapOfMaxHalf.peek();
        if (maxHeapOfMinHalf.size() == minHeapOfMaxHalf.size() + 1) {
            return maxOfMinHalf;
        } else if (minHeapOfMaxHalf.size() == maxHeapOfMinHalf.size() + 1) {
            return minOfMaxHalf;
        } else {
            return ((float)(maxOfMinHalf + minOfMaxHalf)/2);
        }
    }
}
