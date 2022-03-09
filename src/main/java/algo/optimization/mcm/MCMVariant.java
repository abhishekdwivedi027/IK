package algo.optimization.mcm;

import org.apache.commons.lang3.StringUtils;

import static common.Helper.isPalindrome;

public class MCMVariant {

    // step 1: decide left i and right j
    // step 2: base condition - negative/invalid input
    // step 3: k loop
    // step 4: temp solution

    private int[][] memo;

    // 1. min palindrome partition problem
    public int minPalindromePartitions(String s) {
        if (StringUtils.isBlank(s)) {
            return 0;
        }
        int n = s.length();
        memo = new int[n+1][n+1];
        for (int i=0; i<=n; i++) {
            for (int j=0; j<=n; j++) {
                memo[i][j] = -1;
            }
        }

        return minPalindromePartitionsHelper(s, 0, n-1);
    }

    private int minPalindromePartitionsHelper(String s, int left, int right) {
        if (memo[left][right] != -1) {
            return memo[left][right];
        }

        if (left >= right) {
            return 0;
        }

        if (isPalindrome(s, left, right)) {
            return 0;
        }

        int minPalindromePartitions = Integer.MAX_VALUE;
        for (int k=left; k<right; k++) {
            int palindromePartitions = minPalindromePartitionsHelper(s, left, k)
                    + minPalindromePartitionsHelper(s, k+1, right) + 1;
            minPalindromePartitions = Math.min(minPalindromePartitions, palindromePartitions);
        }

        memo[left][right] = minPalindromePartitions;
        return minPalindromePartitions;
    }

    // 2. number of "true" expressions problem
    public int booleanExpressionCount(String s) {
        return booleanExpressionCountHelper(s, 0, s.length()-1);
    }

    private int booleanExpressionCountHelper(String s, int left, int right) {
        if (left>=right) {
            return 0;
        }

        int minCount = Integer.MAX_VALUE;
        for (int k=left; k<right; k=k+2) {

        }

        return 0;
    }
}
