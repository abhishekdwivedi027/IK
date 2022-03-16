package ds.strings;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Strings {

    public static void main(String[] args) {
        Strings tests = new Strings();
        String parentheses = ")()((()))()((()))";
        System.out.println("is valid    " + tests.isValidParentheses(parentheses));
        System.out.println("longest balanced subsequence    " + tests.longestBalancedSubsequence(parentheses));
    }

    public boolean isValidParentheses(String parentheses) {
        Set<Character> closed = new HashSet<>();
        closed.add(')');
        closed.add('}');
        closed.add(']');

        int open = 0;
        int close = 0;

        for (int i=0; i<parentheses.length(); i++) {
            char c = parentheses.charAt(i);
            if (closed.contains(c)) {
                close++;
            } else {
                open++;
            }

            if (close > open) {
                return false;
            }
        }

        return open == close;
    }

    public int longestBalancedSubsequence(String parentheses) {
        int maxLength = 0;
        int length = 0;
        int open = 0;
        int close = 0;

        for (int i=0; i<parentheses.length(); i++) {
            char c = parentheses.charAt(i);
            if (c == '(') {
                open++;
            } else {
                close++;
            }

            if (close == open) {
                length = open + close;
                maxLength = Math.max(length, maxLength);
            }

            if (close > open) {
                open = 0;
                close = 0;
            }
        }

        return maxLength;
    }

}
