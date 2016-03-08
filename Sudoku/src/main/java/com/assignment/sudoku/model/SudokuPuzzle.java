package com.assignment.sudoku.model;


public class SudokuPuzzle {
	
	private static final int[][] puzzle = {{7,0,0,0,4,0,5,3,0},{0,0,5,0,0,8,0,1,0},{0,0,8,5,0,9,0,4,0},
		{5,3,9,0,6,0,0,0,1},{0,0,0,0,1,0,0,0,5},{8,0,0,7,2,0,9,0,0},
		{9,0,7,4,0,0,0,0,0},{0,0,0,0,5,7,0,0,0},{6,0,0,0,0,0,0,5,0}	};
	
	private static final boolean readOnly[][] = {{true,false,false,false,true,false,true,true,false},
		{false,false,true,false,false,true,false,true,false},
		{false,false,true,true,false,true,false,true,false},
		{true,true,true,false,true,false,false,false,true},
		{false,false,false,false,true,false,false,false,true},
		{true,false,false,true,true,false,true,false,false},
		{true,false,true,true,false,false,false,false,false},
		{false,false,false,false,true,true,false,false,false},
		{true,false,false,false,false,false,false,true,false}	};
	
	private boolean isCompleted = false;
	
	private boolean isReadOnly = false;
	
	private boolean isValid = true;
	
	private int[][] puzzleSol = puzzle;
	
	
	public boolean isValid() {
		return isValid;
	}

	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}

	public  int[][] getPuzzleSol() {
		return puzzleSol;
	}

	public  void setPuzzleSol(int[][] puzzleSol) {
		this.puzzleSol = puzzleSol;
	}

	public boolean isCompleted() {
		return isCompleted;
	}

	public void setCompleted(boolean isCompleted) {
		this.isCompleted = isCompleted;
	}

	public static int[][] getPuzzle() {
		return puzzle;
	}

	public static boolean[][] getReadonly() {
		return readOnly;
	}

	public boolean isReadOnly() {
		return isReadOnly;
	}

	public void setReadOnly(boolean isReadOnly) {
		this.isReadOnly = isReadOnly;
	}
	

}
