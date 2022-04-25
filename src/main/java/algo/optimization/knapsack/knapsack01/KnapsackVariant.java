package algo.optimization.knapsack.knapsack01;

public class KnapsackVariant {

    // problem 1: subset sum problem
    // can we find a subset to get this sum?
    // this is subset, not subarray (where range sum or sliding window could be used)
    private boolean[][] subsetSums;
    public boolean subsetSum(int[] nums, int sum) {
        int n = nums.length;
        subsetSums = new boolean[n+1][sum+1];

        // initialization
        for (int i=0; i<=n; i++) {
            for (int j=0; j<=sum; j++) {
                if (i==0) {
                    subsetSums[i][j] = false; // no input => no num to choose
                }
                if (j==0) {
                    subsetSums[i][j] = true; // zero sum => empty subset; subsetSums[0][0] = true
                }
            }
        }

        // recurrence equation
        // f(n, sum) = num <= sum ? leave || pick : leave
        // where leave = f(n-1, sum) and pick = f(n-1, sum - num)

        for (int i=1; i<=n; i++) {
            for (int j=1; j<=sum; j++) {
                int num = nums[i-1];
                boolean leave = subsetSums[i-1][j]; // top
                if (num <= j) {
                    boolean pick = subsetSums[i-1][j-num]; // + num == j - redundant
                    subsetSums[i][j] =  leave || pick;
                } else {
                    subsetSums[i][j] =  leave;
                }
            }
        }

        return subsetSums[n][sum];
    }

    // problem 2: subset sum count problem
    // how many subsets can add to this given sum
    private int[][] counts;
    public int subsetSumCount(int[] nums, int sum) {
        int n = nums.length;
        counts = new int[n+1][sum+1];

        // initialization
        for (int i=0; i<=n; i++) {
            for (int j=0; j<=sum; j++) {
                if (i == 0) {
                    counts[i][j] = 0;
                }
                if (j == 0) {
                    counts[i][j] = 1; // empty subset
                }
            }
        }

        // recurrence equation
        // f(n, sum) = num <= sum ? leave + pick : leave

        for (int i=1; i<=n; i++) {
            for (int j=1; j<=sum; j++) {
                int num = nums[i-1];
                int leave = counts[i-1][j];
                if (num <= j) {
                    int pick = counts[i-1][j-num]; // + 1 - implicit
                    counts[i][j] = pick + leave;
                } else {
                    counts[i][j] = leave;
                }
            }
        }

        return counts[n][sum];
    }

    // problem 3: print subset sum problem
    // what subset adds up to a given sum
    public String printSubsetSum(int[] nums, int sum) {
        int n = nums.length;
        subsetSums = new boolean[n+1][sum+1];

        // initialization - base case
        for (int i=0; i<=n; i++) {
            for (int j=0; j<=sum; j++) {
                if (i == 0) { // no number
                    subsetSums[i][j] = false;
                }
                if (j == 0) { // zero sum - empty subset
                    subsetSums[i][j] = true;
                }
            }
        }

        // recursive equation
        // f(n, sum) = f(n-1, sum-num) || f(n-1, sum)
        for (int i=1; i<=n; i++) {
            for (int j=1; j<=sum; j++) {
                int num = nums[i-1];
                boolean leave = subsetSums[i-1][j];
                if (num <= j) {
                    boolean pick = subsetSums[i-1][j-num];
                    subsetSums[i][j] = pick || leave;
                } else {
                    subsetSums[i][j] = leave;
                }
            }
        }

        if (!subsetSums[n][sum]) {
            return "";
        }

        int i = n;
        int j = sum;
        StringBuilder builder = new StringBuilder();
        while (i>0 && j>0) {
            // we will traverse from T to T
            // either it was picked or left
            boolean leave = subsetSums[i-1][j];
            int num = nums[i-1];
            if (leave) {
                // T came from leave (top)
                i--;
            } else {
                // T came from pick (other place) - pick this number
                builder.append(num);
                builder.append(" ");
                j = j - num;
                i--;
            }
        }

        return builder.reverse().toString().trim();
    }

    // problem 4: equal subset sum problem
    public boolean equalSum(int[] nums) {
        int sum = 0;
        for (int i=0; i<nums.length; i++) {
            sum += nums[i];
        }

        return sum % 2 == 0 && subsetSum(nums, sum/2);
    }

    // problem 5: min subset sum difference problem
    // min(s2 - s1) where s1 <= s2
    // min(sum - 2*s1) where s1 <= range (sum/2)
    // sum - 2 * max(s1)
    private boolean[][] doesSumExist;
    public int minSubsetSumDifference(int[] nums) {
        int n = nums.length;;
        int sum = 0;
        for (int i=0; i<n; i++) {
            sum += nums[i];
        }

        int range = sum/2;
        doesSumExist = new boolean[n+1][range+1];

        // initialize
        for (int i=0; i<=n; i++) {
            for (int j=0; j<=range; j++) {
                if (i == 0) {
                    doesSumExist[i][j] = false;
                }
                if (j == 0) {
                    doesSumExist[i][j] = true;
                }
            }
        }

        // recurrence equation
        // f(n, sum) = num <= sum ? pick || leave : leave;

        for (int i=1; i<=n; i++) {
            for (int j=1; j<=range; j++) {
                int num = nums[i-1];
                boolean leave = subsetSums[i-1][j];
                if (num <= j) {
                    boolean pick = subsetSums[i-1][j-num];
                    subsetSums[i][j] = pick || leave;
                } else {
                    subsetSums[i][j] = leave;
                }
            }
        }

        int min = Integer.MAX_VALUE;
        for (int j=0; j<=range; j++) {
            if (subsetSums[n][j]) {
                min = Math.min(min, sum - 2 * j);
            }
        }

        return min;
    }

    // problem 6: subset sum difference count problem
    // s2 - s1 = diff
    // sum - 2*s1 = diff
    // s1 = (sum - diff)/2
    public int subsetSumDifferenceCount(int[] nums, int diff) {
        int n = nums.length;
        int sum = 0;
        for (int i=0; i<n; i++) {
            sum += nums[i];
        }

        int sumDiff = sum - diff;
        return sumDiff % 2 == 0 ? subsetSumCount(nums, sumDiff/2) : 0;
    }

    // problem 7: target sum count problem
    // put a sign (+ or -) in from of each num in nums to get a target sum
    // because of minus sign, this is not a sum but a difference problem
    // s1 + (-s2) = targetSum
    public int targetSumCount(int nums[], int targetSum) {
        return subsetSumDifferenceCount(nums, targetSum);
    }

}
