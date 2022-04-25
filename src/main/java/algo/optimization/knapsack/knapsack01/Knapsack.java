package algo.optimization.knapsack.knapsack01;

import algo.optimization.Item;

import java.util.Arrays;

public class Knapsack {

    private int[][] maxItemsValues;

    // given items and knapsack constraint
    public int knapsack(Item[] items, int maxWeight) {
        int nums = items.length;
        maxItemsValues = new int[nums+1][maxWeight+1];

        // base condition => initialization
        for (int i=0; i<=nums; i++) {
            for (int j=0; j<=maxWeight; j++) {
                if (i == 0 || j == 0) {
                    maxItemsValues[i][j] = 0; // no item or no weight => zero value
                }
            }
        }

        // recurrence equation
        // optimization => optimal substructure
        // f(n, w) = nth_item_weight <= w ? max(pick, leave) : leave
        // where leave = f(n-1, w) and pick = f(n-1, w - nth_item_weight) + nth_item_value

        // maxItemsValues[i][j] = max value when items size is i and weight limit is j

        for (int i=1; i<=nums; i++) {
            // rem: it's always i-1 in original array
            int itemValue = items[i-1].getValue();
            int itemWeight = items[i-1].getWeight();
            for (int j=1; j<=maxWeight; j++) {
                int leave = maxItemsValues[i-1][j];
                if (itemWeight <= j) {
                    int pick = maxItemsValues[i-1][j-itemWeight] + itemValue;
                    maxItemsValues[i][j] = Math.max(leave, pick);
                } else {
                    maxItemsValues[i][j] = leave;
                }
            }
        }

        return maxItemsValues[nums][maxWeight];
    }
}
