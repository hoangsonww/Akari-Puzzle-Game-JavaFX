package org.example.akarigamejavafx.controller;

import org.example.akarigamejavafx.model.Puzzle;

/** This interface defines the Alternate MVC Controller for the Akari game */
public interface AlternateMvcController {

  /** Handles the click action to go to the next puzzle */
  void clickNextPuzzle();

  /** Handles the click action to go to the previous puzzle */
  void clickPrevPuzzle();

  /** Handles the click action to go to a random puzzle */
  void clickRandPuzzle();

  /** Handles the click action to reset the currently active puzzle */
  void clickResetPuzzle();

  /**
   * Handles the click event on the cell at row r, column c
   *
   * @param r The row of the cell that was clicked
   * @param c The column of the cell that was clicked
   */
  void clickCell(int r, int c);

  /**
   * Returns true if the CORRIDOR cell at row r, column c is lit
   *
   * @param r The row of the cell to check
   * @param c The column of the cell to check
   * @return True if the cell is lit
   */
  boolean isLit(int r, int c);

  /**
   * Returns true if the CORRIDOR cell at row r, column c is a lamp
   *
   * @param r The row of the cell to check
   * @param c The column of the cell to check
   * @return True if the cell is a lamp
   */
  boolean isLamp(int r, int c);

  /**
   * Returns true if the CLUE cell at row r, column c is satisfied by nearby lamps
   *
   * @param r The row of the cell to check
   * @param c The column of the cell to check
   * @return True if the clue is satisfied
   */
  boolean isClueSatisfied(int r, int c);

  /**
   * Returns true if the active puzzle is solved
   *
   * @return True if the active puzzle is solved
   */
  boolean isSolved();

  /**
   * Returns true if the lamp at row r, column c is illegal
   *
   * @param r The row of the cell to check
   * @param c The column of the cell to check
   * @return True if the cell contains an illegal lamp
   */
  boolean isLampIllegal(int r, int c);

  /**
   * Getter method for the active puzzle
   *
   * @return The active puzzle
   */
  Puzzle getActivePuzzle();

  /**
   * Gets the hint for the current active puzzle
   *
   * @return The hint for the current active puzzle
   */
  String getHint();

  /**
   * Check if all clues are satisfied
   *
   * @return true if all clues are satisfied
   */
  boolean areCluesSatisfied();

  /**
   * Check if all cells are fully lit
   *
   * @return true if all cells are fully lit
   */
  boolean isFullyLit();

  /**
   * Check if there are any illegal lamps
   *
   * @return true if there are any illegal lamps
   */
  boolean hasIllegalLamps();
}
