package ds.arrays;

import java.util.Arrays;

public class Stock {

    // for lazy manager, ith day is the last day - only selling is possible
    // globalProfits[i] = max(profit w/o sale on ith day, profit with sale on ith day) - optional sale
    // profit w/o sale on ith day = globalProfits[i-1]
    // profit with sale on ith day = localProfits[i] - mandatory sale on day i
    // localProfits[i] = max(profit when bought on i-1st day, profit when bought earlier)
    // total profit with sale on ith day when bought on i-1st day = globalProfits[last_sale_day] + prices[i] - prices[i-1] - fee
    // total profit with sale on ith day when bought earlier =  localProfits[i-1] + prices[i] - prices[i-1]; fee cancels out
    // localProfits[i] = prices[i] - prices[i-1] + max(globalProfits[last_sale_day], localProfits[i-1])
    // globalProfits[i] = max(globalProfits[i-1], localProfits[i])

    public static void main(String[] args) {
        Stock stock = new Stock();
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println("max profit    " + stock.maxProfit(prices));
        System.out.println("max total profit    " + stock.maxTotalProfit(prices));
        prices = new int[]{1, 2, 3, 0, 2};
        System.out.println("max total profit with cooldown    " + stock.maxTotalProfitWithCooldown(prices));
        prices = new int[]{1, 3, 2, 8, 4, 9};
        System.out.println("max total profit with fee    " + stock.maxTotalProfitWithFee(prices, 2));
    }

    // MAX PROFIT - MAX ONE TRANSACTION
    // globalProfits[last_sale_day] == 0
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int n = prices.length;
        int[] localProfits = new int[n];
        localProfits[0] = 0; // cannot sell on day 1
        int[] globalProfits = new int[n];
        globalProfits[0] = 0;

        for (int i=1; i<n; i++) {
            localProfits[i] = prices[i] - prices[i-1] + Math.max(0, localProfits[i-1]);
            globalProfits[i] = Math.max(globalProfits[i-1], localProfits[i]);
        }

        return globalProfits[n-1];
    }

    // MAX PROFIT - ANY NUMBER OF TRANSACTIONS
    // globalProfits[last_sale_day] == globalProfits[i-1]
    public int maxTotalProfit(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int n = prices.length;
        int[] localProfits = new int[n];
        localProfits[0] = 0; // cannot sell on day 1
        int[] globalProfits = new int[n];
        globalProfits[0] = 0;

        for (int i=1; i<n; i++) {
            localProfits[i] = prices[i] - prices[i-1] + Math.max(globalProfits[i-1], localProfits[i-1]);
            globalProfits[i] = Math.max(globalProfits[i-1], localProfits[i]);
        }

        return globalProfits[n-1];
    }

    // MAX PROFIT - ANY NUMBER OF TRANSACTIONS
    // globalProfits[last_sale_day] == globalProfits[i-3]
    // last_sale_day when buy was on i-1 = i-3 because of one day cooldown
    public int maxTotalProfitWithCooldown(int[] prices) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int n = prices.length;
        int[] localProfits = new int[n];
        localProfits[0] = 0; // cannot sell on day 1
        int[] globalProfits = new int[n];
        globalProfits[0] = 0;

        for (int i=1; i<n; i++) {
            localProfits[i] = prices[i] - prices[i-1] + Math.max(i-3 < 0 ? 0 : globalProfits[i-3], localProfits[i-1]);
            globalProfits[i] = Math.max(globalProfits[i-1], localProfits[i]);
        }

        return globalProfits[n-1];
    }

    // MAX PROFIT - ANY NUMBER OF TRANSACTIONS
    // profit with sale on ith day when bought on i-1st day = prices[i] - prices[i-1] - fee + globalProfits[last_sale_day]
    // profit with sale on ith day when bought earlier = prices[i] - prices[i-1] - fee + localProfits[i-1] + fee
    public int maxTotalProfitWithFee(int[] prices, int fee) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int n = prices.length;
        int[] localProfits = new int[n];
        localProfits[0] = -fee; // same day selling is allowed
        int[] globalProfits = new int[n];
        globalProfits[0] = 0;

        for (int i=1; i<n; i++) {
            localProfits[i] = prices[i] - prices[i-1] + Math.max(globalProfits[i-1] - fee, localProfits[i-1]);
            globalProfits[i] = Math.max(globalProfits[i-1], localProfits[i]);
        }

        return globalProfits[n-1];
    }


    // ######################HARD#########################

    // MAX PROFIT - MAX TWO TRANSACTIONS
    // init all with arr[0] = 0
    // globalMax2[i] = max(globalMax2[i-1], localMax2[i])
    // localMax2[i] = prices[i] - prices[i-1] + max(globalMax1[i-1], localMax2[i-1])
    // globalMax1[i] = max(globalMax1[i-1], localMax1[i])
    // localMax1[i] = prices[i] - prices[i-1] + max(0, localMax1[i-1])

    // MAX PROFIT - MAX k TRANSACTIONS
    // globalMax[i][j] = max(globalMax[i-1][j], localMax[i][j])
    // localMax[i][j] = prices[i] - prices[i-1] + max(globalMax[i-1][j-1], localMax[i-1][j])
    public int maxProfit(int[] prices, int k) {
        if (prices == null || prices.length < 2) {
            return 0;
        }

        int n = prices.length;
        int[][] localProfits = new int[n][k+1];
        int[][] globalProfits = new int[n][k+1];
        for (int i=0; i<n; i++) {
            for (int j=0; j<=k; j++) {
                if (i==0 || j==0) {
                    localProfits[i][j] = 0;
                    globalProfits[i][j] = 0;
                }
            }
        }

        for (int i=1; i<n; i++) {
            for (int j=1; j<=k; j++) {
                localProfits[i][j] = prices[i] - prices[i-1] + Math.max(globalProfits[i-1][j-1], localProfits[i-1][j]);
                globalProfits[i][j] = Math.max(globalProfits[i-1][j], localProfits[i][j]);
            }
        }

        return globalProfits[n-1][k];
    }
}
