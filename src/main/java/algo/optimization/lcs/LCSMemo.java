package algo.optimization.lcs;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public class LCSMemo {

    private int[][] memo;
    public int longestCommonSubsequence(String s1, String s2) {
        if (StringUtils.isBlank(s1) || StringUtils.isBlank(s2)) {
            return 0;
        }
        int n1 = s1.length();
        int n2 = s2.length();
        memo = new int[n1+1][n2+1];

        // base condition => initialization
        for (int i=0; i<=n1; i++) {
            Arrays.fill(memo[i], -1);
            memo[i][0] = 0;
        }
        for (int j=0; j<=n2; j++) {
            memo[0][j] = 0;
        }

        return longestCommonSubsequenceHelper(s1, s2, n1, n2);
    }

    private int longestCommonSubsequenceHelper(String s1, String s2, int i, int j) {
        // memo check
        if (memo[i][j] != -1) {
            return memo[i][j];
        }

        // recursive cases
        if (s1.charAt(i-1) == s2.charAt(j-1)){
            memo[i][j] = longestCommonSubsequenceHelper(s1, s2, i-1, j-1) + 1;
        } else {
            int l1 = longestCommonSubsequenceHelper(s1, s2, i-1, j);
            int l2 = longestCommonSubsequenceHelper(s1, s2, i, j-1);
            memo[i][j] = Math.max(l1, l2);
        }

        return memo[i][j];
    }
}
