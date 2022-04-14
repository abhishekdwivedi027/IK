package ds.arrays;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Matrix {

    public static void main(String[] args) {
        Matrix matrix = new Matrix();

        int[][] nums = {{3,0,1,4,2},{5,6,3,2,1},{1,2,0,1,5},{4,1,0,1,7},{1,0,3,0,5}};
        System.out.println("spiral order     " + Arrays.toString(matrix.spiralOrder(nums).toArray()));
        System.out.println("is found     " + matrix.searchMatrix(nums, 7));
        System.out.println("is found     " + matrix.searchMatrix(nums, 8));
        System.out.println("range sum     " + matrix.rangeSum(nums, 2, 1, 4, 3));

        int[][] grid = {{0,0,1,0},{0,0,1,0},{0,0,1,0},{0,0,1,1},{0,1,1,1},{0,1,1,1},{1,1,1,1}};
        System.out.println("max square area     " + matrix.maximalSquare(grid));
    }

    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> nums = new ArrayList<>();
        if (matrix == null || matrix.length == 0) {
            return nums;
        }

        int top = 0;
        int bottom = matrix.length - 1;
        int left = 0;
        int right = matrix[0].length - 1;
        int totalNums = matrix.length * matrix[0].length;

        while (nums.size() < totalNums) {
            for (int col=left; col<=right && nums.size() < totalNums; col++) {
                nums.add(matrix[top][col]);
            }
            top++;

            for (int row=top; row<=bottom && nums.size() < totalNums; row++) {
                nums.add(matrix[row][right]);
            }
            right--;

            for (int col=right; col>=left && nums.size() < totalNums; col--) {
                nums.add(matrix[bottom][col]);
            }
            bottom--;

            for (int row=bottom; row>=top && nums.size() < totalNums; row--) {
                nums.add(matrix[row][left]);
            }
            left++;
        }

        return nums;
    }

    public int[][] generateMatrix(int n) {
        if (n <= 0) {
            return null;
        }

        int[][] matrix = new int[n][n];

        int top = 0;
        int bottom = n - 1;
        int left = 0;
        int right = n - 1;
        int num = 0;
        int totalNums = n * n;

        while (num < totalNums) {
            for (int col=left; col<=right && num < totalNums; col++) {
                matrix[top][col] = ++num;
            }
            top++;

            for (int row=top; row<=bottom && num < totalNums; row++) {
                matrix[row][right] = ++num;
            }
            right--;

            for (int col=right; col>=left && num < totalNums; col--) {
                matrix[bottom][col] = ++num;
            }
            bottom--;

            for (int row=bottom; row>=top && num < totalNums; row--) {
                matrix[row][left] = ++num;
            }
            left++;
        }

        return matrix;
    }

    // all rows and columns in the matrix are sorted
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix == null || matrix.length == 0) {
            return false;
        }

        boolean found = false;
        int m = matrix.length;
        int n = matrix[0].length;

        int top = 0;
        int right = n-1;

        while (top < m && right > -1) {
            int num = matrix[top][right];
            if (num == target) {
                found = true;
                break;
            }

            if (num < target) {
                // target cannot be found in the first row
                top++;
            } else {
                // target cannot be found in the last column
                right--;
            }
        }

        return found;
    }

    public int findCelebrity(int[][] matrix) {
        int celebrity = -1;
        if (matrix == null || matrix.length == 0) {
            return celebrity;
        }

        celebrity = 0;
        for (int other = 1; other<matrix.length; other++) {
            if (matrix[celebrity][other] == 1) {
                // celebrity knows other => no celebrity
                celebrity = other;
            }
            // else - other cannot be celebrity
        }

        // validate celebrity
        for (int i=0; i<matrix.length; i++) {
            if (matrix[i][celebrity] == 0 || (matrix[celebrity][i] == 1 && i != celebrity)) {
                celebrity = -1;
                break;
            }
        }
        return celebrity;
    }

    // sum of all numbers inside a grid
    // prefixSums[i][j] = matrix[i-1][j-1] + prefixSums[i-1][j] + prefixSums[i][j-1] - prefixSums[i-1][j-1];
    // rangeSum(r1, c1, r2, c2) = prefixSums[r2][c2] - prefixSums[r1-1][c2] - prefixSums[r2][c1-1] + prefixSums[r1-1][c1-1];
    public int rangeSum(int[][] matrix, int row1, int col1, int row2, int col2) {
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

    public int maximalSquare(int[][] matrix) {
        if (matrix == null || matrix.length == 0) {
            return 0;
        }

        int maxSquare = 0;

        int m = matrix.length;;
        int n = matrix[0].length;
        int[][] table = new int[m][n];

        // init for row 0 and col 0
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (i == 0 || j == 0) {
                    table[i][j] = matrix[i][j];
                    maxSquare = Math.max(maxSquare, table[i][j]*table[i][j]);
                }
            }
        }

        // for inner cells
        for (int i=1; i<m; i++) {
            for (int j=1; j<n; j++) {
                if (matrix[i][j] == 0) {
                    table[i][j] = 0;
                } else {
                    table[i][j] = 1 + Math.min(table[i-1][j-1], Math.min(table[i-1][j], table[i][j-1]));
                }

                maxSquare = Math.max(maxSquare, table[i][j]*table[i][j]);
            }
        }

        return maxSquare;
    }
}
