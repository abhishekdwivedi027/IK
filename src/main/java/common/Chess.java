package common;

import java.util.ArrayList;
import java.util.List;

public class Chess {
	
	private List<String[]> result = new ArrayList<>();
    
    private String[][] find_all_arrangements(int n) {
        
        List<Integer> rows = new ArrayList<>();
        helper(n, rows, 0);
        return result.toArray(new String[result.size()][n]);
    }
    
    private void helper(int n, List<Integer> board, int column) {
        //backtracking
        if (isConflict(board)) {
            return;
        }
        
        //base case
        if (column == n) {
            String[] solution = getStringArray(board);
            //System.out.println("solution--- " + Arrays.toString( solution ));
            result.add(solution);
            return;
        }
        
        //recursion case
        for (int row = 0; row<n; row++) {
            board.add(column, row);
            helper(n, board, column+1);
            board.remove(column);
        }
    }
    
    private boolean isConflict(List<Integer> board) {
        int columns = board.size();
        if (columns < 2) {
            return false;
        }
        
        int lastCol = columns-1; 
        int lastRow = board.get(lastCol);
        
        for (int col=0; col<lastCol; col++) {
            int row = board.get(col);
            if (row == lastRow) {
                return true;
            }
            
            if (Math.abs(col - lastCol) == Math.abs(row - lastRow)) {
                return true;
            }
        }
        
        return false;
        
    }
    
    private String[] getStringArray(List<Integer> solution) {
    	int n = solution.size();
    	String[] stringArray = new String[n];
    	for (int i=0; i<n; i++) {
    	    int j = solution.get(i);
    		stringArray[i] = getString(j, n);
    	}
    	return stringArray;
    }
    
    private String getString(int i, int n) {
    	StringBuilder sb = new StringBuilder();
    	for (int j=0; j<n; j++) {
    		if (i == j) {
    			sb.append("q");
    		} else {
    			sb.append("-");
    		}
    	}
    	return sb.toString();
    }

}
