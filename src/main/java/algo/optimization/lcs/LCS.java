package algo.optimization.lcs;

import org.apache.commons.lang3.StringUtils;

public class LCS {

    private int[][] table;
    public int longestCommonSubsequence(String s1, String s2) {
        if (StringUtils.isBlank(s1) || StringUtils.isBlank(s2)) {
            return 0;
        }
        int n1 = s1.length();
        int n2 = s2.length();
        table = new int[n1+1][n2+1];

        // initialization
        for (int i=0; i<=n1; i++) {
            for (int j=0; j<=n2; j++) {
                if (i == 0 || j == 0) {
                    table[i][j] = 0;
                }
            }
        }

        // recurrence equation
        // f(i, j) = c1 == c2 ? f(i-1, j-1) + 1 : max(f(i, j-1), f(i-1, j))
        for (int i=1; i<=n1; i++) {
            for (int j=1; j<=n2; j++) {
                if (s1.charAt(i-1) == s2.charAt(j-1)) {
                    table[i][j] = table[i-1][j-1] + 1;
                } else {
                    table[i][j] = Math.max(table[i][j-1], table[i-1][j]);
                }
            }
        }

        return table[n1][n2];
    }
}
