package algo.enumeration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EnumerationTest {
	
	public static void main(String[] args) {
		
		System.out.println("fib 5 " + Computation.fibRecursionOne(5));
		System.out.println("fib 5 " + Computation.fibRecursionTwo(5));
		System.out.println("fib 5 " + Computation.fibIterative(5));
		System.out.println("fact 5 " + Computation.factorialRecursive(5));
		System.out.println("fact 5 " + Computation.factorialIterative(5));
		System.out.println("power 5 ^ 3  " + Computation.powerRecursive(5, 3));
		System.out.println("power -5 ^ 3  " + Computation.powerRecursive(-5, 3));
		System.out.println("power 5 ^ -3  " + Computation.powerRecursive(5, -3));
		System.out.println("power 5 ^ 3  " + Computation.powerIterative(5, 3));
		System.out.println("power -5 ^ 3  " + Computation.powerIterative(-5, 3));
		System.out.println("power 5 ^ -3  " + Computation.powerIterative(5, -3));
		
		System.out.println("tower of hanoi  " + Recursion.towerOfHanoi(3));
		
		// Combination.printBinaryStrings(3);
		// Combination.printDecimalStrings(3);
		
		// List<Character> chars = Arrays.asList('a', 'b', 'c');
		int[] nums = {10, 1, 2, 7, 6, 1, 5};
		
		// Combination.printSubsets(chars);
		// Combination.printDecimalStringsWithUniqueDigits(3);
		// Combination.subsetsWithPossibleDuplicates(nums);
		// Combination.subsetSize(nums, 2);
		Combination.subsetSum(nums, 8);
		
		
		// Permutation.letterCases("a1b2");
		// Permutation.printDecimalStrings(3);
		// Permutation.permuteWithPossibleDuplicates(nums);
		
		EnumerationTest enumerationTest = new EnumerationTest();
		String[] brackets = enumerationTest.find_all_well_formed_brackets(3);
		System.out.println(Arrays.toString(brackets));
	}
	
	private final List<String> wellFormBrackets = new ArrayList<>();
	public String[] find_all_well_formed_brackets(int n) {
		bracketHelper("", 0, 2 * n);
		return wellFormBrackets.toArray(new String[0]);
	}
	
	private void bracketHelper(String partialSolution, int subproblemSize, int n) {
		
		// backtracking
		if (isInvalid(partialSolution)) {
			return;
		}
		
		// leaf nodes
		if (subproblemSize == n) {
			if (isWellFormed(partialSolution)) {
				wellFormBrackets.add(partialSolution);
			}
			return;
		}
		
		// internal nodes
		bracketHelper(partialSolution + "(", subproblemSize + 1, n);
		bracketHelper(partialSolution + ")", subproblemSize + 1, n);
	}
	
	private long charCount(String s, char c) {
		return s.chars().filter(ch -> ch == c).count();
	}

	private boolean isInvalid(String partialSolution) {
		//true if #) > #(
		return charCount(partialSolution, '(') < charCount(partialSolution, ')'); 
	}
	
	private boolean isWellFormed(String partialSolution) {
		return charCount(partialSolution, '(') == charCount(partialSolution, ')'); 
	}

}
