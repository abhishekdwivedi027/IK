package ds.linkedLists;

public class LLTests {

    public static void main(String[] args) {
        LLTests tests = new LLTests();
        System.out.println("is happy ---- " + tests.isNumberHappy(2));
        System.out.println("is happy ---- " + tests.isNumberHappy(19));

        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        ListNode node4 = new ListNode(4);
        ListNode node5 = new ListNode(5);
        ListNode node6 = new ListNode(6);
        ListNode node7 = new ListNode(7);
        node1.setNext(node2);
        node2.setNext(node3);
        node3.setNext(node4);
        node4.setNext(node5);
        node5.setNext(node6);
        node6.setNext(node7);
        System.out.println("reverse ---- " + tests.reverse(node1).print());
        tests.reverse(node7);
        System.out.println("swap ---- " + tests.swapNodes(node1, 2).print());

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
            slow = isNumberHappyHelper(slow);
            fast = isNumberHappyHelper(isNumberHappyHelper(fast));
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

    // both pointers meet somewhere in the cycle
    // start - where they started
    // junction - what we are going to find
    // end - where they met
    // by the time slow reaches end, and covered s distance (start to end), fast has covered 2s distance (start to end and end to end)
    // s = start to end = end to end
    // start to junction + junction to end = junction to end + end to junction
    // start to junction = end to junction
    public ListNode findWhereCycleStarts(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;

        while (fast != null && fast.getNext() != null) {
            slow = slow.getNext();
            fast = fast.getNext().getNext();
            if (fast == slow) {
                break;
            }
        }

        // another slow starts and head and slow will resume from end
        // they will meet at the junction
        ListNode anotherSlow = head;
        while (anotherSlow != slow) {
            anotherSlow = anotherSlow.getNext();
            slow = slow.getNext();
        }

        return anotherSlow;
    }

    // 1 -> 3 -> 5 -> 7 -> null
    // 7 -> 5 -> 3 -> 1 -> null
    private ListNode reversedHead;
    public ListNode reverse(ListNode head) {
        if (head == null) {
            return null;
        }

        reverse(head, null);
        return reversedHead;
    }

    private void reverse(ListNode node, ListNode prev) {
        if (node == null) {
            reversedHead = prev;
            return;
        }

        reverse(node.getNext(), node);

        // we are only reversing the link
        // no local return needed
        node.setNext(prev);
    }

    public ListNode swapNodes(ListNode head, Integer k) {
        ListNode first = head;

        int count = 1;
        while (count++ < k) {
            if (first == null) {
                return null;
            }
            first = first.getNext();
        }

        ListNode second = head;
        ListNode third = first;
        while (first != null && first.getNext() != null) {
            first = first.getNext();
            second = second.getNext();
        }

        int tempVal = second.getVal();
        second.setVal(third.getVal());
        third.setVal(tempVal);

        return head;
    }

    public ListNode removeKthFromEnd(ListNode head, int k) {
        ListNode first = head;
        int count = 1;
        // we will find k+1th node from the end
        // node to be deleted = next node
        while (count++ <  k+1) {
            if (first == null) {
                return null;
            }
            first = first.getNext();
        }

        // special case
        if (first == null) {
            head = head.getNext();
            return head;
        }

        ListNode second = head;
        while (first != null && first.getNext() != null) {
            first = first.getNext();
            second = second.getNext();
        }

        if (second == null || second.getNext() == null) {
            return null;
        }

        ListNode next = second.getNext().getNext();
        second.setNext(next);
        return head;
    }
}
