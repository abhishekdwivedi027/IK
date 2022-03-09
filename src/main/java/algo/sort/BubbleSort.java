package algo.sort;

public class BubbleSort {
	
	// brute force
	// time complexity 0(n*2)
	// space complexity 0(1)
	
	public static void bubbleSort(Integer[] nums) {
		
		for (int i=0; i<nums.length; i++) {
			
			//reverse scan
			for (int j=nums.length-1; j>i; j--) {
				
				if (nums[j-1] > nums[j]) {
					// swap inside inner loop
					int temp = nums[j-1];
					nums[j-1] = nums[j];
					nums[j] = temp;
				}
			}
		}
	}

}
