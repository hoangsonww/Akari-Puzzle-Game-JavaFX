package org.example.akarigamejavafx.controller;

import org.example.akarigamejavafx.model.CellType;
import org.example.akarigamejavafx.model.Model;
import org.example.akarigamejavafx.model.Puzzle;

import java.util.Random;

/**
 * This class implements the AlternateMvcController interface. It is responsible for handling user
 * input and updating the model accordingly. The controller is the middleman between the user-facing
 * view and the model. The controller is responsible for updating the model when the user interacts
 * with the view. The controller is also responsible for updating the view when the model changes.
 * The controller is the only class that should be aware of both the model and the view.
 */
public class ControllerImpl implements AlternateMvcController {

  /** The model object that the controller is responsible for updating */
  private final Model model;

  /** Random object used to generate random numbers */
  private final Random random;

  /**
   * Constructor for the ControllerImpl class. The controller requires a model object to be passed
   * in. The model object is the object that the controller is responsible for updating.
   *
   * @param model The model object that the controller is responsible for updating
   */
  public ControllerImpl(Model model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }

    this.model = model;
    this.random = new Random();
  }

  /**
   * This method is called when the user clicks the "Next Puzzle" button. It should update the model
   * to the next puzzle in the library.
   */
  @Override
  public void clickNextPuzzle() {
    int currentIndex = model.getActivePuzzleIndex();
    int librarySize = model.getPuzzleLibrarySize();

    // Rotate to the first puzzle if at the end
    model.setActivePuzzleIndex((currentIndex + 1) % librarySize);
  }

  /**
   * This method is called when the user clicks the "Previous Puzzle" button. It should update the
   * model to the previous puzzle in the library.
   */
  @Override
  public void clickPrevPuzzle() {
    int currentIndex = model.getActivePuzzleIndex();
    int librarySize = model.getPuzzleLibrarySize();

    // Rotate to the last puzzle if at the start
    model.setActivePuzzleIndex((currentIndex - 1 + librarySize) % librarySize);
  }

  /**
   * This method is called when the user clicks the "Random Puzzle" button. It should update the
   * model to a random puzzle in the library.
   */
  @Override
  public void clickRandPuzzle() {
    int librarySize = model.getPuzzleLibrarySize();

    // Only change the puzzle if there is more than one puzzle in the library
    if (librarySize > 1) {
      int randomIndex;

      // Loop until a different puzzle is selected
      do {
        randomIndex = random.nextInt(librarySize);
      } while (randomIndex == model.getActivePuzzleIndex());

      model.setActivePuzzleIndex(randomIndex);
    }
  }

  /**
   * This method is called when the user clicks the "Reset Puzzle" button. It should reset the
   * currently active puzzle in the model.
   */
  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  /**
   * This method is called when the user clicks on a cell in the puzzle grid. It should update the
   * model to reflect the user's interaction with the cell.
   *
   * @param r The row of the cell that was clicked
   * @param c The column of the cell that was clicked
   */
  @Override
  public void clickCell(int r, int c) {
    Puzzle puzzle = model.getActivePuzzle();
    CellType cellType = puzzle.getCellType(r, c);

    // Handle the cell click based on the cell type
    switch (cellType) {
      // Toggle lamps in corridor cells
      case CORRIDOR:
        if (model.isLamp(r, c)) {
          model.removeLamp(r, c);
        } else {
          model.addLamp(r, c);
        }
        break;
      case CLUE:
        // Do nothing for clue or wall cells
        break;
      case WALL:
        // Do nothing for clue or wall cells
        break;
      default:
        throw new IllegalStateException("Unexpected cell type: " + cellType);
    }
  }

  /**
   * This method is called when the user clicks on a cell in the puzzle grid. It should return true
   * if the cell at row r, column c is lit.
   *
   * @param r The row of the cell
   * @param c The column of the cell
   * @return true if the cell is lit, false otherwise
   */
  @Override
  public boolean isLit(int r, int c) {
    return model.isLit(r, c);
  }

  /**
   * This method is called when the user clicks on a cell in the puzzle grid. It should return true
   * if the cell at row r, column c is a lamp.
   *
   * @param r The row of the cell
   * @param c The column of the cell
   * @return true if the cell is a lamp, false otherwise
   */
  @Override
  public boolean isLamp(int r, int c) {
    return model.isLamp(r, c);
  }

  /**
   * This method is called when the user clicks on a cell in the puzzle grid. It should return true
   * if the clue at row r, column c is satisfied by nearby lamps.
   *
   * @param r The row of the cell
   * @param c The column of the cell
   * @return true if the clue is satisfied, false otherwise
   */
  @Override
  public boolean isClueSatisfied(int r, int c) {
    return model.isClueSatisfied(r, c);
  }

  /**
   * This method is called to check if the active puzzle is solved. It should return true if the
   * active puzzle is solved.
   *
   * @return true if the active puzzle is solved, false otherwise
   */
  @Override
  public boolean isSolved() {
    return model.isSolved();
  }

  /**
   * This method is called to get the active puzzle. It should return the active puzzle.
   *
   * @return the active puzzle
   */
  @Override
  public Puzzle getActivePuzzle() {
    return model.getActivePuzzle();
  }

  /**
   * This method is called to check if the lamp at row r, column c is illegal. It should return true
   * if the lamp is illegal.
   *
   * @param r The row of the cell
   * @param c The column of the cell
   * @return true if the lamp is illegal, false otherwise
   */
  @Override
  public boolean isLampIllegal(int r, int c) {
    return model.isLampIllegal(r, c);
  }

  /**
   * This method is called to check if all clue cells are satisfied. It should return true if all
   * clue cells are satisfied.
   *
   * @return true if all clue cells are satisfied, false otherwise
   */
  @Override
  public boolean areCluesSatisfied() {
    return model.areCluesSatisfied();
  }

  /**
   * This method is called to check if all cells are fully lit. It should return true if all cells
   * are fully lit.
   *
   * @return true if all cells are fully lit, false otherwise
   */
  @Override
  public boolean isFullyLit() {
    return model.isFullyLit();
  }

  /**
   * This method is called to check if there are any illegal lamps. It should return true if there
   * are any illegal lamps.
   *
   * @return true if there are any illegal lamps, false otherwise
   */
  @Override
  public boolean hasIllegalLamps() {
    return model.hasIllegalLamps();
  }

  /**
   * Enhancement: Provides a hint to help solve the puzzle. Analyzes the current state and offers
   * guidance.
   *
   * @return A hint string for the user
   */
  @Override
  public String getHint() {
    if (!model.areCluesSatisfied()) {
      // Identify an unsatisfied clue and suggest adding lamps nearby
      Puzzle puzzle = model.getActivePuzzle();
      for (int r = 0; r < puzzle.getHeight(); r++) {
        for (int c = 0; c < puzzle.getWidth(); c++) {
          if (puzzle.getCellType(r, c) == CellType.CLUE && !model.isClueSatisfied(r, c)) {
            return "Check the clue at row "
                + (r + 1)
                + ", column "
                + (c + 1)
                + ". It may need more lamps, or some lamps may need to be removed.";
          }
        }
      }
    } else if (model.hasIllegalLamps()) {
      // Identify an illegal lamp and suggest removing it
      Puzzle puzzle = model.getActivePuzzle();
      for (int r = 0; r < puzzle.getHeight(); r++) {
        for (int c = 0; c < puzzle.getWidth(); c++) {
          if (model.isLamp(r, c) && model.isLampIllegal(r, c)) {
            return "The lamp at row " + (r + 1) + ", column " + (c + 1) + " is illegal. Remove it.";
          }
        }
      }
    } else if (!model.isFullyLit()) {
      // Identify an unlit corridor and suggest placing a lamp
      Puzzle puzzle = model.getActivePuzzle();
      for (int r = 0; r < puzzle.getHeight(); r++) {
        for (int c = 0; c < puzzle.getWidth(); c++) {
          if (puzzle.getCellType(r, c) == CellType.CORRIDOR && !model.isLit(r, c)) {
            return "The corridor at row "
                + (r + 1)
                + ", column "
                + (c + 1)
                + " is unlit. Place a lamp at an appropriate location so it can be lit.";
          }
        }
      }
    }

    return "Everything looks good! You may have solved the puzzle already!";
  }
}
