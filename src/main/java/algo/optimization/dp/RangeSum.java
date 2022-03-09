package algo.optimization.dp;

public class RangeSum {

    public static void main(String[] args) {
        RangeSum rangeSum = new RangeSum();

        int[][] matrix = {{3,0,1,4,2},{5,6,3,2,1},{1,2,0,1,5},{4,1,0,1,7},{1,0,3,0,5}};
        System.out.println("range sum     " + rangeSum.matrixRangeSum(matrix, 2, 1, 4, 3));
    }

    // repeated calls for range sum - precompute

    // sum of all numbers inside a grid
    public int matrixRangeSum(int[][] matrix, int row1, int col1, int row2, int col2) {
        int m = matrix.length;
        int n = matrix[0].length;
        int[][] prefixSums = new int[m+1][n+1];

        // init prefixSums
        for (int i=0; i<m+1; i++) {
            for (int j=0; j<n+1; j++) {
                if (i == 0 || j == 0) {
                    prefixSums[i][j] = 0;
                }
            }
        }

        for (int i=1; i<m+1; i++) {
            for (int j=1; j<n+1; j++) {
                prefixSums[i][j] = matrix[i-1][j-1] + prefixSums[i-1][j] + prefixSums[i][j-1] - prefixSums[i-1][j-1];
            }
        }

        int r1 = row1+1;
        int r2 = row2+1;
        int c1 = col1+1;
        int c2 = col2+1;
        return prefixSums[r2][c2] - prefixSums[r1-1][c2] - prefixSums[r2][c1-1] + prefixSums[r1-1][c1-1];
    }

    // how many subarrays sum up to the given sum
    public int subarraySum(int[] nums, int sum) {
        return 0;
    }
}
