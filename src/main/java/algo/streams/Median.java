package algo.streams;

import java.util.Collections;
import java.util.PriorityQueue;

public class Median {

    private PriorityQueue<Integer> minHeapOfMaxHalf = new PriorityQueue<>();
    private PriorityQueue<Integer> maxHeapOfMinHalf = new PriorityQueue<>(Collections.reverseOrder());

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Median median = new Median();
        for (int num: nums) {
            median.addNumber(num);
            System.out.println("median     " + median.getMedian());
        }

    }

    public float getMedian() {
        int maxOfMinHalf = maxHeapOfMinHalf.isEmpty() ? 0 : maxHeapOfMinHalf.peek();
        int minOfMaxHalf = minHeapOfMaxHalf.isEmpty() ? 0 : minHeapOfMaxHalf.peek();
        if (maxHeapOfMinHalf.size() == minHeapOfMaxHalf.size() + 1) {
            return maxOfMinHalf;
        } else if (minHeapOfMaxHalf.size() == maxHeapOfMinHalf.size() + 1) {
            return minOfMaxHalf;
        } else {
            return ((float)(maxOfMinHalf + minOfMaxHalf)/2);
        }
    }

    private void addNumber(int in) {
        if (!maxHeapOfMinHalf.isEmpty() && in < maxHeapOfMinHalf.peek()) {
            maxHeapOfMinHalf.add(in);
        } else if (!minHeapOfMaxHalf.isEmpty() && in >= minHeapOfMaxHalf.peek()) {
            minHeapOfMaxHalf.add(in);
        } else {
            maxHeapOfMinHalf.add(in);
        }

        rebalance();
    }

    private void removeNumber(int out) {
        if (!maxHeapOfMinHalf.isEmpty() && out < maxHeapOfMinHalf.peek()) {
            maxHeapOfMinHalf.remove(out);
        } else if (!minHeapOfMaxHalf.isEmpty() && out >= minHeapOfMaxHalf.peek()) {
            minHeapOfMaxHalf.remove(out);
        }

        rebalance();
    }

    private void rebalance() {
        if (maxHeapOfMinHalf.size() > minHeapOfMaxHalf.size() + 1) {
            minHeapOfMaxHalf.add(maxHeapOfMinHalf.poll());
        } else if (minHeapOfMaxHalf.size() > maxHeapOfMinHalf.size() + 1) {
            maxHeapOfMinHalf.add(minHeapOfMaxHalf.poll());
        }
    }
}
