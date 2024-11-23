package org.example.akarigamejavafx.model;

/** This class implements the Puzzle interface and is used to represent an Akari puzzle */
public class PuzzleImpl implements Puzzle {

  /** The 2D array that represents the puzzle board */
  private final int[][] board;

  /**
   * Constructs a new PuzzleImpl object with the given board. Throws an IllegalArgumentException if
   * the board is null or empty
   *
   * @param board The 2D array that represents the puzzle board
   */
  public PuzzleImpl(int[][] board) {
    if (board == null || board.length == 0 || board[0].length == 0) {
      throw new IllegalArgumentException("Board cannot be null or empty");
    }

    this.board = board;
  }

  /**
   * Getter method for the width of the puzzle (i.e. the number of columns it has)
   *
   * @return The width of the puzzle
   */
  @Override
  public int getWidth() {
    return board[0].length;
  }

  /**
   * Getter method for the height of the puzzle (i.e. the number of rows it has)
   *
   * @return The height of the puzzle
   */
  @Override
  public int getHeight() {
    return board.length;
  }

  /**
   * Getter method for the type of the cell located at row r, column c. Throws an IndexOutOfBounds
   * exception if r or c is out of bounds
   *
   * @param r The row of the cell to check
   * @param c The column of the cell to check
   * @return The type of the cell
   */
  @Override
  public CellType getCellType(int r, int c) {
    validateCell(r, c);
    int value = board[r][c];

    // Determine the cell type based on the value
    if (value >= 0 && value <= 4) {
      return CellType.CLUE;
    } else if (value == 5) {
      return CellType.WALL;
    } else if (value == 6) {
      return CellType.CORRIDOR;
    } else {
      throw new IllegalStateException("Invalid cell value in board");
    }
  }

  /**
   * Getter method for the clue number of the cell located at row r, column c. Throws an
   * IndexOutOfBounds exception if r or c is out of bounds, or an IllegalArgumentException if the
   * cell is not type CLUE
   *
   * @param r The row of the cell to check
   * @param c The column of the cell to check
   * @return The clue number of the cell
   */
  @Override
  public int getClue(int r, int c) {
    validateCell(r, c);

    // Check if the cell is a clue
    if (getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException("Cell is not a clue");
    }

    return board[r][c];
  }

  /**
   * Helper method to validate that the given cell is within the bounds of the board
   *
   * @param r The row of the cell to validate
   * @param c The column of the cell to validate
   */
  private void validateCell(int r, int c) {
    if (r < 0 || r >= getHeight() || c < 0 || c >= getWidth()) {
      throw new IndexOutOfBoundsException("Cell is out of bounds");
    }
  }
}
