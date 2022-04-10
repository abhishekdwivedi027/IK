package algo.streams;

public class StreamTest {

    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9};
        Median median = new Median();
        for (int num: nums) {
            median.addNumber(num);
            System.out.println("median     " + median.getMedian());
        }

        // precondition: majority must exist
        nums = new int[]{5, 5, 5, 1, 5, 2, 5, 3};
        Majority majority = new Majority();
        for (int num: nums) {
            majority.addNumber(num);
            System.out.println("majority     " + majority.getMajority());
        }
    }
}
