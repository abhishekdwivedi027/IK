package ds.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ArrayTests {

    public static void main(String[] args) {
        CycleSort cycleSort = new CycleSort();
        int[] nums = {1, 8, 6, 2, 9, 7, 5, 4, 0, 3};
        cycleSort.cycleSort(nums);
        System.out.println("cycle sort   " + Arrays.toString(nums));
        nums = new int[] {1, 8, 6, 2, 9, 3, 5, 4, 0, 3};
        System.out.println("missing number   " + cycleSort.missingNumber(nums));
        System.out.println("duplicate number   " + cycleSort.duplicateNumber(nums));
        nums = new int[] {1, -7, 8, -1, 6, 2, -69, 9, 3, 5, 4, 3};
        System.out.println("smallest positive missing   " + cycleSort.smallestPositiveMissing(nums));

        SlidingWindowFixed slidingWindowFixed = new SlidingWindowFixed();
        nums = new int[]{2, 2, 2, 2, 5, 5, 5, 8};
        System.out.println("count    " + slidingWindowFixed.windowAverageThresholdCount(nums, 4, 3));
        String s = "havefunonleetcode";
        System.out.println("no repeated chars count    " + slidingWindowFixed.noRepeatedCharactersCount(s, 5));
        System.out.println("is permutation    " + slidingWindowFixed.isPermutationPresent("rb", "abracadabra"));
        System.out.println("max card points    " + slidingWindowFixed.maxTotalPointsFromSides(nums, 2));

        SlidingWindowVariable slidingWindowVariable = new SlidingWindowVariable();
        nums = new int[]{2, 3, 1, 2, 4, 3};
        System.out.println("min length subarray    " + slidingWindowVariable.minLengthSubarraySum(nums, 7));
        System.out.println("max length subarray    " + slidingWindowVariable.maxLengthSubarraySum(nums, 7));
        nums = new int[]{3, 3, 3, 1, 2, 1, 1, 2, 3, 3, 4};
        System.out.println("max fruits    " + slidingWindowVariable.maxFruits(nums));
        System.out.println("max score    " + slidingWindowVariable.maxSumSubarrayDistinctAll(nums));
        System.out.println("subarrayCountDistinctK   " + slidingWindowVariable.subarrayCountDistinctK(nums, 3));
        System.out.println("minWindowSubstring   " + slidingWindowVariable.minWindowSubstring("adobecodebanc", "abc"));

        String letters = "ccaabbb";
        System.out.println("longest substring    " + slidingWindowVariable.longestSubstringDistinctMaxK(letters, 2));

        nums = new int[]{10, 5, 2, 6};
        System.out.println("subarray prod count    " + slidingWindowVariable.subarrayProductLessThanTargetCount(nums, 100));

        BidirectionalDC bidirectionalDC = new BidirectionalDC();
        int[] heights = {9, 3, 7, 1, 3, 2, 8, 1, 1, 3};
        System.out.println("max water    " + bidirectionalDC.maxWater(heights));
        System.out.println("total volume    " + bidirectionalDC.totalVolume(heights));

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
