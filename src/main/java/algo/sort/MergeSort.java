package algo.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

//divide and conquer
//time complexity 0(nlogn)
//space complexity 0(n)
//stable

public class MergeSort {
	
	public static void mergeSort(int[] nums) {
		mergeSortHelper(nums, 0, nums.length-1);
	}

	private static void mergeSortHelper(int[] nums, int start, int end) {
		
		if (start >= end) {
			return;
		}
		
		int mid = start + (end - start)/2;
		mergeSortHelper(nums, start, mid);
		mergeSortHelper(nums, mid+1, end);
		merge(nums, start, mid, end);
	}

	// merge two sorted ds.arrays
	private static void merge(int[] nums, int start, int mid, int end) {
		
		// aux array of size end-start+1
		// we can work with array since we know the size
		int[] aux = new int[end-start+1];

		// array one - from start to mid
		// array two - from mid+1 to end
		int one = start;
		int two = mid+1;
		int merged = 0;
		
		// step 1: combine left and right ds.arrays
		while (one<=mid && two<=end) {
			if (nums[one] < nums[two]) {
				aux[merged++] = nums[one++];
			} else if (nums[two] < nums[one]) {
				aux[merged++] = nums[two++];
			} else {
				// for stability - select common from left array
				aux[merged++] = nums[one++];
				// don't leave out duplicates
				// two++;
			}
		}
		
		// step 2: gather the remaining elements
		
		// 2a: copy left array to aux array
		while (one<=mid) {
			aux[merged++] = nums[one++];
		}
		
		// 2b: copy right array to aux array
		while (two<=end) {
			aux[merged++] = nums[two++];
		}
		
		// step 3: copy aux to array (from start to end)
		one = start;
		merged = 0;
		while (merged<aux.length) {
			nums[one++] = aux[merged++];
		}
		
	}
	
	/*
	 * Application of Merge Sort
	 * 
	 */

	// merge + only unique elements + leave duplicates
	public static int[] union(int[] nums1, int[] nums2) {
		
		Arrays.sort(nums1);
		Arrays.sort(nums2);
		
		List<Integer> union = new ArrayList<>();
		int one = 0;
		int two = 0;
		while (one<nums1.length && two<nums2.length) {
			
			if (nums1[one] < nums2[two]) {
				union.add(nums1[one++]);
			} else if (nums1[one] > nums2[two]) {
				union.add(nums2[two++]);
			} else {
				//select common from left array
				union.add(nums1[one++]);
				//leave out duplicates
				//extra step
				two++;
			}
		}
		
		//gather up the remaining elements
		
		//include duplicates from left array
		while (one<nums1.length) {
			union.add(nums1[one++]);
		}
		
		//include duplicates from right array
		while (two<nums2.length) {
			union.add(nums2[two++]);
		}
		
		return union.stream()
				.filter(Objects::nonNull)
				.mapToInt(Integer::intValue)
                .toArray();
	}

	// merge + only common elements + leave duplicates
	public static int[] intersection(int[] nums1, int[] nums2) {
		
		Arrays.sort(nums1);
		Arrays.sort(nums2);
		
		List<Integer> intersection = new ArrayList<>();
		int one = 0;
		int two = 0;
		while (one<nums1.length && two<nums2.length) {
			
			if (nums1[one] < nums2[two]) {
				one++;
			} else if (nums1[one] > nums2[two]) {
				two++;
			} else {
				//select common from left array
				intersection.add(nums1[one]);
				one++;
				//leave out duplicates
				two++;
			}
		}
		
		return intersection.stream()
				.filter(Objects::nonNull)
				.mapToInt(Integer::intValue)
                .toArray();
	}


}
