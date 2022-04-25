package algo.optimization.knapsack.knapsackUnbounded;

public class KnapsackVariation {

    /**
     * how is this different from knapsack01?
     * 01
     *         // f(n, w) = nth_item_weight <= w ? max(pick, leave) : leave
     *         // where leave = f(n-1, w) and pick = f(n-1, w - nth_item_weight) + nth_item_value
     * unbounded
     *         // f(n, w) = nth_item_weight <= w ? max(pick, leave) : leave
     *         // where leave = f(n-1, w) and pick = f(n**, w - nth_item_weight) + nth_item_value
     *         pick case is different, it's table[i][j-weight] unlike table[i-1][j-weight] in 01
     */

    // 1. Rod Cutting problem
    // weight => length
    private int[][] maxPrices;
    public int maxPrice(Rod[] rods, int length) {
        int n = rods.length;
        maxPrices = new int[n+1][length+1];

        // init
        for (int i=0; i<=n; i++) {
            for (int j=0; j<=length; j++) {
                if (i == 0) {
                    // TODO why not zero?
                    maxPrices[i][j] = Integer.MIN_VALUE; // no rod
                }
                if (j == 0) {
                    maxPrices[i][j] = 0; // zero length
                }
            }
        }

        // recurrence equation
        // f(n, length) = rodLength <= length ? max(leave, pick) : leave
        for (int i=1; i<=n; i++) {
            for (int j=1; j<=length; j++) {
                int rodLength = rods[i-1].getLength();
                int leave = maxPrices[i-1][j];
                if (rodLength <= j) {
                    int rodPrice = rods[i-1].getPrice();
                    int pick = maxPrices[i][j - rodLength] + rodPrice;
                    maxPrices[i][j] = Math.max(leave, pick);
                } else {
                    maxPrices[i][j] = leave;
                }
            }
        }

        return maxPrices[n][length];
    }

    // 2. Coin Change problem
    // (max) number of ways to count a sum with given set of coins
    private int[][] coinCount;
    public int coinChangeCount(int[] coins, int sum) {
        int n = coins.length;
        coinCount = new int[n+1][sum+1];

        // initialization
        for (int i=0; i<=n; i++) {
            for (int j=0; j<=sum; j++) {
                if (i == 0) {
                    coinCount[i][j] = 0;
                }
                if (j == 0) {
                    coinCount[i][j] = 1; // empty subset - one valid way for zero-sum
                }
            }
        }

        // recurrence equation
        for (int i=1; i<=n; i++) {
            int coin = coins[i-1];
            for (int j=1; j<=sum; j++) {
                int leaveCount = coinCount[i-1][j];
                if (coin <= j) {
                    int pickCount = coinCount[i][j-coin];
                    coinCount[i][j] = leaveCount + pickCount;
                } else {
                    coinCount[i][j] = leaveCount;
                }
            }
        }

        return coinCount[n][sum];
    }

    // 2. Min Coin Count problem
    // find a sum with given set of coins with min numbers of coins
    private int[][] minCoinCount;
    public int minCoinCount(int[] coins, int sum) {
        int n = coins.length;
        minCoinCount = new int[n+1][sum+1];

        // initialization
        for (int i=0; i<=n; i++) {
            for (int j=0; j<=sum; j++) {
                if (i == 0) {
                    // TODO why not zero?
                    minCoinCount[i][j] = Integer.MAX_VALUE; // no coin
                }
                if (j == 0) {
                    minCoinCount[i][j] = 0; // zero sum
                }
            }
        }

        // recurrence equation
        for (int i=1; i<=n; i++) {
            int coin = coins[i-1];
            for (int j=1; j<=sum; j++) {
                int minLeaveCount = minCoinCount[i-1][j];
                if (coin <= j) {
                    int minPickCount = minCoinCount[i][j-coin] + 1;
                    minCoinCount[i][j] = Math.min(minLeaveCount, minPickCount);
                } else {
                    minCoinCount[i][j] = minLeaveCount;
                }
            }
        }

        return minCoinCount[n][sum];
    }
}
