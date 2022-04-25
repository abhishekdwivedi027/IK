package ds.arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class RangeSum {

    public static void main(String[] args) {
        RangeSum rangeSum = new RangeSum();
        int[] nums = {3, 1, 1, 5, 19, 16, 20, 16};
        int[] range = {2, 17};
        System.out.println("sum  " + getRangeSum(nums, range));
    }

    // repeated calls for range sum - precompute
    // m + (m+1) + (m+2) + ...... +  (n-1) + n
    // (1 + 2 + .... + (m-1)) + (m + (m+1) + (m+2) + ...... +  (n-1) + n) - (1 + 2 + .... + (m-1))
    // (n(n+1) - m(m-1))/2
    // range sum == subarray sum => suffix sum

    // Decrease and conquer - optimization - optimal substructure
    // globalMax[i] = max(globalMax[i-1], localMax[i])
    // localMax[i] = max(localMax[i-1] + nums[i], nums[i])
    public int maxSubarraySum(int[] nums) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        int[] globalMax = new int[n];
        globalMax[0] = nums[0];
        int[] localMax = new int[n];
        localMax[0] = nums[0];
        for (int i=1; i<n; i++) {
            localMax[i] = Math.max(localMax[i-1] + nums[i], nums[i]);
            globalMax[i] = Math.max(localMax[i], globalMax[i-1]);
        }

        return globalMax[n-1];
    }

    // how many subarrays sum up to the given target (prefixSum)
    // globalCount[i] = globalCount[i-1] + localCount[i]
    // but localCount[i-1] involves target not (target - nums[i]) => no optimal substructure
    // back to question  - how many [j, i] add to target where 0 <= j <= i
    // sum(0, i) = sum(0, j-1) + sum(j, i)
    // prefixSum(i) = prefixSum(j-1) + target; because suffixSum = target
    // prefixSum(j-1) = prefixSum - target --> how many j exist to make this true
    public int countSubarraySumEqualToTarget(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        // important: no of subarray with prefixSum 0 is 1 (empty subarray)
        map.put(0, 1);
        int prefixSum = 0;
        int totalCount = 0;
        for (int i=0; i<n; i++) {
            prefixSum += nums[i];

            // update total count when suffixSum == target
            int num = prefixSum - target;
            if (map.containsKey(num)) {
                totalCount += map.get(num);
            }

            // and update count of prefixSum
            int count = map.containsKey(prefixSum) ? map.get(prefixSum) : 0;
            map.put(prefixSum, ++count);
        }

        return totalCount;
    }

    // prefixSum(j-1) = prefixSum(i) - target
    // longest suffix with sum equal to target
    // shortest prefix - first prefix that meets the condition
    // length = i - j + 1
    public int longestSubarraySumEqualToTarget(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        // important: length of subarray with prefixSum 0 is 0 (empty subarray)
        map.put(0, 0);
        int prefixSum = 0;
        int maxLength = 0;
        for (int i=0; i<n; i++) {
            prefixSum += nums[i];

            int num = prefixSum - target;
            int totalLength = i + 1;
            if (map.containsKey(num)) {
                int prefixLength = map.get(num);
                int suffixLength = totalLength - prefixLength;
                maxLength = Math.max(suffixLength, maxLength);
            }

            // first prefix is the shortest prefix
            // subsequent prefix indices with the same sum can be ignored
            if (!map.containsKey(prefixSum)) {
                map.put(prefixSum, totalLength);
            }

        }

        return maxLength;
    }

    // prefixSum(j-1) = prefixSum(i) - suffixSum
    // if suffix sum has to be odd then prefix sum would depend on total sum
    // count => how many => store instances of even/odd prefixSums
    public int countSubarrayOddSum(int[] nums) {
        int subarrayOddSumCount = 0;
        if (nums == null || nums.length == 0) {
            return subarrayOddSumCount;
        }

        int evenPrefixSumCount = 1; // empty array sum == 0 == even
        int oddPrefixSumCount = 0;

        int sum = 0;

        for (int i=0; i< nums.length; i++) {
            sum += nums[i];

            if (sum % 2 == 0) {
                // total sum is even; if suffixSum has to be odd then prefixSum needs to be odd
                // count of odd suffixSum += count of odd prefixSum
                subarrayOddSumCount += oddPrefixSumCount;
                evenPrefixSumCount++;
            } else {
                // total sum is odd; if suffixSum has to be odd then prefixSum needs to be even
                // count of odd suffixSum += count of even prefixSum
                subarrayOddSumCount += evenPrefixSumCount;
                oddPrefixSumCount++;
            }
        }

        return subarrayOddSumCount;
    }

    // longest suffix => shortest prefix => first prefix
    // equal 0 and 1 => balanced suffix
    // the prefix can be imbalanced
    // total length = i + 1
    public int longestSubarrayWithEqual01(int[] nums) {
        int maxLength = 0;
        if (nums == null || nums.length == 0) {
            return maxLength;
        }

        // for any imbalance (#1 - #0) in prefix, we need min prefix length
        Map<Integer, Integer> map = new HashMap<>();
        map.put(0, 0);

        int prefixImbalance = 0;

        for (int i=0; i< nums.length; i++) {
            int num = nums[i];
            if (num == 1) {
                prefixImbalance++;
            } else {
                prefixImbalance--;
            }

            if (map.containsKey(prefixImbalance)) {
                int minPrefixLength = map.get(prefixImbalance);
                int maxSuffixLength = i + 1 - minPrefixLength;
                maxLength = Math.max(maxSuffixLength, maxLength);
            }

            if (!map.containsKey(prefixImbalance)) {
                map.put(prefixImbalance, i+1);
            }
        }

        return maxLength;
    }

    // again, convert suffixSum problem into prefixSum problem
    // prefixSum(j-1) = prefixSum(i) - k * n
    // prefixSum(j-1) mod k = prefixSum(i) mod k => how many such j exist
    // TODO understand the solution better
    public int countSubarraySumDivisibleByTarget(int[] nums, int k) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        Map<Integer, Integer> map = new HashMap<>();
        // no of subarray with prefixSum 0 is 1 (empty subarray)
        map.put(0, 1);
        int prefixSum = 0;
        int totalCount = 0;
        for (int i=0; i< nums.length; i++) {
            prefixSum = (prefixSum + nums[i]) % k;
            if (prefixSum < 0) {
                prefixSum += k;
            }

            int count = map.containsKey(prefixSum) ? map.get(prefixSum) : 0;
            totalCount += count;

            map.put(prefixSum, ++count);
        }

        return totalCount;
    }

    /**
     * range sum not based on index but numbers
     * @param nums
     * @param range
     * @return
     */
    public static int getRangeSum(int[] nums, int[] range) {

        if (nums == null || nums.length < 0 || range == null || range.length < 0) {
            return 0;
        }

        Arrays.sort(nums);
        int[] prefixSums = new int[nums.length];
        int prefixSum = 0;
        for (int i=0; i<nums.length; i++) {
            int num = nums[i];
            prefixSum += num;
            prefixSums[i] = prefixSum;
        }

        int min = range[0];
        // get the index of number equal to or just more than min
        int minIndex = getLeftRangeIndex(nums, min, 0, nums.length);
        int max = range[1];
        // get the index of number equal to or just less than max
        int maxIndex = getRightRangeIndex(nums, max, 0, nums.length);

        return minIndex > 0 ? prefixSums[maxIndex] - prefixSums[minIndex-1] : prefixSums[maxIndex];
    }

    private static int getLeftRangeIndex(int[] nums, int num, int left, int right) {
        // in binary search we return -1
        // here we need index just above
        if (left > right) {
            return left;
        }

        int mid = left + (right - left)/2;

        if (nums[mid] > num) {
            return getLeftRangeIndex(nums, num, left, mid-1);
        } else if (nums[mid] < num) {
            return getLeftRangeIndex(nums, num, mid+1, right);
        } else {
            // for duplicates
            if (mid-1 >= 0 && nums[mid] == nums[mid-1]) {
                return getLeftRangeIndex(nums, num, left, mid-1);
            }

            return mid;
        }
    }

    private static int getRightRangeIndex(int[] nums, int num, int left, int right) {
        // in binary search we return -1
        // here we need index just below
        if (left > right) {
            return right;
        }

        int mid = left + (right - left)/2;

        if (nums[mid] > num) {
            return getRightRangeIndex(nums, num, left, mid-1);
        } else if (nums[mid] < num) {
            return getRightRangeIndex(nums, num, mid+1, right);
        } else {
            // for duplicates
            if (mid+1<=nums.length-1 && nums[mid] == nums[mid+1]) {
                return getLeftRangeIndex(nums, num, mid+1, right);
            }
            return mid;
        }
    }
}
