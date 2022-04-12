package ds.arrays;

import org.apache.commons.lang3.ArrayUtils;

import java.util.*;

public class SlidingWindowVariable {

     /*
        VARIABLE SIZE WINDOW - optimization - max/min in array
    */

    public int minLengthSubarraySum(int[] nums, int target) {
        if (ArrayUtils.isEmpty(nums) || target == 0) {
            return 0;
        }

        int left = 0;
        int sum = 0;
        // min length is initialized with one more than max
        int minLength = nums.length+1;
        for (int right = 0; right<nums.length; right++) {
            sum += nums[right];

            // push the left wall as long as subarray sum is more than target sum
            while (left <= right && sum-nums[left] > target) {
                sum -= nums[left++];
            }

            int length = right - left + 1;
            minLength = Math.min(minLength, length);
        }

        return minLength == nums.length+1 ? 0 : minLength;
    }

    // question involves selection from both ends
    // mirror it to sliding window problem
    // min length from both ends => max length in the middle
    // optimized window => variable sliding window
    public int maxLengthSubarraySum(int[] nums, int target) {
        if (ArrayUtils.isEmpty(nums) || target == 0) {
            return 0;
        }

        int left = 0;
        int sum = 0;
        // max length is initialized with one less than min
        int maxLength = -1;
        for (int right = 0; right<nums.length; right++) {
            sum += nums[right];

            // push the left wall as long as subarray sum is more than target sum
            while (left <= right && sum-nums[left] >= target) {
                sum -= nums[left++];
            }

            int length = right - left + 1;
            maxLength = Math.max(maxLength, length);
        }

        return maxLength == -1 ? 0 : maxLength;
    }


    public int subarrayProductLessThanTargetCount(int[] nums, int target) {
        if (ArrayUtils.isEmpty(nums)) {
            return 0;
        }

        int left = 0;
        int prod = 1;
        int count = 0;
        for (int right = 0; right<nums.length; right++) {
            int num = nums[right];

            prod *= num;

            // push the left wall as long as subarray prod is more than or equal to target prod
            while (left <= right && prod > target) {
                num = nums[left++];
                prod /= num;
            }

            // count subarrays
            count += right - left + 1;
        }

        return count;
    }

    public int maxConsecutiveOnes(int[] nums, int flipsAllowed) {
        int maxLength = 0;
        if (nums == null || nums.length == 0) {
            return maxLength;
        }

        int zeroes = 0;
        int left = 0;
        for (int right=0; right<nums.length; right++) {
            int num = nums[right];
            if (num == 0) {
                zeroes++;
            }

            while (left <= right && zeroes > flipsAllowed) {
                if (nums[left++] == 0) {
                    zeroes--;
                }
            }

            int length = right - left + 1;
            maxLength = Math.max(maxLength, length);
        }

        return maxLength;
    }

    // fruitTypes - array of integers denoting type of fruits
    // you have two baskets and you can put one type in one basket
    // start from the left and collect fruits as long as you see the 3rd type - then drop 1st type and gather 3rd
    public int maxFruits(int[] fruits) {
        if (ArrayUtils.isEmpty(fruits)) {
            return 0;
        }

        Map<Integer, Integer> fruitTypes = new HashMap();

        int left = 0;
        int num = 0;
        int maxNum = 0;
        for (int right=0; right<fruits.length; right++) {
            int rightType = fruits[right];
            int rightTypeCount = fruitTypes.containsKey(rightType) ? fruitTypes.get(rightType) : 0;
            // add the right type
            fruitTypes.put(rightType, ++rightTypeCount);
            num++;

            // remove the left type
            while (left <= right && fruitTypes.size() > 2) {
                int leftType = fruits[left++];
                int leftTypeCount = fruitTypes.get(leftType);
                fruitTypes.put(leftType, --leftTypeCount);
                if (leftTypeCount == 0) {
                    fruitTypes.remove(leftType);
                }
                num--;
            }


            maxNum = Math.max(maxNum, num);
        }

        return maxNum;
    }

    // subarray with distinct digits
    public int maxSumSubarrayDistinctAll(int[] nums) {
        int maxScore = 0;
        if (nums == null || nums.length == 0) {
            return maxScore;
        }

        Map<Integer, Integer> map = new HashMap<>();

        int score = 0;
        int left = 0;
        for (int right=0; right<nums.length; right++) {
            int num = nums[right];
            int count = map.containsKey(num) ? map.get(num) : 0;
            boolean duplicate = count != 0;
            map.put(num, ++count);
            score += num;

            while (left <= right && duplicate) {
                num = nums[left++];
                count = map.get(num);
                map.put(num, --count);
                score -= num;
                if (count == 0) {
                    map.remove(num);
                } else if (count == 1) {
                    duplicate = false;
                }
            }

            maxScore = Math.max(maxScore, score);
        }

        return maxScore;
    }

    // with max k distinct characters
    public String longestSubstringDistinctMaxK(String s, int k) {
        String longestSubstring = "";
        if (s == null || s.length() < 2) {
            return longestSubstring;
        }

        Map<Character, Integer> map = new HashMap<>();
        int maxLength = 0;
        int left = 0;
        for (int right=0; right<s.length(); right++) {
            char c = s.charAt(right);
            int count = map.containsKey(c) ? map.get(c) : 0;
            map.put(c, ++count);

            while (left<=right && map.size()>k) {
                c = s.charAt(left++);
                count = map.get(c);
                map.put(c, --count);
                if (count == 0) {
                    map.remove(c);
                }
            }
            int length = right - left + 1;
            if (length > maxLength) {
                maxLength = length;
                longestSubstring = s.substring(left, right+1);
            }
        }

        return longestSubstring;
    }

