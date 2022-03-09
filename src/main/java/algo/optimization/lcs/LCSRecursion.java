package algo.optimization.lcs;

import org.apache.commons.lang3.StringUtils;

public class LCSRecursion {

    public int longestCommonSubsequence(String s1, String s2) {
        if (StringUtils.isBlank(s1) || StringUtils.isBlank(s2)) {
            return 0;
        }

        return longestCommonSubsequenceHelper(s1, s2, s1.length(), s2.length());
    }

    private int longestCommonSubsequenceHelper(String s1, String s2, int i, int j) {
        // base conditions
        if (i == 0 || j == 0) {
            return 0;
        }

        // recursive conditions
        if (s1.charAt(i-1) == s2.charAt(j-1)){
            // pick
            return longestCommonSubsequenceHelper(s1, s2, i-1, j-1) + 1;
        } else {
            // leave
            int l1 = longestCommonSubsequenceHelper(s1, s2, i-1, j);
            int l2 = longestCommonSubsequenceHelper(s1, s2, i, j-1);
            return Math.max(l1, l2);
        }
    }
}
