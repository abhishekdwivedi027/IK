package algo.optimization.path;

public class Stairs {

    public int waysToClimb(int n) {
        // recurrence equation
        // ways(n) = ways(n-1) + ways(n-2)
        int[] ways = new int[n+1];
        ways[0] = 0;
        ways[1] = 1;
        ways[2] = 2;
        for (int i=3; i<=n; i++) {
            ways[i] = ways[i-1] + ways[i-2];
        }
        return ways[n];
    }

    public int minCostToClimb(int[] cost) {
        // recurrence equation
        // minCost(n) = min(minCost(n-1), minCost(n-2)) + costs[n]
        int n = cost.length;
        int[] costs = new int[n+2];
        costs[0] = 0;
        for (int i=0; i<n; i++) {
            costs[i+1] = cost[i];
        }
        costs[n+1] = 0;

        int[] minCost = new int[n+2];
        minCost[0] = 0;
        minCost[1] = costs[1];
        minCost[2] = Math.min(minCost[0], minCost[1]) + costs[2];
        for (int i=3; i<n+2; i++) {
            minCost[i] = Math.min(minCost[i-2], minCost[i-1]) + costs[i];
        }
        return minCost[n+1];
    }
}
