package ds.heaps;

import java.util.Comparator;
import java.util.PriorityQueue;

public class Heaps {

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1, new ListNode(3, new ListNode(5)));
        ListNode node2 = new ListNode(1, new ListNode(2, new ListNode(4)));
        ListNode node3 = new ListNode(2, new ListNode(3, new ListNode(6)));
        ListNode[] listNodes = {node1, node2, node3};

        Heaps heaps = new Heaps();
        heaps.mergeKLists(listNodes);

    }

    public ListNode mergeKLists(ListNode[] lists) {
        if (lists == null || lists.length == 0) {
            return null;
        }

        PriorityQueue<ListNode> minHeap = new PriorityQueue<>(Comparator
                .comparing(listNode -> listNode.val));

        // init minHeap
        for (int i=0; i<lists.length; i++) {
            ListNode listNode = lists[i];
            if (listNode != null) {
                minHeap.offer(lists[i]);
            }
        }

        // construct response
        ListNode mergedHead = null;
        ListNode current = null;
        while (!minHeap.isEmpty()) {
            // extract min
            ListNode minListNode = minHeap.poll();
            ListNode listNode = new ListNode(minListNode.val);
            if (mergedHead == null) {
                mergedHead = listNode;
                current = mergedHead;
            } else {
                current.next = listNode;
                current = listNode;
            }

            // add another in the minHeap
            ListNode nextListNode = minListNode.next;
            if (nextListNode != null) {
                minHeap.offer(nextListNode);
            }
        }

        return mergedHead;
    }

    static class ListNode {
        int val;
        ListNode next;

        ListNode(int val) {
            this.val = val;
            this.next = null;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }
}
