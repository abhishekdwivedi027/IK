package algo.enumeration;

import java.util.Stack;

public class Recursion {
	
	private static int steps;
	
	public static int towerOfHanoi(int n) {
		towerOfHanoiHelper(n, new Stack(), new Stack(), new Stack());
		return steps;
	}
	
	// T(n) = T(n-1) + 1 + T(n-1)
	private static void towerOfHanoiHelper(int n, Stack src, Stack dest, Stack aux) {
		if (n == 1) {
			steps++;
			return;
		}
		
		towerOfHanoiHelper(n-1, src, aux, dest);
		steps++;
		towerOfHanoiHelper(n-1, aux, dest, src);
	}

}
