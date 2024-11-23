package org.example.akarigamejavafx.model;

/** Represents the model for the Akari game */
public interface Model {

  /**
   * Adds a lamp if one doesn't already exist to the active puzzle in the cell at row r, column c.
   * Throws an IndexOutOfBoundsException if r or c is out of bounds, or an IllegalArgumentException
   * if the cell is not type CORRIDOR
   *
   * @param r The row of the cell to add a lamp to
   * @param c The column of the cell to add a lamp to
   */
  void addLamp(int r, int c);

  /**
   * Removes a lamp if one exists from the active puzzle at the cell at row r, column c. Throws an
   * IndexOutOfBoundsException if r or c is out of bounds, or an IllegalArgumentException if the
   * cell is not type CORRIDOR
   *
   * @param r The row of the cell to remove a lamp from
   * @param c The column of the cell to remove a lamp from
   */
  void removeLamp(int r, int c);

  /**
   * Returns true only if, in the active puzzle, the cell location row r, column c is currently
   * illuminated by a nearby lamp in the same column or row (and which is not blocked by a wall or
   * clue). If the cell itself contains a lamp, this method should also return true. Throws an
   * IndexOutOfBoundsException if r or c is out of bounds, or an IllegalArgumentException if the
   * cell is not type CORRIDOR
   *
   * @param r The row of the cell to check
   * @param c The column of the cell to check
   * @return True if the cell is lit
   */
  boolean isLit(int r, int c);

  /**
   * Returns true only if, in the active puzzle, the cell at row r, column c contains a user-placed
   * lamp. Throws an IndexOutOfBoundsException if r or c is out of bounds, or an
   * IllegalArgumentException if the cell is not type CORRIDOR
   *
   * @param r The row of the cell to check
   * @param c The column of the cell to check
   * @return True if the cell is a lamp
   */
  boolean isLamp(int r, int c);

  /**
   * Returns true only if, in the active puzzle, the cell at row r, column c contains a user-placed
   * lamp that is in direct view of another lamp (i.e. the lamp is illegally placed). Throws an
   * IndexOutOfBoundsException if r or c is out of bounds, or an IllegalArgumentException if the
   * cell does not currently contain a lamp
   *
   * @param r The row of the cell to check
   * @param c The column of the cell to check
   * @return True if the cell contains an illegal lamp
   */
  boolean isLampIllegal(int r, int c);

  /**
   * Getter method for the current active Puzzle instance
   *
   * @return The active Puzzle instance
   */
  Puzzle getActivePuzzle();

  /**
   * Getter method for the active puzzle index
   *
   * @return The active puzzle index
   */
  int getActivePuzzleIndex();

  /**
   * Setter method for the current active Puzzle index. If the passed index is out of bounds, this
   * method should throw an IndexOutOfBoundsException
   *
   * @param index The index of the puzzle to set as active
   */
  void setActivePuzzleIndex(int index);

  /**
   * Getter method for the number of puzzles contained in the internal PuzzleLibrary
   *
   * @return The number of puzzles in the library
   */
  int getPuzzleLibrarySize();

  /** Resets the active puzzle by removing all lamps which have been placed */
  void resetPuzzle();

  /**
   * Returns true if the active puzzle is solved (i.e. every clue is satisfied and every corridor is
   * illuminated)
   *
   * @return True if the active puzzle is solved
   */
  boolean isSolved();

  /**
   * Returns true if the clue located at row r, column c of the active puzzle is satisfied (i.e. has
   * exactly the number of lamps adjacent as is specified by the clue). Throws an
   * IndexOutOfBoundsException if r or c is out of bounds, or an IllegalArgumentException if the
   * cell is not type CLUE
   *
   * @param r The row of the cell to check
   * @param c The column of the cell to check
   * @return True if the clue is satisfied
   */
  boolean isClueSatisfied(int r, int c);

  /**
   * Adds an observer to the model
   *
   * @param observer The observer to add
   */
  void addObserver(ModelObserver observer);

  /**
   * Removes an observer from the model
   *
   * @param observer The observer to remove
   */
  void removeObserver(ModelObserver observer);

  /**
   * Returns true if all clues in the active puzzle are satisfied
   *
   * @return True if all clues are satisfied
   */
  boolean areCluesSatisfied();

  /**
   * Returns true if all cells in the active puzzle are fully lit
   *
   * @return True if all cells are fully lit
   */
  boolean isFullyLit();

  /**
   * Returns true if the active puzzle contains any illegal lamps
   *
   * @return True if the active puzzle contains illegal lamps
   */
  boolean hasIllegalLamps();

  /** Notifies all observers of the model */
  void notifyObservers();
}
