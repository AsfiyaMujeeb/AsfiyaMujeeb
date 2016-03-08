package com.assignment.sudoku.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.assignment.sudoku.model.SudokuPuzzle;
import com.assignment.sudoku.service.ISudokuService;

@RestController

public class SudokuController {

	 @Autowired
	 ISudokuService sudokuService;
	 

	 /* **************************************
	 * GET service to read the puzzle question
	 * **************************************/	 
    @RequestMapping(value="/puzzle",headers="Accept=application/json")
    public ResponseEntity<SudokuPuzzle> getPuzzle() {
    	        return sudokuService.getPuzzle(); 
	}
    
    /* **************************************
	 * PUT service to validate the successive 
	 * entries of the user and update the grid
	 * **************************************/
    @RequestMapping(value="/puzzleSol/{val}/{row}/{col}",headers="Accept=application/json",method=RequestMethod.PUT)
    public ResponseEntity<SudokuPuzzle> getSolution(@PathVariable("val") int value,@PathVariable("row") int r,@PathVariable("col")int c){
    	return sudokuService.getSolution(value,r,c);
    	
    	
    	
    }
}