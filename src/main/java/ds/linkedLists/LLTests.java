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
        ListNode node8 = new ListNode(8);
        ListNode node9 = new ListNode(9);
        node1.setNext(node2);
        node2.setNext(node3);
        node3.setNext(node4);
        node4.setNext(node5);
        node5.setNext(node6);
        node6.setNext(node7);
        node7.setNext(node8);
        node8.setNext(node9);
        System.out.println("reverse ---- " + tests.reverseIteratively(node1).print());
        tests.reverseRecursively(node9);
        System.out.println("reverse blocks ---- " + tests.reverseGroups(node1, 3).print());
        // System.out.println("swap ---- " + tests.swapNodes(node1, 2).print());

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

    public ListNode reverseIteratively(ListNode head) {

        ListNode prev = null;
        ListNode current = head;
        while (current != null) {
            ListNode next = current.getNext();
            current.setNext(prev);
            prev = current;
            current = next;
        }

        return prev;
    }

    // 1 -> 3 -> 5 -> 7 -> null
    // 7 -> 5 -> 3 -> 1 -> null
    public ListNode reverseRecursively(ListNode head) {
        if (head == null) {
            return null;
        }

        return reverseRecursively(head, null);
    }

    private ListNode reverseRecursively(ListNode node, ListNode prev) {
        if (node == null) {
            return prev;
        }

        ListNode head = reverseRecursively(node.getNext(), node);

        node.setNext(prev);

        return head;
    }

    public ListNode reverseGroups(ListNode head, int k) {

        if (head == null) {
            return null;
        }

        if (k == 1) {
            return head;
        }

        // will be changed at max once
        // will not change if k > size(list)
        ListNode reversedHead = head;

        // we need last node of previous block to link to first node of reversed block
        ListNode prevTail = null;

        while (head != null) {
            // this currHead will be used to reverse the block
            // and will be used as prevTail for the next block
            ListNode currHead = head;

            ListNode curr = head;
            // note: k-1
            for (int i=0; i<k-1; i++) {
                if (curr == null) {
                    break;
                }

                // this will reach end of block
                curr = curr.getNext();
            }

            // whether it's partial block or last block
            if (curr == null) {
                if (prevTail != null) {
                    reverseJoin(prevTail, currHead);
                }
                break;
            }

            // one time change
            if (prevTail == null) {
                reversedHead = curr;
            }

            // reverse left block
            head = curr.getNext();
            curr.setNext(null);
            reverseJoin(prevTail, currHead);
            prevTail = currHead;
        }

        return reversedHead;
    }

    private void reverseJoin(ListNode prev, ListNode head) {

        ListNode newHead = reverseRecursively(head);

        if (prev != null) {
            prev.setNext(newHead);
        }
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
