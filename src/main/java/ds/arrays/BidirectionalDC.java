package ds.arrays;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BidirectionalDC {

    // using binary search
    public boolean hasTwoSum1(int[] nums, int sum) {
        boolean hasTwoSum = false;
        Arrays.sort(nums);
        int n = nums.length;
        for (int i=0; i<=n/2; i++) {
            int num = nums[i];
            int target = sum - num;
            if (isFound(nums, target, i+1, n-1)) {
                hasTwoSum = true;
                break;
            }
        }
        return hasTwoSum;
    }

    private boolean isFound(int[] nums, int target, int left, int right) {
        return getIndex(nums, target, left, right) != -1;
    }

    private int getIndex(int[] nums, int target, int left, int right) {
        int index = -1;
        if (left>right) {
            return index;
        }

        int mid = left + (right-left)/2;
        if (nums[mid] < target) {
            return getIndex(nums, target, mid+1, right);
        } else if (nums[mid] > target) {
            return getIndex(nums, target, left, mid-1);
        } else {
            return mid;
        }
    }

    // using two pointers
    public boolean hasTwoSum2(int[] nums, int sum) {
        boolean hasTwoSum = false;
        Arrays.sort(nums);
        int n = nums.length;
        int left = 0;
        int right = n-1;
        while (left < right) {
            if (nums[left] + nums[right] < sum) {
                left++;
            } else if (nums[left] + nums[right] > sum) {
                right--;
            } else {
                hasTwoSum = true;
                break;
            }
        }
        return hasTwoSum;
    }

    // using hashmap - no sorting
    public boolean hasTwoSum3(int[] nums, int sum) {
        boolean hasTwoSum = false;
        Map<Integer, Integer> map = new HashMap<>();
        int n = nums.length;
        for (int i=0; i<n; i++) {
            int num = nums[i];
            int target = sum - num;
            if (map.containsKey(target)) {
                hasTwoSum = true;
                break;
            } else {
                map.put(num, i);
            }
        }
        return hasTwoSum;
    }

    public int[] threeSum(int[] nums, int sum) {
        if (nums == null || nums.length < 3) {
            return null;
        }

        int[] threeSum = new int[3];
        int left = 0;
        int right = nums.length-1;
        Arrays.sort(nums);
        while (left < right) {
            int thirdNum = sum - (nums[left] + nums[right]);
            int thirdIndex = getIndex(nums, thirdNum, left, right);
            if (thirdIndex != -1) {
                threeSum[0] = left;
                threeSum[1] = thirdIndex;
                threeSum[2] = right;
                break;
            }

            if (sum >= nums[left] + nums[right]) {
                left++;
            } else {
                right--;
            }

        }
        return threeSum;
    }

    public int maxWater(int[] heights) {
        int n = heights.length;
        int left = 0;
        int right = n-1;
        int maxArea = 0;
        while (left < right) {
            int area = Math.min(heights[left], heights[right]) * (right - left);
            maxArea = Math.max(maxArea, area);

            // move the shorter bar
            if (heights[left] <= heights[right]) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }

    public int totalVolume(int[] heights) {
        int n = heights.length;
        int left = 0;
        int right = n-1;
        int globalLevel = 0;
        int totalVolume = 0;
        while (left < right) {
            int localLevel = Math.min(heights[left], heights[right]);
            if (localLevel > globalLevel) {
                totalVolume += (localLevel - globalLevel) * (right - left);
                globalLevel = localLevel;
            }

            // move the shorter bar
            if (heights[left] <= heights[right]) {
                left++;
            } else {
                right--;
            }
        }

        return totalVolume;
    }

    // HARD
    public int rainWater(int[] heights) {
        int n = heights.length;
        int left = 0;
        int right = n-1;
        int rainWater = 0;
        while (left < right) {

        }

        return rainWater;
    }

    // majority: frequency > n/2
    // mode: frequency max
    // alternate solution: map
    public boolean hasMajority(int[] nums) {
        if (nums == null || nums.length == 0) {
            return false;
        }

        int num = nums[0];
        int frequency = 1;
        int maxFrequency = 1;
        for (int i=1; i<nums.length; i++) {
            if (nums[i] == num) {
                frequency++;
            } else {
                maxFrequency = Math.max(maxFrequency, frequency);
                if (maxFrequency > nums.length/2) {
                    return true;
                }
                num = nums[i];
                frequency = 1;
            }
        }

        return false;
    }


    // scan and save frequency for the given target - 0(n)
    // return f > n/2
    public boolean isMajority1(int[] array, int target) {
        if (array == null || array.length == 0) {
            return false;
        }

        Arrays.sort(array);
        int n = array.length;
        int leftIndex = getLeftIndex(array, target, 0, n-1);
        int rightIndex = getRightIndex(array, target, 0, n-1);
        return rightIndex - leftIndex + 1 > n/2;
    }

    private int getLeftIndex(int[] sortedArray, int target, int left, int right) {
        if (left > right) {
            return -1;
        }

        int mid = left + (right - left)/2;
        if (sortedArray[mid] < target) {
            return getLeftIndex(sortedArray, target, left, mid-1);
        } else if (sortedArray[mid] > target) {
            return getLeftIndex(sortedArray, target, mid+1, right);
        } else {
            if (mid == left || sortedArray[mid-1] < sortedArray[mid]) {
                return mid;
            }
            return getLeftIndex(sortedArray, target, left, mid-1);
        }
    }

    private int getRightIndex(int[] sortedArray, int target, int left, int right) {
        if (left > right) {
            return -1;
        }

        int mid = left + (right - left)/2;
        if (sortedArray[mid] < target) {
            return getRightIndex(sortedArray, target, left, mid-1);
        } else if (sortedArray[mid] > target) {
            return getRightIndex(sortedArray, target, mid+1, right);
        } else {
            if (mid == right || sortedArray[mid+1] > sortedArray[mid]) {
                return mid;
            }
            return getRightIndex(sortedArray, target, mid+1, right);
        }
    }

    // scan and save frequency for the given target - 0(n)
    // return f > n/2
    public boolean isMajority2(int[] array, int target) {
        if (array == null || array.length == 0) {
            return false;
        }

        int n = array.length;;
        return getKth(array, 0, n-1, n/2) == target;
    }

    private int getKth(int[] nums, int start, int end, int kthIndex) {

        if (start == kthIndex) {
            return nums[start];
        }

        if (end == kthIndex) {
            return nums[end];
        }

        // nothing to do with K
        Random random = new Random();
        int pivotIndexInitial = random.nextInt(end - start) + start;
        // nothing to do with K
        int pivotIndexFinal = getPivotIndexFinal(nums, start, end, pivotIndexInitial);

        if (pivotIndexFinal < kthIndex) {
            return getKth(nums, pivotIndexFinal+1, end, kthIndex);
        } else if (pivotIndexFinal > kthIndex) {
            return getKth(nums, start, pivotIndexFinal-1, kthIndex);
        } else {
            return nums[pivotIndexFinal];
        }
    }

    private int getPivotIndexFinal(int[] nums, int start, int end, int pivotIndexInitial) {

        int pivotIndexFinal = pivotIndexInitial;

        int pivotValue = nums[pivotIndexInitial];
        nums[pivotIndexInitial] = nums[start];
        nums[start] = pivotValue;

        int left = start + 1;
        int right = end;

        while(left < right) {
            if (nums[left] < pivotValue) {
                left++;
            } else if (nums[right] > pivotValue) {
                right--;
            } else {
                int temp = nums[left];
                nums[left] = nums[right];
                nums[right] = temp;
                left++;
                right--;
            }
        }

        pivotIndexFinal = left-1;
        nums[start] = nums[pivotIndexFinal];
        nums[pivotIndexFinal] = pivotValue;

        return pivotIndexFinal;
    }
}
