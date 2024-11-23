package org.example.akarigamejavafx.model;

/** Represents a puzzle in the Akari game */
public interface Puzzle {

  /**
   * Getter method for the width of the puzzle (i.e. the number of columns it has)
   *
   * @return The width of the puzzle
   */
  int getWidth();

  /**
   * Getter method for the height of the puzzle (i.e. the number of rows it has)
   *
   * @return The height of the puzzle
   */
  int getHeight();

  /**
   * Getter method for the type of the cell located at row r, column c. Throws an IndexOutOfBounds
   * exception if r or c is out of bounds
   *
   * @param r The row of the cell to check
   * @param c The column of the cell to check
   * @return The type of the cell
   */
  CellType getCellType(int r, int c);

  /**
   * Getter method for the clue number of the cell located at row r, column c. Throws an
   * IndexOutOfBounds exception if r or c is out of bounds, or an IllegalArgumentException if the
   * cell is not type CLUE
   *
   * @param r The row of the cell to check
   * @param c The column of the cell to check
   * @return The clue number of the cell
   */
  int getClue(int r, int c);
}
