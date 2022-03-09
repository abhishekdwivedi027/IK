package common;

public class Helper {

    public static boolean isPalindrome(String s) {
        return isPalindrome(s, 0, s.length()-1);
    }

    public static boolean isPalindrome(String s, int left, int right) {
        boolean isPalindrome = true;
        while (left<right) {
            if (s.charAt(left++) != s.charAt(right--)) {
                isPalindrome = false;
                break;
            }
        }
        return isPalindrome;
    }
}
