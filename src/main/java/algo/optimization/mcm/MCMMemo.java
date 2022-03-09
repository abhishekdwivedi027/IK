package algo.optimization.mcm;

public class MCMMemo {

    private int[][] memo;

    public int minCost(int[] nums) {
        memo = new int[nums.length+1][nums.length+1];
        // base condition => initialization
        // unlike knapsack and lcs - base condition is not the smallest VALID input but INVALID input
        for (int i=0; i<=nums.length; i++) {
            for (int j=0; j<=nums.length; j++) {
                memo[i][j] = -1;
            }
        }

        return minCostHelper(nums, 1, nums.length-1);
    }

    private int minCostHelper(int[] nums, int left, int right) {
        if (memo[left][right] != -1) {
            return memo[left][right];
        }

        if (left >= right) {
            return 0;
        }

        int minCost = Integer.MAX_VALUE;
        for (int k=left; k<right; k++) {
            int tempMinCost = minCostHelper(nums, left, k)
                    + minCostHelper(nums, k+1, right)
                    + nums[left-1] * nums[k] * nums[right];
            minCost = Math.min(minCost, tempMinCost);
        }

        memo[left][right] = minCost;
        return memo[left][right];
    }
}
