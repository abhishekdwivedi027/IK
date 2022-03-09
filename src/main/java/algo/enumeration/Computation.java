package algo.enumeration;

public class Computation {
	
	public static int fibRecursionOne(int n) {
		
		// base/exit cases
		if (n == 0 || n == 1) {
			return n;
		}
		
		return fibRecursionOne(n-1) + fibRecursionOne(n-2);
	}
	
	// additive sequence
	public static int fibRecursionTwo(int n) {
		
		// input check
		if (n == 0 || n == 1) {
			return n;
		}
		
		return fibRecursionTwoHelper(n, 0, 1);
		
	}

	private static int fibRecursionTwoHelper(int n, int i, int j) {
		
		// exit case
		if (n == 0) {
			return i;
		}
		
		return fibRecursionTwoHelper(n-1, j, i+j);
	}
	
	public static int fibIterative(int n) {
		
		//input check
		if (n == 0 || n == 1) {
			return n;
		}
		
		int first = 0;
		int second = 1;
		int result = 0;
		
		for (int i=2; i<=n; i++) {
			
			result = first + second;
			first = second;
			second = result;
		}
		
		return result;
	}
	
	public static int factorialRecursive(int n){
		
		//input check
		if (n <= 0)
			return 0;
		
		return factorialRecursionHelper(n);
	}

	private static int factorialRecursionHelper(int n) {
		
		//exit condition
		if (n == 1){
			return 1;
		}
		
		return n * factorialRecursionHelper(n-1);
	}
	
	public static int factorialIterative(int n){
		
		//input check
		if (n <= 0)
			return 0;
		
		int result = 1;
		
		for (int i=2; i<=n; i++) {
			result = i * result;
		}
		
		return result;
	}
	
	public static int powerRecursive(int base, int exponent){
		
		//input check
		if (base == 0 || base == 1) {
			return base;
		}
		
		int result = powerRecursionHelper(base, Math.abs(exponent));
		
		return exponent >= 0 ? result : 1/result; 
	}

	private static int powerRecursionHelper(int base, int exponent) {
		
		if (exponent == 0) {
			return 1;
		}
		
		return base * powerRecursionHelper(base, exponent-1);
	}
	
	public static int powerIterative(int base, int exponent){
		
		//input check
		if (base == 0 || base == 1) {
			return base;
		}
		
		int exp = Math.abs(exponent);
		
		int result = 1;
		for (int i=0; i<exp; i++) {
			result *= base;
		}
		
		return exponent >= 0 ? result : 1/result; 
	}

}
