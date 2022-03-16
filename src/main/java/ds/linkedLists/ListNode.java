package ds.linkedLists;

public class ListNode {

    private int val;
    private ListNode next;

    public ListNode(int val) {
        this(val, null);
    }

    public ListNode(int val, ListNode next) {
        this.val = val;
        this.next = next;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }

    public String print() {
        ListNode head = this;
        StringBuilder builder = new StringBuilder();
        while (head != null) {
            builder.append(head.getVal());
            builder.append(" ");
            head = head.getNext();
        }

        return builder.toString();
    }
}
