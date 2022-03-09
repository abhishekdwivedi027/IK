package algo.optimization.lcs.palindrome;

import algo.optimization.lcs.LCS;
import org.apache.commons.lang3.StringUtils;

public class LPS {

    // LPS(s) = LCS(s, reverse(s))
    public int longestPalindromeSubsequence(String s) {
        if (StringUtils.isBlank(s)) {
            return 0;
        }

        LCS lcs = new LCS();
        return lcs.longestCommonSubsequence(s, StringUtils.reverse(s));
    }
}
