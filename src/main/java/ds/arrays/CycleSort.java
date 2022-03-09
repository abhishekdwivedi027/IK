package ds.arrays;

public class CycleSort {
    /*
        Decrease and Conquer

        {
            1. lazy manager works on leftmost

            2. recursive call on remaining elements - tail recursion
        }

        ex: selection algo.sort - select the minimum for the leftmost index and then proceed rightwards

        or

        {
            1. recursive call on remaining elements - head recursion

            2. lazy manager works on leftmost
        }

        ex: insertion algo.sort - insert the leftmost after sorting the rest

        cycle algo.sort - like selection algo.sort - instead of finding min number leftmost index we will find right index for the leftmost number
     */

    // nums has o to n-1
    public void cycleSort(int[] nums) {
        int n = nums.length;
        for (int i=0; i<n; i++) {
            while (nums[i] != i) {
                // j is where nums[i] should go
                int j = nums[i]; // destination index
                int temp = nums[j];
                nums[j] = nums[i];
                nums[i] = temp;
            }
        }
    }

    // nums has o to n - total n+1 nums - array size n - numbers > array length => one num is missing
    public int missingNumber(int[] nums) {
        int n = nums.length;
        for (int i=0; i<n; i++) {
            while (nums[i] != i) {
                // j is where nums[i] should go
                int j = nums[i];
                // if j is valid destination index
                if (isValidDestinationIndex(nums, 0, nums.length-1, i, j)) {
                    int temp = nums[j];
                    nums[j] = nums[i];
                    nums[i] = temp;
                } else {
                    // nums[i] at index i is not at right place
                    break;
                }
            }
        }

        int missingNumber = n;
        // missing number won't be where it should be
        for (int i=0; i<n; i++) {
            if (nums[i] != i) {
                missingNumber = i;
                break;
            }
        }

        return missingNumber;
    }

    private boolean isValidDestinationIndex(int[] nums, int left, int right, int sourceIndex, int destinationIndex) {
        return  destinationIndex >= left && destinationIndex <= right && nums[sourceIndex] != nums[destinationIndex];
    }

    // nums has 1 to n - total n nums (unless duplicate) - array size n+1 - one num is duplicate
    // num = index + 1 => index = num - 1
    public int duplicateNumber(int[] nums) {
        int m = nums.length;
        for (int i=0; i<m; i++) {
            while (nums[i]-1 != i) {
                // j is where nums[i] should go
                int j = nums[i]-1;
                // if j is valid destination index
                if (isValidDestinationIndex(nums, 0, nums.length-1, i, j)) {
                    int temp = nums[j];
                    nums[j] = nums[i];
                    nums[i] = temp;
                } else {
                    // nums[i] at index i is not at right place
                    break;
                }
            }
        }

        int duplicateNumber = -1;
        // missing number won't be where it should be
        // other number - duplicate - should be there
        for (int i=0; i<m; i++) {
            if (nums[i]-1 != i) {
                // missing number = i+1
                duplicateNumber = nums[i];
                break;
            }
        }

        return duplicateNumber;
    }

    public int smallestPositiveMissing(int[] nums) {
        // non-positive numbers are noise - removed them
        // partition this array into non-positive and positive section
        // keep the positive partition in the left
        int p = 0;
        int np = nums.length-1;
        // partition - check left and move left rightwards
        while (p <= np ) { // partition - left and right pointers mustn't cross each other
            // check left pointer
            if (nums[p] < 0) {
                // failure - fix it by swapping
                // negative numbers must be put in the right half
                int temp = nums[p];
                nums[p] = nums[np];
                nums[np] = temp;
                // move right pointer - because only this is certain to have been fixed
                np--;
            }
            // success - move on
            p++;
        }

        // 0 to p-1 - positive
        // p to np - non-positive

        // ideally the positive sections be filled with 1 to p
        // range of the smallest positive missing 1 to p
        for (int i=0; i<p; i++) {
            while(nums[i]-1 != i) {
                int j = nums[i]-1;
                if (isValidDestinationIndex(nums, 0, p-1, i, j)) {
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                } else {
                    break;
                }
            }
        }

        // range of the smallest positive missing 1 to p
        int smallestPositiveMissing = p;
        for (int i=0; i<p; i++) {
            if (nums[i]-1 != i) {
                // missing number = nums[i] = i+1
                smallestPositiveMissing = i+1;
                break;
            }
        }

        return smallestPositiveMissing;
    }

    // TODO
    public int minSwapsCouplesHoldingHands(int[] nums) {
        int minSwaps = -1;
        return minSwaps;
    }
}
