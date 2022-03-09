package algo.enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Combination {
	
	// order doesn't matter in combinations
	// choose or leave
	
	// top down approach - DFS - lazy manager works up-front (at i = 0) - SELECTED - low space complexity O(n)
	// bottom up approach - BFS - lazy manager combines & extends (at i = n-1) - REJECTED - high space complexity O (2^n)
	
	// more than one recursion cases => combinatorial algo.enumeration problem
	// no BFS (bottom up recursion) in such situations
	// only DFS (top down recursion) for such problems
	
	
	// time complexity: top-down DFS - 0(n * 2^n)
	// space complexity: input(n) + output(n * 2^n) + implicit aux(n) + explicit aux(0) = O(n * 2^n)
	public static void printBinaryStrings(int n) {
		printBinaryStringsHelper("", n);
	}

	// subproblemSize => subproblemDefinition
	private static void printBinaryStringsHelper(String partialSolution, int subproblemSize) {
		
		//base case - for the last worker
		//when subproblemDefinition is empty
		if (subproblemSize == 0) {
			System.out.println(partialSolution);
			return;
		}
		
		//recursion case(s)- for all mid managers
		
		//exclusion - NA - partialSolution/subproblemSize doesn't change
		//Rem: exclusion case doesn't make sense where repetition is not allowed
		
		//inclusion - one call per inclusion 
		printBinaryStringsHelper(partialSolution.concat("0"), subproblemSize-1);
		printBinaryStringsHelper(partialSolution.concat("1"), subproblemSize-1);
	}
	
	public static void printDecimalStrings(int n) {
		printDecimalStringsHelper("", n);
	}

	private static void printDecimalStringsHelper(String partialSolution, int subproblemSize) {
		
		// base case - at every leaf node
		if (subproblemSize == 0) {
			System.out.println(partialSolution);
			return;
		}

		// recursion case(s) - at every internal node - for every inclusion
		for (int i=0; i<10; i++) {
			printDecimalStringsHelper(partialSolution + i, subproblemSize-1);
		}
	}
	
	public static void printDecimalStringsWithUniqueDigits(int n) {
		if (n > 10) {
			return;
		}
		
		Set<Integer> usedDigits = new HashSet<>();
		
		printDecimalStringsWithUniqueDigitsHelper("", n, usedDigits);
	}

	private static void printDecimalStringsWithUniqueDigitsHelper(String partialSolution, int subproblemSize, Set<Integer> usedDigits) {
		
		// base case - at every leaf
		if (subproblemSize == 0) {
			System.out.println(partialSolution);
			return;
		}

		// recursion case(s) - at every non-leaf
		for (int digit=0; digit<10; digit++) {
			
			if (usedDigits.contains(digit)) {
				continue;
			}
			
			//include digit
			usedDigits.add(digit);
			printDecimalStringsWithUniqueDigitsHelper(partialSolution + digit, subproblemSize - 1, usedDigits);
			usedDigits.remove(digit);
		}
	}
	
	// S(n) = total number of subsets
	// S(n) = 2 * S(n-1) -- exclude/include
	// S(n) = 2 ^ n
	public static void printSubsets(List<Character> chars) {
		if (chars == null || chars.size() == 0) {
			return;
		}
		
		printSubsetsHelper("", 0, chars);
	}

	// partialSolution must be IMMUTABLE in this implementation - ex: String
	// string + some_append doesn't mutate the original string
 	private static void printSubsetsHelper(String partialSolution, int subproblemIndex, List<Character> chars) {
		// base case
		if (subproblemIndex == chars.size()) {
			System.out.println(partialSolution);
			return;
		}
		
		// recursion case(s) - for each char - a. inclusion b. exclusion
		
		// exclusion
		printSubsetsHelper(partialSolution, subproblemIndex + 1, chars);
		// inclusion
		printSubsetsHelper(partialSolution + chars.get(subproblemIndex), subproblemIndex + 1, chars);
	}
 	
 	public static void subsetsWithPossibleDuplicates(int[] nums) {
 		Arrays.sort(nums);
 		subsetsWithPossibleDuplicatesHelper("", 0, nums);	
 	}

	private static void subsetsWithPossibleDuplicatesHelper(String partialSolution, int subproblemIndex, int[] nums) {
		
		if (subproblemIndex == nums.length) {
			System.out.println(partialSolution);
			return;
		}
		
		// let one manager deal with all copies of same number/character
		int count = getCount(nums, subproblemIndex);
	
		// exclusion
		subsetsWithPossibleDuplicatesHelper(partialSolution, subproblemIndex + count, nums);
		
		// inclusion
		String tempPartialSolution = partialSolution;
		for (int i=1; i<=count; i++) {
			tempPartialSolution += nums[subproblemIndex];
			subsetsWithPossibleDuplicatesHelper(tempPartialSolution, subproblemIndex + count, nums);
		}
	}

	private static int getCount(int[] nums, int i) {
		
		int j = i + 1;
		while (j < nums.length && nums[j] == nums[i]) {
			j++;
		}
		
		return j - i;
	}
	
	public static void subsetSize(int[] nums, int k) {
		if (nums == null || nums.length == 0 || k <= 0) {
			return;
		}
		
		subsetSizeHelper("", 0, nums, k);
	}

	// partialSolution must be IMMUTABLE in this implementation - ex: String
	// string + some_append doesn't mutate the original string
 	private static void subsetSizeHelper(String partialSolution, int subproblemIndex, int[] nums, int subsetSize) {
 		
 		// backtracking
 		if (partialSolution.length() == subsetSize) {
 			System.out.println(partialSolution);
 			return;
 		}
		
 		// base case
		if (subproblemIndex == nums.length) {
			// System.out.println(partialSolution);
			return;
		}
		
		// recursion case(s) - for each char - a. inclusion b. exclusion
		
		// exclusion
		subsetSizeHelper(partialSolution, subproblemIndex + 1, nums, subsetSize);
		// inclusion
		subsetSizeHelper(partialSolution + nums[subproblemIndex], subproblemIndex + 1, nums, subsetSize);
	}
 	
 	public static void subsetSum(int[] nums, int sum) {
		if (nums == null || nums.length == 0) {
			return;
		}
		
		List<Integer> partialSolution = new ArrayList<>();
		subsetSumHelper(partialSolution, 0, nums, sum);
	}

 	private static void subsetSumHelper(List<Integer> partialSolution, int subproblemIndex, int[] nums, int subsetSum) {
 		
 		// backtracking
 		if (getSum(partialSolution) == subsetSum) {
 			System.out.println(partialSolution);
 			return;
 		}
		
 		// base case
		if (subproblemIndex == nums.length) {
			// System.out.println(partialSolution);
			return;
		}
		
		// recursion case(s) - for each char - a. inclusion b. exclusion
		
		// exclusion
		subsetSumHelper(partialSolution, subproblemIndex + 1, nums, subsetSum);
		// inclusion
		int toBeAdded = nums[subproblemIndex];
		partialSolution.add(toBeAdded);
		subsetSumHelper(partialSolution, subproblemIndex + 1, nums, subsetSum);
		partialSolution.remove(partialSolution.lastIndexOf(toBeAdded));
	}

	private static int getSum(List<Integer> partialSolution) {
		
		return partialSolution.stream().mapToInt(Integer::intValue).sum();
	}

}
