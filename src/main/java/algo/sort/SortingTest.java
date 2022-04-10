package algo.sort;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class SortingTest {
	
	public static void main(String[] args) {

		SortingTest sortingTest = new SortingTest();

		Integer[] nums1 = {2, 4, 6, 8, 10, 9, 7, 5, 3, 1, 0, -1, 10, 9, 10};
		int[] numbers1 = Arrays.asList(nums1).stream().mapToInt(Integer::intValue).toArray();
		System.out.println("unsorted array 1     " + Arrays.toString(numbers1));
		QuickSort.quickSort(numbers1);
		System.out.println("sorted array 1     " + Arrays.toString(numbers1));
		
		Integer[] nums2 = {1, 3, 5, 7, 9, 10, 8, 6, 4, 2, 1, 0, 9, 8, 9};
		int[] numbers2 = Arrays.asList(nums2).stream().mapToInt(Integer::intValue).toArray();
		System.out.println("unsorted array 2     " + Arrays.toString(nums2));
		HeapSort.heapSort(nums2);
		System.out.println("sorted array 2     " + Arrays.toString(nums2));

		
		// SelectionSort.selectionSort(nums1);
		// BubbleSort.bubbleSort(nums1);
		// InsertionSort.insertionSort(nums1);
		// MergeSort.mergeSort(nums1);
		// QuickSort.quickSort(nums1);
		// HeapSort.heapSort(nums1);
		// System.out.println(Arrays.toString(nums1));
	
		System.out.println("3rd max " + QuickSort.kthMax(numbers1, 3));
		System.out.println("4th max " + QuickSort.kthMax(numbers1, 4));
		System.out.println("3rd min " + QuickSort.kthMin(numbers1, 3));
		System.out.println("4th min " + QuickSort.kthMin(numbers1, 4));

		System.out.println("two sum " + Arrays.toString(sortingTest.twoSumIndices(numbers1, 15)));
		
		System.out.println("union    " + Arrays.toString(MergeSort.union(numbers1, numbers2)));
		System.out.println("intersection    " + Arrays.toString(MergeSort.intersection(numbers1, numbers2)));
		
		char[] colors = {'W', 'W', 'B', 'B', 'R', 'R', 'B', 'W', 'R'};
		QuickSort.dutchFlag(colors);
		System.out.println("dutch flag     " + Arrays.toString(colors));
	}

	// transform - REARRANGE input - algo.sort
	// algo.sort when indices don't matter
	public boolean hasTwoSum(int[] nums, int target) {
		Arrays.sort(nums);
		
		int i = 0;
		int j = nums.length - 1;
		while(i<j) {
			int comp = target - nums[i];
			if (nums[j] == comp) {
				return true;
			}

			if (nums[j] > comp) {
				j--;
			} else {
				i++;
			}
		}
		
		return false;
	}
	
	// transform - REPRESENT input to solve blocking operation (search) - hashmap
	// cannot sort when indices are important
	public int[] twoSumIndices(int[] nums, int target) {
		Map<Integer, Integer> map = new HashMap<>();
		
		int[] indices = new int[2];
		
		// decrease and conquer: head recursion or bottom-up
		for(int i=nums.length-1; i>-1; i--) {
			int comp = target - nums[i];
			if (map.keySet().contains(comp)) {
				indices[0] = i;
				indices[1] = map.get(comp);
				break;
			}
			map.put(nums[i], i);
		}
		
		return indices;
	}
	
	//kth max 
	//array of k elements and algo.sort each time an element is added
	//max heap of k elements and compare/remove root if need be
	public int secondMax(int[] nums) {
		int secMax = -1;
		if (nums == null || nums.length < 2) {
			return secMax;
		}
		
		int max = Math.max(nums[0], nums[1]);
		secMax = Math.min(nums[0], nums[1]);
		
		for (int i=2; i<nums.length; i++) {
			if (nums[i] > max) {
				secMax = max;
				max = nums[i];
			} else if (nums[i] > secMax) {
				secMax = nums[i];
			}
		}
		
		return secMax;
	}
	
	
}