    public String longestSubstringDistinctAll(String s) {
        String longestSubstring = "";
        if (s == null || s.length() == 0) {
            return longestSubstring;
        }

        Map<Character, Integer> map = new HashMap<>();

        int left = 0;
        int maxLength = 0;
        for (int right=0; right<s.length(); right++) {
            char c = s.charAt(right);
            int count = map.containsKey(c) ? map.get(c) : 0;
            boolean duplicate = count != 0;
            map.put(c, ++count);

            while (left <= right && duplicate) {
                c = s.charAt(left++);
                count = map.get(c);
                map.put(c, --count);
                if (count == 0) {
                    map.remove(c);
                } else if (count == 1) {
                    duplicate = false;
                }
            }

            int length = right - left + 1;
            if (length > maxLength) {
                maxLength = length;
                longestSubstring = s.substring(left, right+1);
            }
        }
        return longestSubstring;
    }

    // length of longest subarray in which maxNum - minNum <= limit
    public int longestSubarrayDiffLimit(int[] nums, int limit) {
        int maxLength = 0;
        if (nums == null || nums.length == 0) {
            return maxLength;
        }

        Deque<Integer> minDeck = new LinkedList<>();
        Deque<Integer> maxDeck = new LinkedList<>();

        int left = 0;
        for (int right = 0; right < nums.length; right++) {
            int num = nums[right];

            // remove
            while (!minDeck.isEmpty() && num < minDeck.getLast()) {
                minDeck.removeLast();
            }
            // add
            minDeck.addLast(num);

            // remove
            while (!maxDeck.isEmpty() && num > maxDeck.getLast()) {
                maxDeck.removeLast();
            }
            // add
            maxDeck.addLast(num);

            while (left <= right && maxDeck.getFirst() - minDeck.getFirst() > limit) {
                num = nums[left++];
                if (num == maxDeck.getFirst()) {
                    maxDeck.removeFirst();
                }
                if (num == minDeck.getFirst()) {
                    maxDeck.removeFirst();
                }
            }

            int length = right - left + 1;
            maxLength = Math.max(length, maxLength);

        }

        return maxLength;
    }

    // ######################  HARD  ######################

    // all subarrays with distinct K integers
    public int subarrayCountDistinctK(int[] nums, int k) {
        int subarrayCount = 0;
        if (nums == null || nums.length < k) {
            return subarrayCount;
        }

        Map<Integer, Integer> notLessThanK = new HashMap<>();
        int leftLeft = 0;

        Map<Integer, Integer> lessThanK = new HashMap<>();
        int leftRight = 0;

        int num = 0;
        int count = 0;

        for (int right=0; right<nums.length; right++) {
            num = nums[right];
            count = notLessThanK.containsKey(num) ? notLessThanK.get(num) : 0;
            notLessThanK.put(num, ++count);
            while(leftLeft <= right && notLessThanK.size() > k) {
                num = nums[leftLeft++];
                count = notLessThanK.get(num);
                notLessThanK.put(num, --count);
                if (count == 0) {
                    notLessThanK.remove(num);
                }
            }

            num = nums[right];
            count = lessThanK.containsKey(num) ? lessThanK.get(num) : 0;
            lessThanK.put(num, ++count);
            while(leftRight <= right && lessThanK.size() >= k) {
                num = nums[leftRight++];
                count = lessThanK.get(num);
                lessThanK.put(num, --count);
                if (count == 0) {
                    lessThanK.remove(num);
                }
            }

            subarrayCount += leftRight - leftLeft;
        }

        return subarrayCount;
    }

    public String minWindowSubstring(String big, String small) {
        String minWindowSubstring = "";
        if (big == null || small == null || big.length() < small.length()) {
            return minWindowSubstring;
        }

        int minWindowSubstringLength = big.length() + 1;

        Map<Character, Integer> bigMap = new HashMap<>();
        Map<Character, Integer> smallMap = new HashMap<>();

        for (char c:small.toCharArray()) {
            int count = smallMap.containsKey(c) ? smallMap.get(c) : 0;
            smallMap.put(c, ++count);
        }

        int left = 0;
        for (int right=0; right<big.length(); right++) {
            char c = big.charAt(right);
            int count = bigMap.containsKey(c) ? bigMap.get(c) : 0;
            bigMap.put(c, ++count);

            boolean removed = false;
            while (left <= right && contains(bigMap, smallMap)) {
                c = big.charAt(left++);
                count = bigMap.containsKey(c) ? bigMap.get(c) : 0;
                bigMap.put(c, --count);
                if (count == 0) {
                    bigMap.remove(c);
                }
                removed = true;
            }

            if (removed) {
                left--;
                count = bigMap.containsKey(c) ? bigMap.get(c) : 0;
                bigMap.put(c, ++count);
                int length = right - left + 1;
                if (length < minWindowSubstringLength) {
                    minWindowSubstringLength = length;
                    minWindowSubstring = big.substring(left, right + 1);
                }
            }
        }

        return minWindowSubstring;
    }

    private boolean contains(Map<Character, Integer> bigMap, Map<Character, Integer> smallMap) {
        if (bigMap.size() < smallMap.size()) {
            return false;
        }

        Set<Character> bigSet = bigMap.keySet();
        Set<Character> smallSet = smallMap.keySet();
        if (!bigSet.containsAll(smallSet)) {
            return false;
        }

        for (char c: smallSet) {
            if (bigMap.get(c) < smallMap.get(c)) {
                return false;
            }
        }

        return true;
    }
}
