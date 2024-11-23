package org.example.akarigamejavafx.controller;

/** This interface defines the Classic MVC Controller for the Akari game */
public interface ClassicMvcController {

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
}
