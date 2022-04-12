package ds.arrays;

import java.util.HashMap;
import java.util.Map;

public class RangeSum {

    public static void main(String[] args) {
        RangeSum rangeSum = new RangeSum();

        int[][] matrix = {{3,0,1,4,2},{5,6,3,2,1},{1,2,0,1,5},{4,1,0,1,7},{1,0,3,0,5}};
        System.out.println("range sum     " + rangeSum.matrixRangeSum(matrix, 2, 1, 4, 3));
    }

    // repeated calls for range sum - precompute
    // m + (m+1) + (m+2) + ...... +  (n-1) + n
    // (1 + 2 + .... + (m-1)) + (m + (m+1) + (m+2) + ...... +  (n-1) + n) - (1 + 2 + .... + (m-1))
    // (n(n+1) - m(m-1))/2
    // range sum == subarray sum

    // sum of all numbers inside a grid
    // prefixSums[i][j] = matrix[i-1][j-1] + prefixSums[i-1][j] + prefixSums[i][j-1] - prefixSums[i-1][j-1];
    public int matrixRangeSum(int[][] matrix, int row1, int col1, int row2, int col2) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] prefixSums = new int[m+1][n+1];

        // init prefixSums
        for (int i=0; i<m+1; i++) {
            for (int j=0; j<n+1; j++) {
                if (i == 0 || j == 0) {
                    prefixSums[i][j] = 0;
                }
            }
        }

        for (int i=1; i<m+1; i++) {
            for (int j=1; j<n+1; j++) {
                prefixSums[i][j] = matrix[i-1][j-1] + prefixSums[i-1][j] + prefixSums[i][j-1] - prefixSums[i-1][j-1];
            }
        }

        int r1 = row1+1;
        int r2 = row2+1;
        int c1 = col1+1;
        int c2 = col2+1;
        return prefixSums[r2][c2] - prefixSums[r1-1][c2] - prefixSums[r2][c1-1] + prefixSums[r1-1][c1-1];
    }

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
    // localCount[i] = num of subarrays ending at i-1 and summing (target - nums[i]) + target == nums[i] ? 1 : 0
    // but localCount[i-1] involves target not (target - nums[i]) => no optimal substructure
    // back to question  - how many [j, i] add to target where 0 <= j <= i
    // prefixSum(0, i) = prefixSum(0, j-1) + prefixSum(j, i)
    // prefixSum(j-1) = prefixSum(i) - target => how many such j exist?
    public int countSubarraySum(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return 0;
        }

        int n = nums.length;
        Map<Integer, Integer> map = new HashMap<>();
        // this is for the edge case when the last element equals target
        map.put(0, 1);
        int prefixSum = 0;
        int totalCount = 0;
        for (int i=0; i<n; i++) {
            prefixSum += nums[i];

            int num = prefixSum - target;
            int count = map.containsKey(num) ? map.get(num) : 0;
            totalCount += count;

            count = map.containsKey(prefixSum) ? map.get(prefixSum) : 0;
            map.put(prefixSum, ++count);
        }

        return totalCount;
    }
}
