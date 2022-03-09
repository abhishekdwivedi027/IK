package common;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Sudoku {
	
	public ArrayList<ArrayList<Integer>> solve_sudoku_puzzle(ArrayList<ArrayList<Integer>> board) {
        solve(board);
        return board;
    }
	
	private boolean solve(ArrayList<ArrayList<Integer>> board) {
        for (int row = 0; row < board.size(); row++) {
            for (int column = 0; column < board.size(); column++) {
                if (board.get(row).get(column) == 0) {
                    for (int k = 1; k <= 9; k++) {
                        board.get(row).set(column, k);
                        //check if this is valid
                        //if yes - solve for the next cell
                        //else - undo this and try another number
                        if (isValid(board, row, column) && solve(board)) {
                            return true;
                        }
                        board.get(row).set(column, 0);
                    }
                    return false;
                }
            }
        }
        return true;
    }
	
	private boolean isValid(ArrayList<ArrayList<Integer>> board, int row, int col) {
        
        //check row level conflict - duplication in a row
        if (isDuplicate(board.get(row))) {
            return false;
        }
        //System.out.println("no row conflict");
        
        //check columns level conflict - duplication in a column
        List<Integer> column = new ArrayList<>();
        for (int i=0; i<=8; i++) {
            column.add(board.get(i).get(col));
        }
        if (isDuplicate(column)) {
            return false;
        }
        //System.out.println("no column conflict");
        
        //check section level conflict - duplication in a section
        int rowStart = row/3 * 3;
        int rowEnd = rowStart + 3;
        int colStart = col/3 * 3;
        int colEnd = colStart + 3;
        List<Integer> section = new ArrayList<>();
        for (int i=rowStart; i<rowEnd; i++) {
            for (int j=colStart; j<colEnd; j++) {
                section.add(board.get(i).get(j));
            }
        } 
        if (isDuplicate(section)) {
            return false;
        }
        //System.out.println("no section conflict");

        return true;
    }
    
    private boolean isDuplicate(List<Integer> nums) {
        Set<Integer> set = new HashSet<>();
        for (int i=0; i<nums.size(); i++) {
            Integer num = nums.get(i);
            if (num != 0 && !set.add(num)) {
                return true;
            }
        }
        
        return false;
    }


}
