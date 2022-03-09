package algo.optimization.knapsack.knapsackUnbounded;

import algo.optimization.Item;

public class KnapsackRecursive {

    public int knapsackRecursive(Item[] items, int maxWeight) {

        return knapsackRecursiveHelper(items, items.length, maxWeight);
    }

    private int knapsackRecursiveHelper(Item[] items, int index, int weight) {

        // base condition - think of the smallest valid input
        if (index == 0 || weight == 0) {
            return 0;
        }

        // recurrence equation
        // algo.optimization => optimal substructure
        // f(n, w) = nth_item_weight <= w ? max(pick, leave) : leave
        // where leave = f(n-1, w) and pick = f(n, w - nth_item_weight) + nth_item_value

        int itemValue = items[index-1].getValue();
        int itemWeight = items[index-1].getWeight();
        int leave = knapsackRecursiveHelper(items, index-1, weight);
        if (itemWeight <= weight) {
            // here is the difference from 01Knapsack
            // PICK case - index is not deducted by one
            int pick = knapsackRecursiveHelper(items, index, weight-itemWeight) + itemValue;
            return Math.max(leave, pick);
        } else {
            return leave;
        }
    }
}
