package algo.sort;

// decrease and conquer
// time complexity 0(n*2)
// space complexity 0(1)

public class InsertionSort {
	
	
	public static void insertionSort(Integer[] nums) {
		insertionSortHelper(nums, nums.length-1);
	}

	private static void insertionSortHelper(Integer[] nums, int n) {
	
		if (n <= 0) {
			return;
		}
		
		// algo.sort rest of the array from 0 to n-1
		insertionSortHelper(nums, n-1);
		
		// insert nth element into sorted array
		insert(nums, n);
	}

	private static void insert(Integer[] nums, int n) {
	
		int element = nums[n];
		int i = n-1;
		
		while (i>=0 && nums[i]>element) {
			// shift
			nums[i+1] = nums[i--];
		}

		// insert
		nums[i+1] = element;
	}

}
