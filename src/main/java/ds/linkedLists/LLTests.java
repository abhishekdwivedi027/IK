package ds.linkedLists;

public class LLTests {

    public static void main(String[] args) {
        LLTests tests = new LLTests();
        System.out.println("is happy ---- " + tests.isNumberHappy(2));
        System.out.println("is happy ---- " + tests.isNumberHappy(19));
    }

    public int findMiddleNodeValue(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
        }

        return slow.getVal();
    }

    // can keep visited nodes in hashtable
    // empty list - no cycle
    public boolean hasCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        boolean hasCycle = false;
        while (fast != null && fast.getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
            if (fast == slow) {
                hasCycle = true;
                break;
            }
        }

        return hasCycle;
    }

    // happy number - sum of squares of all digits = 1
    public boolean isNumberHappy(int n) {
        int fast = n;
        int slow = n;
        while (true) {
            slow = isNumberHappyHelper(n);
            fast = isNumberHappyHelper(isNumberHappyHelper(n));
            if (fast == 1) {
                return true;
            }
            if (fast == slow) {
                return false;
            }
        }
    }

    private int isNumberHappyHelper(int n) {
        int sum = 0;
        while (n != 0) {
            int digit = n%10;
            int num = digit * digit;
            sum += num;
            n = n/10;
        }
        return sum;
    }
}
