package com.assignment.sudoku.service;

import org.springframework.http.ResponseEntity;

import com.assignment.sudoku.model.SudokuPuzzle;


public interface ISudokuService {
	public ResponseEntity<SudokuPuzzle> getPuzzle();
	public ResponseEntity<SudokuPuzzle> getSolution(int val,int row,int col);
}
