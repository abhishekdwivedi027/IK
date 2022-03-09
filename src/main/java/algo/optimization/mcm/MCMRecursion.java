package algo.optimization.mcm;

public class MCMRecursion {

    public int minCost(int[] nums) {
        return minCostHelper(nums, 1, nums.length-1);
    }

    // private int minCost = Integer.MAX_VALUE;
    private int minCostHelper(int[] nums, int left, int right) {
        // base condition
        // unlike knapsack and lcs - not the smallest VALID input
        // negative or invalid check
        if (left >= right) {
            return 0;
        }

        // recursion
        // recursive equation
        // f(left, right) = f(left, mid) + f(mid+1, right) + f(left, mid, right)
        int minCost = Integer.MAX_VALUE;
        for (int k=left; k<right; k++) {
            int tempMinCost = minCostHelper(nums, left, k)
                    + minCostHelper(nums, k+1, right)
                    + nums[left-1] * nums[k] * nums[right];
            minCost = Math.min(minCost, tempMinCost);
        }

        return minCost;
    }
}
