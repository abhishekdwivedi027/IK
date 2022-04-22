package ds.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
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
        nums = new int[]{1, 2, 3, 4, 5};
        System.out.println("products    " + Arrays.toString(tests.product(nums)));

        int[] buildings = {5, 1, 10, 2, 15, 3, 20, 4};
        System.out.println("buildings with view    " + tests.vantagePoints(buildings));

        ArrayList<ArrayList<Integer>> intervals = new ArrayList<>();
        intervals.add(new ArrayList<>(Arrays.asList(1, 3)));
        intervals.add(new ArrayList<>(Arrays.asList(5, 7)));
        intervals.add(new ArrayList<>(Arrays.asList(2, 4)));
        intervals.add(new ArrayList<>(Arrays.asList(6, 8)));
        tests.getMergedIntervals(intervals);

        int[] rotated = {8, 9, 1, 2, 3, 4, 5};
        System.out.println("min index sorted rotated    " + tests.findMinInRotatedSorted(rotated));
        System.out.println("find index sorted rotated    " + tests.searchInRotatedSorted(rotated, 9));
        System.out.println("find index sorted rotated    " + tests.searchInRotatedSorted(rotated, 0));
    }

    public int[] product(int[] nums) {
        if (nums == null || nums.length < 2) {
            return null;
        }

        int n = nums.length;;
        int[] products = new int[n];

        // calculate prefix
        int prefix = 1;
        for (int i=0; i<n ; i++) {
            products[i] = prefix;
            prefix *= nums[i];
        }

        // calculate postfix
        int postfix = 1;
        for (int i=n-1; i>-1 ; i--) {
            products[i] *= postfix;
            postfix *= nums[i];
        }

        return products;
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


    ArrayList<ArrayList<Integer>> getMergedIntervals(ArrayList<ArrayList<Integer>> intervals) {

        if (intervals == null || intervals.size() < 2) {
            return intervals;
        }

        ArrayList<ArrayList<Integer>> mergedIntervals = new ArrayList();

        // sort intervals based on start time
        intervals.sort(Comparator.comparing((list) -> list.get(0)));

        ArrayList<Integer> mergedInterval = new ArrayList<>();
        for (ArrayList<Integer> interval: intervals) {
            if (mergedInterval.size() == 0) {
                mergedInterval.add(interval.get(0));
                mergedInterval.add(interval.get(1));
                mergedIntervals.add(mergedInterval);
                continue;
            }

            if (mergedInterval.get(1) >= interval.get(0)) {
                mergedInterval.set(1, Math.max(interval.get(1), mergedInterval.get(1)));
            } else {
                mergedInterval = new ArrayList<>();
                mergedInterval.add(interval.get(0));
                mergedInterval.add(interval.get(1));
                mergedIntervals.add(mergedInterval);
            }
        }

        return mergedIntervals;
    }

    public int findMinInRotatedSorted(int[] nums) {
        if (nums == null || nums.length < 1) {
            return -1;
        }

        if (nums.length == 1) {
            return nums[0];
        }

        int n = nums.length;
        int left = 0;
        int right = n-1;

        return findMinInRotatedSortedHelper(nums, left, right);
    }

    private int findMinInRotatedSortedHelper(int[] nums, int left, int right) {
        if (left > right) {
            return -1;
        }

        int mid = left + (right - left)/2;

        if (mid == 0) {
            if (nums[mid] < nums[mid+1]) {
                return mid;
            } else {
                return mid+1;
            }
        }

        if (nums[mid] < nums[mid-1]) {
            return mid;
        }

        if (nums[mid] < nums[right]) {
            return findMinInRotatedSortedHelper(nums, left, mid-1);
        } else {
            return findMinInRotatedSortedHelper(nums, mid+1, right);
        }
    }

    public int searchInRotatedSorted(int[] nums, int num) {
        if (nums == null || nums.length < 1) {
            return -1;
        }

        if (nums.length == 1) {
            return nums[0] == num ? 0 : -1;
        }

        int left = 0;
        int right = nums.length-1;

        int min = findMinInRotatedSortedHelper(nums, left, right);
        if (min == -1) {
            return -1;
        }

        if (num >= nums[min] && num <= nums[right]) {
            return binarySearch(nums, num, min, right);
        }

        if (min > 0 && num >= nums[left] && num <= nums[min-1] ) {
            return binarySearch(nums, num, left, min-1);
        }

        return -1;
    }

    private int binarySearch(int[] nums, int num, int left, int right) {
        if (left > right) {
            return -1;
        }

        int mid = left + (right - left)/2;
        if (num == nums[mid]) {
            return mid;
        } else if (num < nums[mid]) {
            return binarySearch(nums, num, left, mid-1);
        } else {
            return binarySearch(nums, num, mid+1, right);
        }
    }
}
