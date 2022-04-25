package algo.streams;

import java.util.Collections;
import java.util.Comparator;
import java.util.PriorityQueue;

public class Median {

    private PriorityQueue<Integer> minHeapOfMaxHalf = new PriorityQueue<>();
    private PriorityQueue<Integer> maxHeapOfMinHalf = new PriorityQueue<>(Comparator.reverseOrder());

    // one half side - the side with smaller nums - is kept in max heap
    // other half side - the side with larger nums - is kept in min heap
    public void addNumber(int in) {
        if (!maxHeapOfMinHalf.isEmpty() && in < maxHeapOfMinHalf.peek()) {
            maxHeapOfMinHalf.add(in);
        } else if (!minHeapOfMaxHalf.isEmpty() && in >= minHeapOfMaxHalf.peek()) {
            minHeapOfMaxHalf.add(in);
        } else {
            maxHeapOfMinHalf.add(in);
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


    public void removeNumber(int out) {
        if (!maxHeapOfMinHalf.isEmpty() && out < maxHeapOfMinHalf.peek()) {
            maxHeapOfMinHalf.remove(out);
        } else if (!minHeapOfMaxHalf.isEmpty() && out >= minHeapOfMaxHalf.peek()) {
            minHeapOfMaxHalf.remove(out);
        }

        rebalance();
    }
}
