package algo.common;

import java.util.Arrays;

public class CommonTest {
	
	public static void main(String[] args) {
		CommonTest commonTest = new CommonTest();
		int[] rep = commonTest.representNumber(10, 10);
		System.out.println(Arrays.toString(rep));
		int reversed = commonTest.reverseNumber(10);
		System.out.println(reversed);
		int quotient = commonTest.divide(-2147483648, -1);
		System.out.println(quotient);
	}
	
	// if y>10 this will return an array of ds.strings
	// for base 64: {0-9}{a-z}{A-Z}{-_}
	public int[] representNumber(int x, int y) {
		int[] rep = new int[String.valueOf(x).length()];
		
		int count = 0;
		while (x >= y) {
			rep[count++] = x%y; // remainder - mod
			x = x/y; // quotient - div
		}
		rep[count] = x;
		
		return rep;
	}

	
    public int reverseNumber(int x) {
    	
    	// return Integer.valueOf(String.valueOf(x));
    	// make sure at any stage any number shouldn't exceed the limits
        
        int higherLimit = (int) Math.pow(2.0, 31.0) - 1;
        int lowerLimit = (int) Math.pow(-2.0, 31.0);
        
        int result = 0;
        while (x != 0) {
        	int div = x/10;
        	int mod = x%10;
            x = div;
            
            if (result == 0 && mod == 0) {
                continue;
            }
            
            if (result < lowerLimit/10 || result > higherLimit/10) {
                return 0;
            }
            result = result * 10 + mod;
        }
        
        return result;
    }
  
	/*
	 
	public int reverseNumber(int x) {
		
		int lowerLimit = (int) Math.pow(-2.0, 31.0); // -2147483648
        int higherLimit = (int) Math.pow(2.0, 31.0) - 1; // 2147483646	
        
        boolean isNegative = Integer.signum(x) == -1;
		
		int[] rep = representNumber(x, 10);
		int n = rep.length;
		
		if (n>10) {
			return 0;
		}
		
		int reversed = 0;
		for (int i=n-1; i>-1; i--) {
			int multiplier = (int) Math.pow(10.0, n-1-i);
			int toBeAdded = rep[i] * multiplier;
			reversed += toBeAdded;
			if (reversed > higherLimit) {
				return 0;
			}
		}
		
		if (isNegative) {
			reversed *= -1;
		}
		
		return reversed;
    }
    
    */
	
	public int maxArea(int[] heights) {
        int maxArea = 0;
        int minHeight = 0;
        
        for (int i = heights.length-1; i>0; i--) {
        	
        	// algo.optimization
            if (heights[i] < minHeight) {
                continue;
            }
            
            for (int j = i-1; j>-1; j--) {
                int height = Math.min(heights[i], heights[j]);
                int area = (i - j) * height;
                if (area > maxArea) {
                    maxArea = area;
                    minHeight = height;
                }
            }
        }
        
        return maxArea;
    }
	
	public int removeDuplicates(int[] nums) {
        if (nums.length == 0) {
            return 0;
        }
        
        int leftMostM = -1;
        int lastValue = nums[0];
        for (int i=1; i<nums.length; i++) {
            if (nums[i] == lastValue) {
                nums[i] = Integer.MAX_VALUE;
                if (leftMostM == -1) {
                    leftMostM = i;
                }
            } else {
                lastValue = nums[i];
            }
        }
        
        if (leftMostM == -1) {
            return nums.length;
        }
        
        int nextNum = leftMostM+1;
        while (nextNum < nums.length) {
            
            if (nums[nextNum] == Integer.MAX_VALUE) {
                nextNum++;
                continue;
            }
            
            swap(nums, leftMostM, nextNum);
            // find next left most M
            while(leftMostM < nums.length) {
                if (nums[leftMostM] != Integer.MAX_VALUE) {
                    leftMostM++;
                } else {
                    break;
                }
            }
        }
        
        return leftMostM;
    }
    
    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
    
    public int divide(int dividend, int divisor) {
    	
        double higherLimit = Math.pow(2.0, 31.0) - 1.0; // 2147483646	
        int lowerLimit = (int) Math.pow(-2.0, 31.0); // -2147483648	
        int higherDiv = (int) Math.sqrt(higherLimit);
        
        int multiplier = Integer.signum(dividend) == Integer.signum(divisor) ? 1 : -1;
        dividend = dividend == lowerLimit ? (int) higherLimit : Math.abs(dividend);
        divisor = Math.abs(divisor);
        
        if (divisor == 1) {
            return dividend * multiplier;
        }
        
        int quotient = 0;
        int div = divisor;
        while (higherDiv > div && dividend > div*div) {
            dividend -= div*div;
            quotient += div;
        }
        while (dividend >= div) {
            dividend -= div;
            quotient += 1;
        }
        
        return quotient * multiplier;
    }
}
