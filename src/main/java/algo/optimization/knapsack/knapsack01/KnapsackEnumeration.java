package algo.optimization.knapsack.knapsack01;

import algo.optimization.Item;

public class KnapsackEnumeration {

    // Knapsack types
    // 0/1
    // unbounded
    // fractional - greedy

    // other related problems
    // subset sum
    // equal sum partition
    // min subset difference


    // step 1: identify DP problem - a. choices(nCk) - to take or not to take b. algo.optimization
    // step 2: write recurrence equation - p(i) =
    private int maxValue = 0;
    public int knapsackEnumeration(Item[] items, int maxWeight) {
        // base condition - think of the smallest valid input
        if (items.length == 0 || maxWeight == 0) {
            return 0;
        }

        knapsackEnumerationHelper(0, 0, 0, items, maxWeight);
        return maxValue;
    }

    private void knapsackEnumerationHelper(int value, int weight, int index, Item[] items, int maxWeight) {

        if (weight > maxWeight) {
            return;
        }

        if (index == items.length) {
            if (value > maxValue) {
                maxValue = value;
            }
            return;
        }

        // leave
        knapsackEnumerationHelper(value, weight, index+1, items, maxWeight);
        // select
        int itemValue = items[index].getValue();
        int itemWeight = items[index].getWeight();
        knapsackEnumerationHelper(value+itemValue, weight+itemWeight, index+1, items, maxWeight);
    }
}


