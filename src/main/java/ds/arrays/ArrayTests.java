package ds.arrays;

import java.util.ArrayList;
import java.util.List;

public class ArrayTests {

    public static void main(String[] args) {
        CycleSort cycleSort = new CycleSort();
        int[] nums = {1};
        System.out.println("smallest positive missing   " + cycleSort.smallestPositiveMissing(nums));

        SlidingWindow slidingWindow = new SlidingWindow();
        nums = new int[]{2, 2, 2, 2, 5, 5, 5, 8};
        System.out.println("count    " + slidingWindow.windowAverageThresholdCount(nums, 4, 3));
        String s = "havefunonleetcode";
        System.out.println("no repeated chars count    " + slidingWindow.noRepeatedCharactersCount(s, 5));

        nums = new int[]{2, 3, 1, 2, 4, 3};
        System.out.println("min length subarray    " + slidingWindow.minLengthSubarraySum(nums, 7));

        nums = new int[]{10, 5, 2, 6};
        System.out.println("subarray prod count    " + slidingWindow.subarrayProductCount(nums, 100));

        ArrayTests tests = new ArrayTests();
        int[] buildings = {5, 1, 10, 2, 15, 3, 20, 4};
        System.out.println("buildings with view    " + tests.vantagePoints(buildings));
    }

    // this is NOT an optimization problem
    // no local and global result
    public List<Integer> vantagePoints(int[] heights) {
        if (heights == null || heights.length == 0) {
            return null;
        }

        List<Integer> result = new ArrayList<>();
        int lastHeight = Integer.MIN_VALUE;
        for (int height: heights) {
            if (height > lastHeight) {
                lastHeight = height;
                result.add(height);
            }
        }

        return result;
    }
}
