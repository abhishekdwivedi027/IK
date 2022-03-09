package algo.sort;

public class SelectionSort {
	
	// brute force
	// time complexity 0(n*2)
	// space complexity 0(1)
	
	public static void selectionSort(Integer[] nums) {
		
		for (int i=0; i<nums.length; i++) {
			
			int minIndex = i;
			int minNum = nums[i];
			
			for (int j=i+1; j<nums.length; j++) {
				
				if (nums[j] < minNum) {
					minIndex = j;
					minNum = nums[j];
				}
			}
			
			// swap outside inner loop
			nums[minIndex] = nums[i];
			nums[i] = minNum;
		}
	}

}
