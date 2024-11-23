package org.example.akarigamejavafx;

import org.example.akarigamejavafx.model.Model;
import org.example.akarigamejavafx.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * This class contains JUnit tests for the Akari puzzle model implementation. The tests cover the
 * Puzzle and Model classes - which are the main logic components of the Akari puzzle game.
 */
public class AkariModelTest {

  /** Default constructor */
  public AkariModelTest() {}

  /** The sample puzzle board */
  private int[][] sampleBoard;

  /** The puzzle object */
  private Puzzle puzzle;

  /** The puzzle library object */
  private PuzzleLibrary puzzleLibrary;

  /** The model object */
  private Model model;

  /** Sets up the sample puzzle board and initializes the Puzzle and Model objects for testing. */
  @Before
  public void setUp() {
    // Sample puzzle board:
    sampleBoard =
        new int[][] {
          {5, 6, 6, 6, 0},
          {6, 5, 6, 1, 6},
          {6, 6, 6, 6, 6},
          {6, 2, 5, 6, 6},
          {6, 6, 6, 6, 5},
        };

    // Initialize Puzzle and Model
    puzzle = new PuzzleImpl(sampleBoard);
    puzzleLibrary = new PuzzleLibraryImpl();
    puzzleLibrary.addPuzzle(puzzle);
    model = new ModelImpl(puzzleLibrary);
  }

  /** Test puzzle dimensions */
  @Test
  public void testPuzzleDimensions() {
    assertEquals(5, puzzle.getWidth());
    assertEquals(5, puzzle.getHeight());
  }

  /** Test puzzle cell types */
  @Test
  public void testPuzzleCellTypes() {
    assertEquals(CellType.WALL, puzzle.getCellType(0, 0));
    assertEquals(CellType.CORRIDOR, puzzle.getCellType(0, 1));
    assertEquals(CellType.CLUE, puzzle.getCellType(0, 4));
  }

  /** Test puzzle out of bounds case */
  @Test(expected = IndexOutOfBoundsException.class)
  public void testPuzzleOutOfBounds() {
    puzzle.getCellType(10, 10);
  }

  /** Test puzzle clues */
  @Test
  public void testPuzzleClues() {
    assertEquals(0, puzzle.getClue(0, 4));
    assertEquals(1, puzzle.getClue(1, 3));
    assertEquals(2, puzzle.getClue(3, 1));
  }

  /** Test invalid clue access for non-clue cells */
  @Test(expected = IllegalArgumentException.class)
  public void testInvalidClueAccess() {
    puzzle.getClue(0, 1); // Not a clue cell
  }

  /** Test adding and removing lamps */
  @Test
  public void testAddAndRemoveLamp() {
    assertFalse(model.isLamp(0, 1));
    model.addLamp(0, 1);
    assertTrue(model.isLamp(0, 1));
    model.removeLamp(0, 1);
    assertFalse(model.isLamp(0, 1));
  }

  /** Test adding lamp to a non-corridor cell */
  @Test(expected = IllegalArgumentException.class)
  public void testAddLampToNonCorridor() {
    model.addLamp(0, 0); // Wall cell
  }

  /** Test removing lamp from a non-corridor cell */
  @Test(expected = IllegalArgumentException.class)
  public void testRemoveLampFromNonCorridor() {
    model.removeLamp(0, 0); // Wall cell
  }

  /** Test the isLit method */
  @Test
  public void testIsLit() {
    // Adding lamp at (0,1) to light up the corridor
    model.addLamp(0, 1);

    // Cell with the lamp should be lit
    assertTrue(model.isLit(0, 1));

    // Adjacent corridor cells should also be lit
    assertTrue(model.isLit(0, 2));

    // Cells in the same row but blocked by a wall should not be lit
    assertFalse(model.isLit(1, 0));
  }

  /** Test if a cell is lit in a non-corridor cell - should throw an exception */
  @Test(expected = IllegalArgumentException.class)
  public void testIsLitNonCorridor() {
    model.isLit(0, 0);
  }

