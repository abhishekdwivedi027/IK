package algo.optimization;

import algo.optimization.knapsack.knapsack01.*;
import algo.optimization.knapsack.knapsackUnbounded.KnapsackRecursive;
import algo.optimization.knapsack.knapsackUnbounded.KnapsackVariation;
import algo.optimization.lcs.*;
import algo.optimization.lcs.palindrome.LPS;
import algo.optimization.lcs.palindrome.LPSVariant;
import algo.optimization.mcm.MCMMemo;
import algo.optimization.mcm.MCMRecursion;
import algo.optimization.mcm.MCMVariant;
import algo.optimization.path.Grid;
import algo.optimization.path.Stairs;

public class DPTest {
	
	// Enumeration = Recursion with multiple recursion cases - exponential complexity
	// when branching factor == 2 then number(leafs) = 2^h
	// when branching factor > 2 then it becomes useless w/o backtracking
	// partialSolution & subproblemSize
	// top-down approach - lazy manager works first (bottom-up - lazy manager works last => high space complexity)
	// *recursive* algo.enumeration
	
	// DP = Recursion without repetition
	// overlapping subproblems (multiple recursion calls) & optimal substructure (decrease & conquer)
	// *recurrence equation* with 1-2 params (ex: nCk = n-1Ck-1 + n-1Ck)
    // lazy manager works last - to create the recurrence equation
	// from recursion ds.tree to dependency ds.graph - DAG => topo algo.sort
	// bottom-up approach - iteration with tabulation (top-down recursion with memo cache => high space complexity)
	// dimension(table) = dimension(problem) & number of variables
	// *iterative* DP
	// 1. counting (number of ways) - 1D or 2D (nCk) - overlapping subproblems
    // 2. algo.optimization (max/min) - and optimal substructure

    public static void main(String[] args) {

        Stairs stairs = new Stairs();
        System.out.println("ways to climb   " + stairs.waysToClimb(3));

        int[] cost = {1,100,1,1,1,100,1,1,100,1};
        System.out.println("min cost to climb   " + stairs.minCostToClimb(cost));

        Grid grid = new Grid();
        System.out.println("paths   " + grid.pathCount(3, 7));

        int[][] blockedGrid = {{0,0,0},{0,-1,0},{0,0,0}};
        System.out.println("blocked paths   " + grid.blockedPathCount(blockedGrid));

        Item[] items = {
                new Item(2, 7),
                new Item(5, 1),
                new Item(7, 20),
                new Item(5, 2),
        };

        // 01 knapsack

        KnapsackEnumeration knapsackEnumeration = new KnapsackEnumeration();
        System.out.println("max value   " + knapsackEnumeration.knapsackEnumeration(items, 15));

        KnapsackRecursion knapsackRecursion = new KnapsackRecursion();
        System.out.println("max value   " + knapsackRecursion.knapsackRecursion(items, 15));

        KnapsackMemo knapsackMemo = new KnapsackMemo();
        System.out.println("max value   " + knapsackMemo.knapsackMemo(items, 15));

        Knapsack knapsack = new Knapsack();
        System.out.println("max value   " + knapsack.knapsack(items, 15));

        KnapsackVariant knapsackVariant = new KnapsackVariant();
        int[] nums = {1, 2, 3, 5, 7, 11};
        System.out.println("has subset sum   " + knapsackVariant.subsetSum(nums, 15));
        System.out.println("subset sum count   " + knapsackVariant.subsetSumCount(nums, 8));
        System.out.println("print subset sum   " + knapsackVariant.printSubsetSum(nums, 15));
        System.out.println("equal subset sum   " + knapsackVariant.equalSum(nums));
        System.out.println("min subset difference   " + knapsackVariant.minSubsetSumDifference(nums));
        System.out.println("given subset difference count   " + knapsackVariant.subsetSumDifferenceCount(nums, 11));
        System.out.println("target sum count   " + knapsackVariant.targetSumCount(nums, 11));

        // unbounded knapsack

        KnapsackRecursive knapsackRecursive = new KnapsackRecursive();
        System.out.println("max value   " + knapsackRecursive.knapsackRecursive(items, 15));

        int[] coins = {1, 2, 3};
        KnapsackVariation knapsackVariation = new KnapsackVariation();
        System.out.println("coin change count   " + knapsackVariation.coinChangeCount(coins, 5));
        System.out.println("min coin count   " + knapsackVariation.minCoinCount(coins, 5));

        // LCS
        LCSEnumeration lcsEnumeration = new LCSEnumeration();
        System.out.println("longest common subsequence   " + lcsEnumeration.longestCommonSubsequence("abcdgh", "abedfgh"));
        LCSRecursion lcsRecursion = new LCSRecursion();
        System.out.println("longest common subsequence   " + lcsRecursion.longestCommonSubsequence("abcdgh", "abedfgh"));
        LCSMemo lcsMemo = new LCSMemo();
        System.out.println("longest common subsequence   " + lcsMemo.longestCommonSubsequence("abcdgh", "abedfgh"));
        LCS lcs = new LCS();
        System.out.println("longest common subsequence   " + lcs.longestCommonSubsequence("abcdgh", "abedfgh"));
        LCSVariant lcsVariant = new LCSVariant();
        System.out.println("longest common substring   " + lcsVariant.printLongestCommonSubsequence("abcdgh", "abedfgh"));
        System.out.println("longest common substring   " + lcsVariant.longestCommonSubstring("abcdgh", "abedfgh"));
        System.out.println("shortest super sequence   " + lcsVariant.shortestSuperSequence("abcdgh", "abedfgh"));
        System.out.println("shortest super sequence   " + lcsVariant.printShortestSuperSequence("abcdgh", "abedfgh"));
        int[] numChange = lcsVariant.numberOfInsertionDeletionToTransform("abcdgh", "abedfgh");
        System.out.println("#insertions    " + numChange[0]);
        System.out.println("#deletions    " + numChange[1]);
        System.out.println("longest repeating subsequence   " + lcsVariant.longestRepeatingSubsequence("abcabcdgh"));

        LPS lps = new LPS();
        System.out.println("longest palindromic subsequence   " + lps.longestPalindromeSubsequence("axyzbcba"));
        LPSVariant lpsVariant = new LPSVariant();
        System.out.println("min deletions palindrome   " + lpsVariant.minDeletionsPalindrome("axyzbcba"));
        System.out.println("min insertions palindrome   " + lpsVariant.minInsertionPalindrome("axyzbcba"));

        int[] arr = {10, 30, 5, 60};
        MCMRecursion mcmRecursion = new MCMRecursion();
        System.out.println("min cost   " + mcmRecursion.minCost(arr));
        MCMMemo mcmMemo = new MCMMemo();
        System.out.println("min cost   " + mcmMemo.minCost(arr));
        MCMVariant mcmVariant = new MCMVariant();
        System.out.println("min palindrome partitions   " + mcmVariant.minPalindromePartitions("nitini"));
    }

}



