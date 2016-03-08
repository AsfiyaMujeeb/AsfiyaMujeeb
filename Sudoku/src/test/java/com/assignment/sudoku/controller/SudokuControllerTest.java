package com.assignment.sudoku.controller;


import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.assignment.sudoku.model.SudokuPuzzle;
import com.assignment.sudoku.service.ISudokuService;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"file:src/test/resources/test-sudoku-servlet.xml"})
@TestExecutionListeners(inheritListeners = false, listeners
= {DependencyInjectionTestExecutionListener.class,
    DirtiesContextTestExecutionListener.class})
public class SudokuControllerTest {

	private MockMvc mockMVC;
	
	@Autowired
	ISudokuService sudokuServMock;
	
	@Autowired
    private WebApplicationContext webApplicationContext;
	
	@Before
	   public void setUp() {
       
        Mockito.reset(sudokuServMock);
        
        mockMVC = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }
	
	@Test
	public void getPuzzle_default() throws Exception{
		
		int puz[][] = new int[][]{{7,0,0,0,4,0,5,3,0},{0,0,5,0,0,8,0,1,0},{0,0,8,5,0,9,0,4,0},
				{5,3,9,0,6,0,0,0,1},{0,0,0,0,1,0,0,0,5},{8,0,0,7,2,0,9,0,0},
				{9,0,7,4,0,0,0,0,0},{0,0,0,0,5,7,0,0,0},{6,0,0,0,0,0,0,5,0}	};
		SudokuPuzzle puzzle = new SudokuPuzzle();
		puzzle.setCompleted(false);
		puzzle.setPuzzleSol(puz);
		puzzle.setReadOnly(false);
		puzzle.setValid(true);
		
			
		
		when(sudokuServMock.getPuzzle()).thenReturn(
				new ResponseEntity<SudokuPuzzle>(puzzle, HttpStatus.ACCEPTED));

		mockMVC.perform(get("/puzzle")).andExpect(status().isAccepted())
				.andExpect(content().contentType("application/json"))
				.andExpect(jsonPath("$.valid", is(true)))
				.andExpect(jsonPath("$.completed", is(false)))
				.andExpect(jsonPath("$.readOnly", is(false)))
				.andExpect(jsonPath("$puzzleSol[0][0]", is(puz[0][0])))
				.andExpect(jsonPath("$puzzleSol[1][5]", is(puz[1][5])))
				.andExpect(jsonPath("$puzzleSol[8][7]", is(puz[8][7])))
				.andExpect(jsonPath("$puzzleSol[5][5]", is(puz[5][5])));
		
		
	}
	
	@Test
	public void getSolution_Correct_Entry() throws Exception{
		SudokuPuzzle puzzle = new SudokuPuzzle();
		puzzle.setCompleted(false);
		puzzle.getPuzzleSol()[0][1] = 1;
		puzzle.setReadOnly(false);
		puzzle.setValid(true);
		
		when(sudokuServMock.getSolution(1, 0, 1)).thenReturn(new ResponseEntity<SudokuPuzzle>(puzzle, HttpStatus.ACCEPTED));
		
		
		mockMVC.perform(put("/puzzleSol/1/0/1")).andExpect(status().isAccepted())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.valid", is(true)))
		.andExpect(jsonPath("$.completed", is(false)))
		.andExpect(jsonPath("$.readOnly", is(false)))
		.andExpect(jsonPath("$puzzleSol[0][1]", is(1)));
		
		
	}
	
	@Test
	public void getSolution_ReadOnly_Cell() throws Exception{
		
		SudokuPuzzle puzzle = new SudokuPuzzle();
		puzzle.setCompleted(false);
		puzzle.setReadOnly(true);
		puzzle.setValid(false);
		
		when(sudokuServMock.getSolution(1, 0, 0)).thenReturn(new ResponseEntity<SudokuPuzzle>(puzzle, HttpStatus.LOCKED));
		
		mockMVC.perform(put("/puzzleSol/1/0/0")).andExpect(status().isLocked())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.valid", is(false)))
		.andExpect(jsonPath("$.completed", is(false)))
		.andExpect(jsonPath("$.readOnly", is(true)));
		
	}

	
	@Test
	public void getSolution_Incorrect_Entry() throws Exception{
		SudokuPuzzle puzzle = new SudokuPuzzle();
		puzzle.setCompleted(false);
		puzzle.setReadOnly(false);
		puzzle.setValid(false);
		
		when(sudokuServMock.getSolution(4, 0, 2)).thenReturn(new ResponseEntity<SudokuPuzzle>(puzzle, HttpStatus.NOT_ACCEPTABLE));
		
		mockMVC.perform(put("/puzzleSol/4/0/2")).andExpect(status().isNotAcceptable())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.valid", is(false)))
		.andExpect(jsonPath("$.completed", is(false)))
		.andExpect(jsonPath("$.readOnly", is(false)));
		
	}
	
	@Test
	public void getSolution_Completed_lastEntry() throws Exception{
		
		int sol[][] = {				
				{7,9,2,1,4,6,5,3,8},
				{4,6,5,2,3,8,7,1,9},
				{3,1,8,5,7,9,6,4,2},
				{5,3,9,8,6,4,2,7,1},
				{2,7,6,9,1,3,4,8,5},
				{8,4,1,7,2,5,9,6,3},
				{9,5,7,4,8,1,3,2,6},
				{1,2,3,6,5,7,8,9,4},
				{6,8,4,3,9,2,1,5,7}};
		
		SudokuPuzzle puzzle = new SudokuPuzzle();
		puzzle.setCompleted(true);
		puzzle.setReadOnly(false);
		puzzle.setValid(true);
		puzzle.setPuzzleSol(sol);
		
when(sudokuServMock.getSolution(7, 8, 8)).thenReturn(new ResponseEntity<SudokuPuzzle>(puzzle, HttpStatus.OK));
		
		mockMVC.perform(put("/puzzleSol/7/8/8")).andExpect(status().isOk())
		.andExpect(content().contentType("application/json"))
		.andExpect(jsonPath("$.valid", is(true)))
		.andExpect(jsonPath("$.completed", is(true)))
		.andExpect(jsonPath("$.readOnly", is(false)));
	}
}