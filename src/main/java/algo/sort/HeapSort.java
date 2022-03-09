package algo.sort;

import java.util.Arrays;
import java.util.PriorityQueue;

//divide and conquer
//time complexity 0(nlogn)
//space complexity 0(n)

public class HeapSort {

	public static void heapSort(Integer[] nums) {
		
		/*
		 * PriorityQueue<Integer> minHeap = new PriorityQueue<Integer>();
		 * minHeap.addAll(Arrays.asList(nums)); 
		 * for (int i=0; i<nums.length; i++) {
		 *     nums[i] = minHeap.poll(); 
		 * }
		 */
		
		// PriorityQueue<Integer> maxHeap = new PriorityQueue<Integer>(Comparator.reverseOrder());
		
		Heap heap = new Heap(nums);
		for (int i=0; i<nums.length; i++) {
			nums[i] = heap.extract();
		}
	}
	

	// min heap
	public static class Heap {
		
		private int heapSize;
		private Integer[] heapArray;
		
		public Heap(Integer[] nums) {
			// heap size is array size plus one
			heapSize = nums.length+1;
			// copy array into heap array
			heapArray = Arrays.copyOf(nums, heapSize);
			// copy root value at the last
			heapArray[heapSize-1] = heapArray[0];
			// first element of heap array is null 
			heapArray[0] = null;
			
			
			int lastChildindex = heapSize-1;
			int lastParentIndex = lastChildindex/2;
			
			while (lastChildindex>lastParentIndex) {
				swim(lastChildindex--);
			}
			
			// System.out.println(Arrays.toString(heapArray));
		}
		

		private void swim(int i) {
			
			if (i<=1) {
				return;
			}
			
			if (heapArray[i] < heapArray[i/2]) {
				Integer temp = heapArray[i];
				heapArray[i] = heapArray[i/2];
				heapArray[i/2] = temp;
				
				swim(i/2);
			}
		}
		
		private Integer extract() {
			int rootValue = heapArray[1];
			int extractIndex = heapSize-1;
			heapArray[1] = heapArray[extractIndex];
			heapArray[extractIndex] = null;
			heapSize--;
			sink(1);
			
			return rootValue;
		}
		
		private void sink(int i) {
			
			int minIndex = getMinIndex(i, 2*i, 2*i+1);
			
			if (i != minIndex) {
				Integer temp = heapArray[i];
				heapArray[i] = heapArray[minIndex];
				heapArray[minIndex] = temp;
				
				sink(minIndex);
			}
		}

		private int getMinIndex(int parentIndex, int childIndex1, int childIndex2) {
			
			int minIndex = parentIndex;
			
			if (childIndex1 <= heapSize && heapArray[childIndex1] != null) {
				minIndex = heapArray[minIndex] <= heapArray[childIndex1] ? minIndex : childIndex1;
			}
			
			if (childIndex2 <= heapSize && heapArray[childIndex2] != null) {
				minIndex = heapArray[minIndex] <= heapArray[childIndex2] ? minIndex : childIndex2;
			}
			
			return minIndex;
		}
		
		private void insert(Integer num) {
			int insertIndex = heapSize++;
			heapArray[insertIndex] = num;
			swim(insertIndex);
		}

	}

}
