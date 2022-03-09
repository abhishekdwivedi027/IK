package algo.optimization.knapsack.knapsack01;

import algo.optimization.Item;

public class KnapsackRecursion {

    public int knapsackRecursion(Item[] items, int maxWeight) {

        return knapsackRecursionHelper(items, items.length, maxWeight);
    }

    private int knapsackRecursionHelper(Item[] items, int index, int weight) {

        // base condition - think of the smallest valid input
        if (index == 0 || weight == 0) {
            return 0;
        }

        // recurrence equation
        // algo.optimization => optimal substructure
        // f(n, w) = nth_item_weight <= w ? max(value with nth item, value without nth item) : value without nth item
        // where leave = f(n-1, w) and pick = f(n-1, w - nth_item_weight) + nth_item_value

        int itemValue = items[index-1].getValue();
        int itemWeight = items[index-1].getWeight();
        int leave = knapsackRecursionHelper(items, index-1, weight);
        if (itemWeight <= weight) {
            int pick = knapsackRecursionHelper(items, index-1, weight-itemWeight) + itemValue;
            return Math.max(leave, pick);
        } else {
            return leave;
        }
    }
}
