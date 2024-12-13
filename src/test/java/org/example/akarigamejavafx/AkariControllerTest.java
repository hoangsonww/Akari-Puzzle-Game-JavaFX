package org.example.akarigamejavafx;

import org.example.akarigamejavafx.controller.ControllerImpl;
import org.example.akarigamejavafx.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class contains JUnit tests for the ControllerImpl class. It ensures that the controller's
 * methods correctly interact with the model.
 */
public class AkariControllerTest {

  /** Default constructor */
  public AkariControllerTest() {}

  /** The sample puzzle board */
  private int[][] sampleBoard;

  /** The puzzle object */
  private Puzzle puzzle;

  /** The puzzle library object */
  private PuzzleLibrary puzzleLibrary;

  /** The model object */
  private Model model;

  /** The controller object */
  private ControllerImpl controller;

  /**
   * Sets up the sample puzzle board and initializes the Puzzle, PuzzleLibrary, Model, and
   * Controller objects for testing.
   */
  @Before
  public void setUp() {
    // Sample puzzle board
    sampleBoard =
        new int[][] {
          {5, 6, 6, 6, 0},
          {6, 5, 6, 1, 6},
          {6, 6, 6, 6, 6},
          {6, 2, 5, 6, 6},
          {6, 6, 6, 6, 5}
        };

    // Initialize Puzzle, PuzzleLibrary, Model, and Controller
    puzzle = new PuzzleImpl(sampleBoard);
    puzzleLibrary = new PuzzleLibraryImpl();
    puzzleLibrary.addPuzzle(puzzle);
    model = new ModelImpl(puzzleLibrary);
    controller = new ControllerImpl(model);
  }

  /** Test the clickNextPuzzle method */
  @Test
  public void testClickNextPuzzle() {
    // Verify initial active puzzle index
    assertEquals(0, model.getActivePuzzleIndex());

    // Add a second puzzle and test cycling
    int[][] newBoard = {{6, 6, 6}, {6, 5, 6}, {6, 6, 6}};
    puzzleLibrary.addPuzzle(new PuzzleImpl(newBoard));

    controller.clickNextPuzzle();
    assertEquals(1, model.getActivePuzzleIndex());

    controller.clickNextPuzzle();
    assertEquals(0, model.getActivePuzzleIndex());
  }

  /** Test the clickPrevPuzzle method */
  @Test
  public void testClickPrevPuzzle() {
    // Verify initial active puzzle index
    assertEquals(0, model.getActivePuzzleIndex());

    // Add a second puzzle and test cycling backwards
    int[][] newBoard = {{6, 6, 6}, {6, 5, 6}, {6, 6, 6}};
    puzzleLibrary.addPuzzle(new PuzzleImpl(newBoard));

    controller.clickPrevPuzzle();
    assertEquals(1, model.getActivePuzzleIndex());

    controller.clickPrevPuzzle();
    assertEquals(0, model.getActivePuzzleIndex());
  }

  /** Test the clickRandPuzzle method */
  @Test
  public void testClickRandPuzzle() {
    // Add additional puzzles to allow randomness
    int[][] board2 = {{6, 6, 5}, {5, 6, 6}, {6, 6, 6}};
    int[][] board3 = {{5, 6, 6}, {6, 6, 6}, {6, 5, 6}};
    puzzleLibrary.addPuzzle(new PuzzleImpl(board2));
    puzzleLibrary.addPuzzle(new PuzzleImpl(board3));

    controller.clickRandPuzzle();
    assertTrue(
        model.getActivePuzzleIndex() >= 0 && model.getActivePuzzleIndex() < puzzleLibrary.size());
  }

  /** Test the clickResetPuzzle method */
  @Test
  public void testClickResetPuzzle() {
    // Add a lamp and verify reset functionality
    model.addLamp(0, 1);
    assertTrue(model.isLamp(0, 1));

    controller.clickResetPuzzle();
    assertFalse(model.isLamp(0, 1));
  }

  /** Test the clickCell method */
  @Test
  public void testClickCellToggleLamp() {
    // Verify toggling lamps in corridor cells
    controller.clickCell(0, 1);
    assertTrue(model.isLamp(0, 1));

    controller.clickCell(0, 1);
    assertFalse(model.isLamp(0, 1));
  }

  /** Test the clickCell method */
  @Test
  public void testClickCellNoActionOnWallOrClue() {
    // Ensure no action is taken on wall or clue cells
    controller.clickCell(0, 0); // Wall cell
    assertFalse(model.isLamp(0, 0));

    controller.clickCell(0, 4); // Clue cell
    assertFalse(model.isLamp(0, 4));
  }

  /** Test the isLit method */
  @Test
  public void testIsLit() {
    // Add a lamp and verify the lit status of cells
    model.addLamp(0, 1);
    assertTrue(controller.isLit(0, 1));
    assertTrue(controller.isLit(0, 2));
    assertFalse(controller.isLit(1, 0)); // Blocked by wall
  }

  /** Test the isLamp method */
  @Test
  public void testIsLamp() {
    controller.clickCell(0, 1);
    assertTrue(controller.isLamp(0, 1));

    controller.clickCell(0, 1);
    assertFalse(controller.isLamp(0, 1));
  }

  /** Test the isClueSatisfied method */
  @Test
  public void testIsClueSatisfied() {
    model.addLamp(0, 3);
    assertTrue(controller.isClueSatisfied(1, 3));

    model.addLamp(1, 2);
    assertFalse(controller.isClueSatisfied(1, 3));

    model.removeLamp(1, 2);
    assertTrue(controller.isClueSatisfied(1, 3));
  }

  /** Test the isSolved method */
  @Test
  public void testIsSolved() {
    // Solve the puzzle and verify
    model.addLamp(0, 1);
    model.addLamp(1, 0);
    model.addLamp(1, 2);
    model.addLamp(2, 1);
    model.addLamp(3, 4);
    model.addLamp(4, 1);

    assertTrue(controller.isSolved());

    model.addLamp(0, 3); // Add an illegal lamp
    assertFalse(controller.isSolved());
  }

  /** Test the getHint method */
  @Test
  public void testGetHint() {
    // Test hint functionality when clues are not satisfied
    String hint = controller.getHint();
    assertNotNull(hint);
    assertFalse(hint.isEmpty());

    // Solve the puzzle and verify hint changes
    model.addLamp(0, 1);
    model.addLamp(1, 0);
    model.addLamp(1, 2);
    model.addLamp(2, 1);
    model.addLamp(3, 4);
    model.addLamp(4, 1);

    assertEquals(
        "Everything looks good! You may have solved the puzzle already!", controller.getHint());
  }
}
