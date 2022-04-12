package ds.arrays;

import java.util.HashMap;
import java.util.Map;

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

        ex: insertion sort - insert the leftmost after sorting the rest

        cycle sort - like selection sort - instead of finding min number leftmost index we will find right index for the leftmost number
     */

    // nums has o to n-1
    public void cycleSort(int[] nums) {
        int n = nums.length;
        for (int i=0; i<n; i++) {
            // source nums[i] must equal destination i
            while (nums[i] != i) {
                // source is not equal to destination - find the right destination for this source
                int j = nums[i]; // j is the destination index for source nums[i]
                int temp = nums[j];
                nums[j] = nums[i]; // source is placed at the destination
                nums[i] = temp; // i has not been fixed as yet => that's why while loop not if condition
            }
        }
    }

    // nums has o to n - total n+1 nums - array size n - numbers > array length => one num is missing
    public int missingNumber(int[] nums) {
        int n = nums.length;
        for (int i=0; i<n; i++) {
            while (nums[i] != i) {
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
        // missing number => source != destination
        // since source can be any random number, destination "represents" missing number
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

    public int duplicateNumber(int[] nums) {
        int m = nums.length;
        for (int i=0; i<m; i++) {
            // source nums[i]
            // destination i
            while (nums[i] != i) {
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

        int duplicateNumber = -1;
        // duplicate number => source != destination
        // since source cannot be any random number, source is the duplicate number
        for (int i=0; i<m; i++) {
            if (nums[i] != i) {
                duplicateNumber = nums[i];
                break;
            }
        }

        return duplicateNumber;
    }

    //################  HARD   ###############

    public int smallestPositiveMissing(int[] nums) {
        // non-positive numbers are noise - remove them
        // partition this array into non-positive and positive section
        // keep the positive partition in the left
        int p = 0;
        int np = nums.length-1;
        // partition - check left and move left rightwards
        while (p <= np ) { // partition - left and right pointers mustn't cross each other
            // check left pointer
            if (nums[p] <= 0) {
                // failure - fix it by swapping
                // non-positive numbers must be put in the right half
                int temp = nums[p];
                nums[p] = nums[np];
                nums[np] = temp;
                // move right pointer - because only this is certain to have been fixed
                np--;
            } else {
                // success - move on
                p++;
            }
        }

        // 0 to p-1 - positive
        // p to np - non-positive

        // ideally the positive sections be filled with 1 to p
        // range of the smallest positive missing 1 to p
        for (int i=0; i<p; i++) {
            // source nums[i]
            // destination i
            // source = destination + 1 in this case
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
                // missing number = destination + 1
                smallestPositiveMissing = i+1;
                break;
            }
        }

        return smallestPositiveMissing;
    }

    public int minSwapsCouplesHoldingHands(int[] nums) {
        if (nums == null || nums.length < 2 || nums.length%2 == 1) {
            return -1;
        }
        int swaps = 0;
        int n = nums.length/2;

        Map<Integer, Integer> map = new HashMap<>();
        for (int i=0; i<2*n; i++) {
            map.put(nums[i], i);
        }

        for (int sofa=0; sofa<n; sofa++) {
            int leftSeat = 2 * sofa;
            int rightSeat = 2 * sofa + 1;
            int leftSeatPerson = nums[leftSeat];
            int rightSeatPersonExpected = -1;
            if (leftSeatPerson%2 == 0) {
                rightSeatPersonExpected = leftSeatPerson+1;
            } else {
                rightSeatPersonExpected = leftSeatPerson-1;
            }

            while (nums[rightSeat] != rightSeatPersonExpected) {
                // place source nums[rightSeat] to its correct destination
                // placing => swapping
                int rightSeatPersonActual = nums[rightSeat];
                int rightSeatActualPartner = -1;
                if (rightSeatPersonActual%2 == 0) {
                    rightSeatActualPartner = rightSeatPersonActual+1;
                } else {
                    rightSeatActualPartner = rightSeatPersonActual-1;
                }
                int rightSeatActualPartnerSeat = map.get(rightSeatActualPartner);
                int rightSeatActualDestination = -1;
                if (rightSeatActualPartnerSeat%2 == 0) {
                    rightSeatActualDestination = rightSeatActualPartnerSeat+1;
                } else {
                    rightSeatActualDestination = rightSeatActualPartnerSeat-1;
                }
                int stranger = nums[rightSeatActualDestination];
                nums[rightSeatActualDestination] = rightSeatPersonActual;
                nums[rightSeat] = stranger;
                swaps++;
            }
        }

        return swaps;
    }
}
