package algo.optimization.path;

import java.util.Arrays;

public class Grid {

    public int pathCount(int m, int n) {
        // recurrence equation
        // paths[m][n] = paths[m-1][n] + paths[m][n-1]

        int[][] paths = new int[m][n];
        for (int i=0; i<m; i++) {
            for (int j=0; j<n; j++) {
                if (i ==0 || j == 0) {
                    paths[i][j] = 1;
                }
            }
        }
        paths[0][0] = 0;

        for (int i=1; i<m; i++) {
            for (int j=1; j<n; j++) {
                paths[i][j] = paths[i-1][j] + paths[i][j-1];
            }
        }

        return paths[m-1][n-1];
    }

    public int blockedPathCount(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        if (grid[m-1][n-1] == -1) {
            return -1;
        }

        int[][] paths = new int[m][n];
        for (int i=0; i<m; i++) {
            Arrays.fill(paths[i], 0);
        }

        for (int i=0; i<m; i++) {
            if (grid[i][0] == -1) {
                break;
            }
            paths[i][0] = 1;
        }
        for (int j=0; j<n; j++) {
            if (grid[0][j] == -1) {
                break;
            }
            paths[0][j] = 1;
        }
        paths[0][0] = 0;

        for (int i=1; i<m; i++) {
            for (int j=1; j<n; j++) {
                if (grid[i][j] == -1) {
                    continue;
                }
                paths[i][j] = paths[i-1][j] + paths[i][j-1];
            }
        }

        return paths[m-1][n-1];
    }
}
