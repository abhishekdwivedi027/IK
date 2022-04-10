package ds.arrays;

public class Stock {

    // for lazy manager, ith day is the last day - only selling is possible
    // globalProfits[i] = max(profit w/o sale on ith day, profit with sale on ith day) - optional sale
    // profit w/o sale on ith day = globalProfits[i-1]
    // profit with sale on ith day = localProfits[i] - mandatory sale on day i
    // localProfits[i] = max(profit when bought on i-1st day, profit when bought earlier)
    // profit with sale on ith day when bought on i-1st day = prices[i] - prices[i-1] + globalProfits[last_sale_day]
    // profit with sale on ith day when bought earlier = prices[i] - prices[i-1] + localProfits[i-1]
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
}
