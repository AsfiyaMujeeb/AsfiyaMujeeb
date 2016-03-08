package com.assignment.sudoku.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.assignment.sudoku.model.SudokuPuzzle;


public class SudokuService implements ISudokuService {
	
	
	SudokuPuzzle sudoPuzzle = new SudokuPuzzle();
	
	/* **************************************
	 * Returns or resets the puzzle question
	 * **************************************/
	public ResponseEntity<SudokuPuzzle> getPuzzle(){
		
		sudoPuzzle.setPuzzleSol(SudokuPuzzle.getPuzzle());
		return new ResponseEntity<SudokuPuzzle>(sudoPuzzle, HttpStatus.ACCEPTED);
		
	}
	
	
	/* ******************************************************
	 * Validates the entered value and updates at the backend
	 * *******************************************************/
	public ResponseEntity<SudokuPuzzle> getSolution(int val,int row,int col){
		
		
		int puzzleSol[][] = sudoPuzzle.getPuzzleSol();
		
		boolean readOnly[][] = SudokuPuzzle.getReadonly();
		
		if(readOnly[row][col]==true){
			sudoPuzzle.setReadOnly(true);
			sudoPuzzle.setValid(false);
			return new ResponseEntity<SudokuPuzzle>(sudoPuzzle,HttpStatus.LOCKED);
		}else{
			sudoPuzzle.setReadOnly(false);
		}
		sudoPuzzle.setCompleted(true);
		puzzleSol[row][col]=0;
		if(checkRow(val, row) && checkCol(val, col) && checkGrid(val, row, col)){
			puzzleSol[row][col]=val;
			sudoPuzzle.setPuzzleSol(puzzleSol);
			sudoPuzzle.setValid(true);
			for(int r=0;r<9;r++){
				for(int c=0;c<9;c++){
					if(puzzleSol[r][c]==0){
						sudoPuzzle.setCompleted(false);
						break;
					}
				}
			}
			
			if(sudoPuzzle.isCompleted())	return new ResponseEntity<SudokuPuzzle>(sudoPuzzle, HttpStatus.OK);
			
			return new ResponseEntity<SudokuPuzzle>(sudoPuzzle, HttpStatus.ACCEPTED);
		}
		
		
		else{
			sudoPuzzle.setValid(false);
			sudoPuzzle.setCompleted(false);
			return new ResponseEntity<SudokuPuzzle>(sudoPuzzle,HttpStatus.NOT_ACCEPTABLE);
		}
			
		
	}
	
	
	/* **************************************
	 * Checks if the value is present in the row
	 * **************************************/
	public boolean checkRow(int val,int r){
		
		for(int c=0;c<9;c++){
			if(sudoPuzzle.getPuzzleSol()[r][c]==val) return false;
		}
		return true;
	}
	
	/* **************************************
	 * Checks if the value is present in the column
	 * **************************************/
	public boolean checkCol(int val,int c){
		
		for(int r=0;r<9;r++){
			if(sudoPuzzle.getPuzzleSol()[r][c]==val) return false;
		}
		return true;
	}
	
	
	/* **************************************
	 * Checks if the value is present in the grid
	 * **************************************/
	public boolean checkGrid(int val,int r,int c){
		for(int g = r-r%3;g<r-r%3+3;g++){
			for(int h= c-c%3;h<c-c%3+3;h++){
				if(sudoPuzzle.getPuzzleSol()[g][h]==val) return false;
			}
		}
		return true;
	}
	
}
