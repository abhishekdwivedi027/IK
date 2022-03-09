package algo.sort;

import org.apache.commons.lang3.RandomUtils;

import java.util.Arrays;
import java.util.Random;

//divide and conquer
//time complexity 0(nlogn)
//space complexity 0(1)
//in-place

public class QuickSort {
	
	public static void quickSort(int[] nums) {
		quickSortHelper(nums, 0, nums.length-1);
	}

	private static void quickSortHelper(int[] nums, int start, int end) {

		if (start >= end) {
			return;
		}
		
		int pivotIndexInitial = getPivotIndexInitial(start, end);
		int pivotIndexFinal = getPivotIndexFinal(nums, start, end, pivotIndexInitial);
		quickSortHelper(nums, start, pivotIndexFinal-1);
		quickSortHelper(nums, pivotIndexFinal+1, end);
	}

	private static int getPivotIndexInitial(int min, int max) {
		// (int) ((Math.random() * (max - min)) + min);
	    // Random random = new Random();
	    // return random.nextInt(max - min) + min;
		return RandomUtils.nextInt(min, max+1);
	}
	
	private static int getPivotIndexFinal(int[] nums, int start, int end, int pivotIndexInitial) {
		
		int pivotIndexFinal = pivotIndexInitial;
		
		// place pivot at the start
		int pivotValue = nums[pivotIndexInitial];
		nums[pivotIndexInitial] = nums[start];
		nums[start] = pivotValue;
		
		int left = start+1;
		int right = end;
		
		while (left<=right) {
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

		// remember - left minus one
		pivotIndexFinal = left-1;
		
		// place pivot at the pivot index final
		nums[start] = nums[pivotIndexFinal];
		nums[pivotIndexFinal] = pivotValue;
		
		return pivotIndexFinal;
	}
	
	/*
	 * Application of Quick Sort
	 * 
	 */
	
	public static int kthMin(int[] nums, int k) {
		int kthMin = -1;
		if (nums == null && nums.length < k) {
			return kthMin;
		}
		
		kthMin = getKth(nums, 0, nums.length-1, k-1);
		
		return kthMin;
	}
	
	public static int kthMax(int[] nums, int k) {
		int kthMax = -1;
		if (nums == null && nums.length < k) {
			return kthMax;
		}
		
		kthMax = getKth(nums, 0, nums.length-1, nums.length-k);
		
		return kthMax;
	}


	private static int getKth(int[] nums, int start, int end, int kthIndex) {
		
		if (start == kthIndex) {
			return nums[start];
		}
		if (end == kthIndex) {
			return nums[end];
		}

		// nothing to do with K
		int pivotIndexInitial = getPivotIndexInitial(start, end);
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
	
	// RWB
	public static char[] dutchFlag(char[] chars) {
		int red = 0;
		int white = 0;
		int blue = chars.length - 1;

		// the middle pointer starts at the left and move rightwards
		while (red <= white && white <= blue) {
			if (chars[white] == 'R') {
				swap(chars, white, red);
				red++;
				white++;
			} else if (chars[white] == 'B') {
				swap(chars, white, blue);
				//white++;
				blue--;
			} else {
				white++;
			}
		}
		
		return chars;
	}

	private static void swap(char[] chars, int i, int j) {
		char temp = chars[i];
		chars[i] = chars[j];
		chars[j] = temp;
	}

}
