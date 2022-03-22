package ds.arrays;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

public class SlidingWindow {

    /*
        steps -

        1. initialize variables for the leftmost window
        2. for i = k to n-1
            a. update window and its variables
            b. update global result based on local result - counting or algo.optimization

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

    private void addChar(Map<Character, Integer> letters, char in) {
        if (letters.get(in) == null) {
            letters.put(in, 1);
        } else {
            letters.put(in, letters.get(in) + 1);
        }
    }

    private void removeChar(Map<Character, Integer> letters, char out) {
        if (letters.get(out) == 1) {
            letters.remove(out);
        } else {
            letters.put(out, letters.get(out) - 1);
        }
    }

    // permutation => anagram
    // compare map/set of characters
    public boolean isPermutationPresent(String s1, String s2) {
        if (StringUtils.isBlank(s1) || StringUtils.isBlank(s2) || s1.length() > s2.length()) {
            return false;
        }

        boolean isPermutationPresent = false;

        int k = s1.length();
        int n = s2.length();

        Map<Character, Integer> letters1 = new HashMap<>();
        Map<Character, Integer> letters2 = new HashMap<>();

        // init window
        for (int i=0; i<k; i++) {
            char c1 = s1.charAt(i);
            addChar(letters1, c1);
            char c2 = s2.charAt(i);
            addChar(letters2, c2);
        }

        // slide window
        for (int i=k; i<n; i++) {
            char out = s2.charAt(i-k);
            removeChar(letters2, out);
            char in = s2.charAt(i);
            addChar(letters2, in);
            if (letters1.equals(letters2)) {
                isPermutationPresent = true;
                break;
            }
        }

        return isPermutationPresent;
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
            // ideally max and sec max will remain in the deck
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
            // it must be the max (at the left/head/first) if it has been there
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

    /*
        VARIABLE SIZE WINDOW
    */

    public int minLengthSubarraySum(int[] nums, int sum) {
        if (ArrayUtils.isEmpty(nums) || sum == 0) {
            return 0;
        }

        int left = 0;
        int leftSum = 0;
        int minLength = nums.length+1;
        for (int right = 0; right<nums.length; right++) {
            leftSum += nums[right];

            // push the right wall if subarray sum is less than target sum
            if (leftSum < sum) {
                continue;
            }

            // push the left wall as long as subarray sum is more than target sum
            while (left <= right && leftSum-nums[left] >= sum) {
                leftSum -= nums[left++];
            }

            int length = right - left + 1;
            minLength = Math.min(minLength, length);
        }

        return minLength == nums.length+1 ? 0 : minLength;
    }

    public int subarrayProductCount(int[] nums, int prod) {
        if (ArrayUtils.isEmpty(nums)) {
            return 0;
        }

        int left = 0;
        int leftProd = 1;
        int count = 0;
        for (int right = 0; right<nums.length; right++) {
            int num = nums[right];
            if (num < prod) {
                count++;
            }

            leftProd *= num;

            // push the left wall as long as subarray prod is more than or equal to target prod
            while (left <= right && leftProd >= prod) {
                leftProd /= nums[left++];
            }

            // count subarrays
            if (left < right && leftProd < prod) {
                count += right - left;
            }
        }

        return count;
    }
}
