package algo.optimization.lcs;

import org.apache.commons.lang3.StringUtils;

public class LCSVariant {

    private int[][] table;

    public String printLongestCommonSubsequence(String s1, String s2) {

        if (StringUtils.isBlank(s1) || StringUtils.isBlank(s2)) {
            return "";
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
                    table[i][j] = 0;
                }
            }
        }

        // print lcs
        int i = n1;
        int j = n2;
        StringBuilder builder = new StringBuilder();
        while (i>0 && j>0) {
            if (s1.charAt(i-1) == s2.charAt(j-1)) {
                builder.append(s1.charAt(i-1));
                i--;
                j--;
            } else {
                if (table[i-1][j] >= table[i][j-1]) {
                    i--;
                } else {
                    j--;
                }
            }
        }

        return builder.reverse().toString();
    }

    public int longestCommonSubstring(String s1, String s2) {
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
        // f(i, j) = c1 == c2 ? f(i-1, j-1) + 1 : 0
        for (int i=1; i<=n1; i++) {
            for (int j=1; j<=n2; j++) {
                if (s1.charAt(i-1) == s2.charAt(j-1)) {
                    table[i][j] = table[i-1][j-1] + 1;
                } else {
                    table[i][j] = 0;
                }
            }
        }

        return table[n1][n2];
    }

    // length(shortestSuperSequence) = s1Length + s2Length - length(longestCommonSubsequence)
    public int shortestSuperSequence(String s1, String s2) {
        if (StringUtils.isBlank(s1)) {
            return StringUtils.isBlank(s2) ? 0 : s2.length();
        }
        if (StringUtils.isBlank(s2)) {
            return StringUtils.isBlank(s2) ? 0 : s2.length();
        }

        LCS lcs = new LCS();
        return s1.length() + s2.length() - lcs.longestCommonSubsequence(s1, s2);
    }

    public String printShortestSuperSequence(String s1, String s2) {
        if (StringUtils.isBlank(s1) || StringUtils.isBlank(s2)) {
            return "";
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
        for (int i=1; i<=n1; i++) {
            for (int j=1; j<=n2; j++) {
                if (s1.charAt(i-1) == s2.charAt(j-1)) {
                    table[i][j] = table[i-1][j-1] + 1;
                } else {
                    table[i][j] = Math.max(table[i-1][j], table[i][j-1]);
                }
            }
        }

        // print scs
        int i = n1;
        int j = n2;
        StringBuilder builder = new StringBuilder();
        while (i>0 && j>0) {
            if (s1.charAt(i-1) == s2.charAt(j-1)) {
                builder.append(s1.charAt(i-1));
                i--;
                j--;
            } else {
                if (table[i-1][j] >= table[i][j-1]) {
                    builder.append(s1.charAt(i-1));
                    i--;
                } else {
                    builder.append(s2.charAt(j-1));
                    j--;
                }
            }
        }

        return builder.reverse().toString();
    }

    // num of insertions and deletions to convert s1 to s2
    // s1 -> lcs -- deletions
    // lcs -> s2 -- insertions
    // add replace/update and it becomes "edit distance" problem
    public int[] numberOfInsertionDeletionToTransform(String s1, String s2) {
        LCS lcs = new LCS();
        int longestCommonSubsequence = lcs.longestCommonSubsequence(s1, s2);
        int deletions = s1.length() - longestCommonSubsequence;
        int insertions = s2.length() - longestCommonSubsequence;
        int[] result = {insertions, deletions};
        return result;
    }

    // TODO explain the algorithm
    public int longestRepeatingSubsequence(String s) {
        if (StringUtils.isBlank(s)) {
            return 0;
        }

        int n = s.length();
        table = new int[n+1][n+1];

        // init
        for (int i=0; i<=n; i++) {
            for (int j=0; j<=n; j++) {
                if (i == 0 || j == 0) {
                    table[i][j] = 0;
                }
            }
        }

        // lcs
        for (int i=1; i<=n; i++) {
            for (int j=1; j<=n; j++) {
                if (i != j && s.charAt(i-1) == s.charAt(j-1)) {
                    table[i][j] = table[i-1][j-1] + 1;
                } else {
                    table[i][j] = Math.max(table[i-1][j], table[i][j-1]);
                }
            }
        }

        return table[n][n];
    }

}