  /** Test the isLampIllegal method */
  @Test
  public void testIsLampIllegal() {
    model.addLamp(0, 1);
    model.addLamp(0, 2);
    // Lamp at (0,1) is illegal because it is in direct view of another lamp at (0,2)
    assertTrue(model.isLampIllegal(0, 1));
  }

  /** Test isClueSatisfied method */
  @Test
  public void testClueSatisfied() {
    model.addLamp(0, 3);
    assertTrue(model.isClueSatisfied(1, 3)); // Clue is satisfied

    model.addLamp(1, 2);
    assertFalse(model.isClueSatisfied(1, 3)); // Clue now exceeds lamp requirement

    model.removeLamp(1, 2);
    assertTrue(model.isClueSatisfied(1, 3)); // Back to satisfied
  }

  /** Test resetPuzzle method */
  @Test
  public void testResetPuzzle() {
    model.addLamp(0, 1);
    model.addLamp(2, 2);
    model.resetPuzzle();
    assertFalse(model.isLamp(0, 1));
    assertFalse(model.isLamp(2, 2));
  }

  /** Test isSolved method */
  @Test
  public void testIsSolved() {
    // Clear any existing lamps
    model.resetPuzzle();

    // Puzzle illustration:
    // [Wall, Corridor, Corridor, Corridor, Clue (0)]
    // [Corridor, Wall, Corridor, Clue (1), Corridor]
    // [Corridor, Corridor, Corridor, Corridor, Corridor]
    // [Corridor, Clue (2), Corridor, Corridor, Corridor]
    // [Corridor, Corridor, Corridor, Corridor, Clue (5)]

    // Try solving the puzzle with valid lamps
    model.addLamp(0, 1);
    model.addLamp(1, 0);
    model.addLamp(1, 2);
    model.addLamp(2, 1);
    model.addLamp(3, 4);
    model.addLamp(4, 1);

    System.out.println("Testing with valid lamps...");
    assertTrue(model.isSolved()); // Puzzle should now be solved

    // Add an illegal lamp
    model.addLamp(0, 3);
    System.out.println("Testing with an illegal lamp added...");
    assertFalse(model.isSolved()); // Puzzle should no longer be solved

    // Remove the illegal lamp
    model.removeLamp(0, 3);
    System.out.println("Testing after removing the illegal lamp...");
    assertTrue(model.isSolved()); // Puzzle should be solved again
  }

  /**
   * Test the active puzzle management functionality in the model. This test ensures that: 1. The
   * initial active puzzle index is correctly set to 0. 2. The model returns the correct active
   * puzzle based on the index. 3. Adding a new puzzle to the library and switching to it updates
   * the active puzzle index and retrieves the correct puzzle dimensions.
   */
  @Test
  public void testActivePuzzleManagement() {
    // Verify the initial active puzzle index is 0
    assertEquals(0, model.getActivePuzzleIndex());

    // Verify the current active puzzle is the one added during initialization
    assertEquals(puzzle, model.getActivePuzzle());

    // Add a new puzzle to the library
    int[][] newBoard = {
      {6, 6, 5},
      {5, 6, 6},
      {6, 6, 6},
    };
    puzzleLibrary.addPuzzle(new PuzzleImpl(newBoard)); // Add a new 3x3 puzzle to the library

    // Set the active puzzle index to the newly added puzzle
    model.setActivePuzzleIndex(1);

    // Verify the new active puzzle index is updated correctly
    assertEquals(1, model.getActivePuzzleIndex());

    // Verify the dimensions of the new active puzzle
    assertEquals(3, model.getActivePuzzle().getWidth());
    assertEquals(3, model.getActivePuzzle().getHeight());
  }

  /**
   * Test the behavior when setting an invalid active puzzle index. This test ensures that
   * attempting to set the active puzzle index to an out-of-bounds value throws an
   * IndexOutOfBoundsException.
   */
  @Test(expected = IndexOutOfBoundsException.class)
  public void testInvalidActivePuzzleIndex() {
    // Attempt to set the active puzzle index to an invalid value (out of bounds)
    model.setActivePuzzleIndex(100); // This should throw an IndexOutOfBoundsException
  }
}
