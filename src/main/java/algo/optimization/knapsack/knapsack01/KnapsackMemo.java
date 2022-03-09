package algo.optimization.knapsack.knapsack01;

import algo.optimization.Item;

import java.util.Arrays;

public class KnapsackMemo {

    private int[][] maxItemsValues;

    public int knapsackMemo(Item[] items, int maxWeight) {

        int nums = items.length;
        maxItemsValues = new int[nums+1][maxWeight+1];

        // base condition => initialization
        for (int i=0; i<=nums; i++) {
            Arrays.fill(maxItemsValues[i], -1);
            maxItemsValues[i][0] = 0;
        }
        for (int j=0; j<=maxWeight; j++) {
            maxItemsValues[0][j] = 0;
        }
        return knapsackMemoHelper(items, nums, maxWeight);
    }

    private int knapsackMemoHelper(Item[] items, int index, int weight) {

        if (maxItemsValues[index][weight] != -1) {
            return maxItemsValues[index][weight];
        }

        // recurrence equation
        // algo.optimization => optimal substructure
        // f(n, w) = nth_item_weight <= w ? max(value with nth item, value without nth item) : value without nth item
        // where leave = f(n-1, w) and pick = f(n-1, w - nth_item_weight) + nth_item_value

        int itemValue = items[index-1].getValue();
        int itemWeight = items[index-1].getWeight();
        int leave = knapsackMemoHelper(items, index-1, weight);
        if (itemWeight <= weight) {
            int pick = knapsackMemoHelper(items, index-1, weight-itemWeight) + itemValue;
            maxItemsValues[index][weight] = Math.max(leave, pick);
        } else {
            maxItemsValues[index][weight] = leave;
        }

        return maxItemsValues[index][weight];
    }
}
