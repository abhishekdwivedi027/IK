package algo.enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Permutation {
	
	// order matters in permutations
	// arrange
	
	// top down approach - DFS - lazy manager works up-front (at i = 0) - SELECTED - low space complexity O(n)
	// bottom up approach - BFS - lazy manager combines & extends (at i = n-1) - REJECTED - high space complexity O (2^n)
	
	// more than one recursion cases => combinatorial algo.enumeration problem
	// no BFS (bottom up recursion) in such situations
	// only DFS (top down recursion) for such problems
	
	// time complexity: top-down DFS - 0(n * n!)
	// space complexity: input(n) + output(n * n!) + implicit aux(n) + explicit aux(0) = 0(n * n!)
	
	// private static List<String> result = new ArrayList();
	
	public static void letterCases(String s) {
		if (s == null || s.length() == 0) {
			return;
		}
		
		letterCasesHelper("", 0, s);
	}

	private static void letterCasesHelper(String partialSolution, int subproblemSize, String s) {
		
		// base case : leaf node
		if (subproblemSize == s.length()) {
			// result.add(partialSolution);
			System.out.println(partialSolution);
			return;
		}
		
		// recursion case : internal node
		char c = s.charAt(subproblemSize);
		// lower case
		letterCasesHelper(partialSolution + c, subproblemSize + 1, s);
		// upper case
		if (!Character.isDigit(c)) {
			letterCasesHelper(partialSolution + Character.toUpperCase(c), subproblemSize + 1, s);
		} 
	}
	
	public static void printDecimalStrings(int n) {
		
		if (n < 1 || n > 9) {
			return;
		}
		
		int[] nums = new int[n];
		for (int i=0; i<n; i++) {
			nums[i] = i+1;
		}
		
		printDecimalStringsHelper("", 0, nums);
	}

	// permutation => swap => array format
	private static void printDecimalStringsHelper(String partialSolution, int subproblemIndex, int[] nums) {
		
		// base case - leaf node
		if (subproblemIndex == nums.length) {
			System.out.println(partialSolution);
			return;
		}
		
		// recursion case - internal node
		int toBeAdded = nums[subproblemIndex];
		for (int i=subproblemIndex; i<nums.length; i++) {
			swap(nums, i, subproblemIndex);
			// partialSolution.add(toBeAdded) - if partialSolution were mutable
			printDecimalStringsHelper(partialSolution + toBeAdded, subproblemIndex + 1, nums);
			// partialSolution.remove(toBeAdded) - if partialSolution were mutable
			swap(nums, i, subproblemIndex);
		}
		
	}

	private static void swap(int[] nums, int i, int j) {
		int temp = nums[i];
		nums[i] = nums[j];
		nums[j] = temp;
	}
	
	public static void permuteWithPossibleDuplicates(int[] nums) {
		Arrays.sort(nums);
		permuteWithPossibleDuplicatesHelper("", 0, nums);
	}

	private static void permuteWithPossibleDuplicatesHelper(String partialSolution, int subproblemIndex, int[] nums) {
		
		if (subproblemIndex == nums.length) {
			System.out.println(partialSolution);
			return;
		}
		
		int toBeAdded = nums[subproblemIndex];
		for (int i=subproblemIndex; i<nums.length; i++) {
            
			// find the last index for a number
			// and leave out the duplicates
			if (i!=subproblemIndex && nums[i] == toBeAdded) {
                continue;
            } 
            toBeAdded = nums[i];
			
            swap(nums, i, subproblemIndex);
			permuteWithPossibleDuplicatesHelper(partialSolution + toBeAdded, subproblemIndex + 1, nums);
			swap(nums, i, subproblemIndex);
		}
		
	}
}
