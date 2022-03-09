package algo.optimization.dp;

public class Stock {

    public static void main(String[] args) {
        Stock stock = new Stock();
        int[] prices = {7, 1, 5, 3, 6, 4};
        System.out.println("max profit    " + stock.maxProfit(prices));
        System.out.println("max total profit    " + stock.maxTotalProfit(prices));
        prices = new int[]{1, 2, 3, 0, 2};
        System.out.println("max total profit with cooldown    " + stock.maxTotalProfitWithCooldown(prices));
        prices = new int[]{1, 3, 2, 8, 4, 9};
        System.out.println("max total profit with fee    " + stock.maxTotalProfitWithFee(prices, 2));

        // TODO max area problem
    }

    // MAX PROFIT - MAX ONE TRANSACTION
    // for lazy manager, ith day is the last day - only selling is possible
    // globalProfit(i) = max(local profit when no selling on ith day, local profit when selling on ith day) - I
    // local profit when no selling on ith day - globalProfit(i-1)
    // local profit when selling on ith day = max(profit when buying on prev day, profit when buying on other day)
    // local profit when buying on prev day --> prices[i] - prices[i-1]
    // local profit when buying on other day --> local profit when selling on i-1th day - prices[i-1] + prices[i]
    // local profit when selling on ith day - prices[i] - prices[i-1] + max(0, profit when selling on i-1th day) - II
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
    // for lazy manager, ith day is the last day - only selling is possible
    // globalProfit(i) = max(local profit when no selling on ith day, local profit when selling on ith day) - I
    // local profit when no selling on ith day - globalProfit(i-1)
    // local profit when selling on ith day = max(profit when buying on prev day, profit when buying on other day)
    // local profit when buying on prev day --> prices[i] - prices[i-1] + globalProfit(i-1)
    // local profit when buying on other day --> local profit when selling on i-1th day - prices[i-1] + prices[i]
    // local profit when selling on ith day - prices[i] - prices[i-1] + max(0, profit when selling on i-1th day) - II
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
    // for lazy manager, ith day is the last day - only selling is possible
    // globalProfit(i) = max(local profit when no selling on ith day, local profit when selling on ith day) - I
    // local profit when no selling on ith day - globalProfit(i-1)
    // local profit when selling on ith day = max(profit when buying on prev day, profit when buying on other day)
    // local profit when buying on prev day --> prices[i] - prices[i-1] + globalProfit(i-3)
    // local profit when buying on other day --> local profit when selling on i-1th day - prices[i-1] + prices[i]
    // local profit when selling on ith day - prices[i] - prices[i-1] + max(0, profit when selling on i-1th day) - II
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
    // for lazy manager, ith day is the last day - only selling is possible
    // globalProfit(i) = max(local profit when no selling on ith day, local profit when selling on ith day) - I
    // local profit when no selling on ith day - globalProfit(i-1)
    // local profit when selling on ith day = max(profit when buying on prev day, profit when buying on other day)
    // local profit when buying on prev day --> prices[i] - prices[i-1] - fee + globalProfit(i-1)
    // local profit when buying on other day --> local profit when selling on i-1th day - prices[i-1] + prices[i]
    // local profit when selling on ith day - prices[i] - prices[i-1] + max(0, profit when selling on i-1th day) - II
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
