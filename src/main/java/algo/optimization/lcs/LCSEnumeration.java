package algo.optimization.lcs;

import org.apache.commons.lang3.StringUtils;

public class LCSEnumeration {

    private int maxLength = 0;
    public int longestCommonSubsequence(String s1, String s2) {
        if (StringUtils.isBlank(s1) || StringUtils.isBlank(s2)) {
            return 0;
        }

        longestCommonSubsequenceHelper("", 0, 0, s1, s2);
        return maxLength;
    }

    private void longestCommonSubsequenceHelper(String s, int i, int j, String s1, String s2) {
        if (i == s1.length() || j == s2.length()) {
            if (s.length() > maxLength) {
                maxLength = s.length();
            }
            return;
        }

        char c = s1.charAt(i);
        char ch = s2.charAt(j);
        if (c == ch) {
            longestCommonSubsequenceHelper(s+c, i+1, j+1, s1, s2);
        } else {
            longestCommonSubsequenceHelper(s, i+1, j, s1, s2);
            longestCommonSubsequenceHelper(s, i, j+1, s1, s2);
        }
    }
}
