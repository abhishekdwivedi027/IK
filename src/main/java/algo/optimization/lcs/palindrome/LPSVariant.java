package algo.optimization.lcs.palindrome;

import org.apache.commons.lang3.StringUtils;

public class LPSVariant {

    public int minDeletionsPalindrome(String s) {
        if (StringUtils.isBlank(s)) {
            return 0;
        }

        LPS lps = new LPS();
        int longestPalindromeSubsequence = lps.longestPalindromeSubsequence(s);
        return s.length() - longestPalindromeSubsequence;
    }

    public int minInsertionPalindrome(String s) {
        if (StringUtils.isBlank(s)) {
            return 0;
        }

        LPS lps = new LPS();
        int longestPalindromeSubsequence = lps.longestPalindromeSubsequence(s);
        return s.length() - longestPalindromeSubsequence;
    }
}
