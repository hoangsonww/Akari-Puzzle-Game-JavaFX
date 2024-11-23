package org.example.akarigamejavafx.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** This class implements the Model interface and represents the state of the Akari game */
public class ModelImpl implements Model {

  /** The puzzle library that contains all the puzzles */
  private final PuzzleLibrary puzzleLibrary;

  /** The index of the active puzzle in the library */
  private int activePuzzleIndex;

  /** The set of lamps that have been placed on the active puzzle */
  private final Set<String> lamps;

  /** The list of observers that are listening for changes to the model */
  private final List<ModelObserver> observers;

  /**
   * Constructor for the ModelImpl class. The model requires a puzzle library to be passed in. The
   * puzzle library is the object that contains all the puzzles that the model can use.
   *
   * @param library The puzzle library that contains all the puzzles
   */
  public ModelImpl(PuzzleLibrary library) {
    if (library == null || library.size() == 0) {
      throw new IllegalArgumentException("Puzzle library cannot be null or empty");
    }

    this.puzzleLibrary = library;
    this.activePuzzleIndex = 0;
    this.lamps = new HashSet<>();
    this.observers = new ArrayList<>();
  }

  /** Notifies all observers that the model has been updated */
  @Override
  public void notifyObservers() {
    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }

  /**
   * Adds a lamp to the specified cell in the active puzzle. Throws an IllegalArgumentException if
   * the cell is not of type CORRIDOR
   *
   * @param r The row of the cell to add a lamp to
   * @param c The column of the cell to add a lamp to
   */
  @Override
  public void addLamp(int r, int c) {
    Puzzle puzzle = getActivePuzzle();
    validateCell(puzzle, r, c);

    // Check if the cell is a CORRIDOR cell
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("Lamps can only be added to CORRIDOR cells");
    }

    lamps.add(r + "," + c);
    notifyObservers();
  }

  /**
   * Removes a lamp from the specified cell in the active puzzle. Throws an IllegalArgumentException
   * if the cell is not of type CORRIDOR
   *
   * @param r The row of the cell to remove a lamp from
   * @param c The column of the cell to remove a lamp from
   */
  @Override
  public void removeLamp(int r, int c) {
    Puzzle puzzle = getActivePuzzle();
    validateCell(puzzle, r, c);

    // Check if the cell is a CORRIDOR cell
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("Lamps can only be removed from CORRIDOR cells");
    }

    lamps.remove(r + "," + c);
    notifyObservers();
  }

  /**
   * Returns true if the cell at row r, column c is lit. Throws an IllegalArgumentException if the
   * cell is not of type CORRIDOR
   *
   * @param r The row of the cell to check
   * @param c The column of the cell to check
   * @return True if the cell is lit, false otherwise
   */
  @Override
  public boolean isLit(int r, int c) {
    Puzzle puzzle = getActivePuzzle();
    validateCell(puzzle, r, c);

    // Check if the cell is a CORRIDOR cell - only CORRIDOR cells can be lit
    if (puzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException("Can only check lighting for CORRIDOR cells");
    }

    // If the cell contains a lamp, it is lit automatically
    if (isLamp(r, c)) {
      return true;
    }

    // Check for lamps in the same row (above the current cell)
    for (int i = r - 1; i >= 0; i--) {
      // Stop if a wall or clue blocks the view
      if (isWallOrClue(puzzle, i, c)) {
        break;
      }
      // If a lamp is found in this direction, return true (indicating a conflict)
      if (isLamp(i, c)) {
        return true;
      }
    }

    // Check for lamps in the same row (below the current cell)
    for (int i = r + 1; i < puzzle.getHeight(); i++) {
      // Stop if a wall or clue blocks the view
      if (isWallOrClue(puzzle, i, c)) {
        break;
      }
      // If a lamp is found in this direction, return true (indicating a conflict)
      if (isLamp(i, c)) {
        return true;
      }
    }

    // Check for lamps in the same column (to the left of the current cell)
    for (int j = c - 1; j >= 0; j--) {
      // Stop if a wall or clue blocks the view
      if (isWallOrClue(puzzle, r, j)) {
        break;
      }
      // If a lamp is found in this direction, return true (indicating a conflict)
      if (isLamp(r, j)) {
        return true;
      }
    }

    // Check for lamps in the same column (to the right of the current cell)
    for (int j = c + 1; j < puzzle.getWidth(); j++) {
      // Stop if a wall or clue blocks the view
      if (isWallOrClue(puzzle, r, j)) {
        break;
      }
      // If a lamp is found in this direction, return true (indicating a conflict)
      if (isLamp(r, j)) {
        return true;
      }
    }

    // If no lamps are found in any direction, return false
    return false;
  }

  /**
   * Returns true if the cell at row r, column c is a lamp
   *
   * @param r The row of the cell to check
   * @param c The column of the cell to check
   * @return True if the cell is a lamp, false otherwise
   */
  @Override
  public boolean isLamp(int r, int c) {
    return lamps.contains(r + "," + c);
  }

  /**
   * Returns true if the cell at row r, column c contains a lamp that is in direct view of another
   * lamp (i.e. the lamp is illegally placed). Throws an IllegalArgumentException if the cell does
   * not contain a lamp
   *
   * @param r The row of the cell to check
   * @param c The column of the cell to check
   * @return True if the cell contains an illegal lamp, false otherwise
   */
  @Override
  public boolean isLampIllegal(int r, int c) {
    // Check if the cell contains a lamp - if not, throw an exception
    if (!isLamp(r, c)) {
      throw new IllegalArgumentException("Cell does not contain a lamp");
    }

    // Check for other lamps in the same row or column
    // Loop to check upward in the same column (cells above the current cell)
    for (int i = r - 1; i >= 0; i--) {
      // If the loop encounters a wall or clue, stop checking further
      if (isWallOrClue(getActivePuzzle(), i, c)) {
        break;
      }
      // If a lamp is found in this direction, return true (indicating a conflict)
      if (isLamp(i, c)) {
        return true; // Found another lamp
      }
    }

    // Loop to check downward in the same column (cells below the current cell)
    for (int i = r + 1; i < getActivePuzzle().getHeight(); i++) {
      // If the loop encounters a wall or clue, stop checking further
      if (isWallOrClue(getActivePuzzle(), i, c)) {
        break;
      }
      // If a lamp is found in this direction, return true (indicating a conflict)
      if (isLamp(i, c)) {
        return true; // Found another lamp
      }
    }

    // Loop to check leftward in the same row (cells to the left of the current cell)
    for (int j = c - 1; j >= 0; j--) {
      // If the loop encounters a wall or clue, stop checking further
      if (isWallOrClue(getActivePuzzle(), r, j)) {
        break;
      }
      // If a lamp is found in this direction, return true (indicating a conflict)
      if (isLamp(r, j)) {
        return true; // Found another lamp
      }
    }

    // Loop to check rightward in the same row (cells to the right of the current cell)
    for (int j = c + 1; j < getActivePuzzle().getWidth(); j++) {
      // If the loop encounters a wall or clue, stop checking further
      if (isWallOrClue(getActivePuzzle(), r, j)) {
        break;
      }
      // If a lamp is found in this direction, return true (indicating a conflict)
      if (isLamp(r, j)) {
        return true; // Found another lamp
      }
    }

    // If no lamps are found in any direction, return false
    return false;
  }

  /**
   * Returns the active puzzle instance
   *
   * @return The active puzzle instance
   */
  @Override
  public Puzzle getActivePuzzle() {
    return puzzleLibrary.getPuzzle(activePuzzleIndex);
  }

  /**
   * Returns the index of the active puzzle
   *
   * @return The active puzzle index
   */
  @Override
  public int getActivePuzzleIndex() {
    return activePuzzleIndex;
  }

  /**
   * Sets the active puzzle index to the specified value. Throws an IndexOutOfBoundsException if the
   * index is out of bounds
   *
   * @param index The index of the puzzle to set as active
   */
  @Override
  public void setActivePuzzleIndex(int index) {
    if (index < 0 || index >= puzzleLibrary.size()) {
      throw new IndexOutOfBoundsException("Invalid puzzle index");
    }

    activePuzzleIndex = index;
    lamps.clear();
    notifyObservers();
  }

  /**
   * Gets the puzzle library's size
   *
   * @return The size of the puzzle library
   */
  @Override
  public int getPuzzleLibrarySize() {
    return puzzleLibrary.size();
  }

  /** Resets the active puzzle by removing all lamps that have been placed */
  @Override
  public void resetPuzzle() {
    lamps.clear();
    notifyObservers();
  }

  /**
   * Returns true if the active puzzle is solved (i.e. every clue is satisfied and every corridor is
   * illuminated)
   *
   * @return True if the active puzzle is solved
   */
  @Override
  public boolean isSolved() {
    Puzzle puzzle = getActivePuzzle();

    // Loop to check the rows
    for (int row = 0; row < puzzle.getHeight(); row++) {
      // Loop to check the columns
      for (int col = 0; col < puzzle.getWidth(); col++) {
        CellType type = puzzle.getCellType(row, col);

        // Check if all corridors are lit
        if (type == CellType.CORRIDOR && !isLit(row, col)) {
          return false;
        }

        // Check if all clues are satisfied
        if (type == CellType.CLUE && !isClueSatisfied(row, col)) {
          return false;
        }

        // Check for any illegal lamps
        if (type == CellType.CORRIDOR && isLamp(row, col) && isLampIllegal(row, col)) {
          return false;
        }
      }
    }

    // If all checks pass, the puzzle is solved
    return true;
  }

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
  @Override
  public boolean isClueSatisfied(int r, int c) {
    Puzzle puzzle = getActivePuzzle();
    validateCell(puzzle, r, c);

    // Check if the cell is a CLUE cell
    if (puzzle.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException("Cell is not a CLUE");
    }

    // Get the clue number for the cell
    int clue = puzzle.getClue(r, c);

    // Initialize a counter to keep track of the number of lamps adjacent to the cell
    int lampCount = 0;

    // Check the cell directly above the current cell
    if (r > 0 && isLamp(r - 1, c)) {
      lampCount++; // Increment the counter if there is a lamp above
    }

    // Check the cell directly below the current cell
    if (r < puzzle.getHeight() - 1 && isLamp(r + 1, c)) {
      lampCount++; // Increment the counter if there is a lamp below
    }

    // Check the cell directly to the left of the current cell
    if (c > 0 && isLamp(r, c - 1)) {
      lampCount++; // Increment the counter if there is a lamp to the left
    }

    // Check the cell directly to the right of the current cell
    if (c < puzzle.getWidth() - 1 && isLamp(r, c + 1)) {
      lampCount++; // Increment the counter if there is a lamp to the right
    }

    // Return true if the number of lamps adjacent to the cell matches the clue number
    return lampCount == clue;
  }

  /**
   * Adds an observer to the list of observers that are listening for changes to the model
   *
   * @param observer The observer to add
   */
  @Override
  public void addObserver(ModelObserver observer) {
    observers.add(observer);
  }

  /**
   * Removes an observer from the list of observers that are listening for changes to the model
   *
   * @param observer The observer to remove
   */
  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }

  /**
   * Helper method to validate that a cell is within the bounds of the puzzle
   *
   * @param puzzle The puzzle to validate the cell against
   * @param r The row of the cell
   * @param c The column of the cell
   */
  private void validateCell(Puzzle puzzle, int r, int c) {
    // Check if the cell is out of bounds (i.e. out of the puzzle's dimensions)
    if (r < 0 || r >= puzzle.getHeight() || c < 0 || c >= puzzle.getWidth()) {
      throw new IndexOutOfBoundsException("Cell is out of bounds");
    }
  }

  /**
   * Helper method to check if a cell is a wall or a clue
   *
   * @param puzzle The puzzle to check the cell against
   * @param r The row of the cell
   * @param c The column of the cell
   * @return True if the cell is a wall or a clue, false otherwise
   */
  private boolean isWallOrClue(Puzzle puzzle, int r, int c) {
    CellType type = puzzle.getCellType(r, c);
    return type == CellType.WALL || type == CellType.CLUE;
  }

  /**
   * Returns true if all clues in the active puzzle are satisfied
   *
   * @return True if all clues are satisfied
   */
  @Override
  public boolean areCluesSatisfied() {
    Puzzle puzzle = getActivePuzzle();

    // Loop to check the rows
    for (int r = 0; r < puzzle.getHeight(); r++) {
      // Loop to check the columns
      for (int c = 0; c < puzzle.getWidth(); c++) {
        // Check if the cell is a clue and if the clue is satisfied
        if (puzzle.getCellType(r, c) == CellType.CLUE && !isClueSatisfied(r, c)) {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * Returns true if all cells in the active puzzle are fully lit
   *
   * @return True if all cells are fully lit
   */
  @Override
  public boolean isFullyLit() {
    Puzzle puzzle = getActivePuzzle();

    // Loop to check the rows
    for (int r = 0; r < puzzle.getHeight(); r++) {
      // Loop to check the columns
      for (int c = 0; c < puzzle.getWidth(); c++) {
        // Check if the cell is a corridor and if the cell is lit
        if (puzzle.getCellType(r, c) == CellType.CORRIDOR && !isLit(r, c)) {
          return false;
        }
      }
    }

    return true;
  }

  /**
   * Returns true if the active puzzle contains any illegal lamps
   *
   * @return True if the active puzzle contains illegal lamps
   */
  @Override
  public boolean hasIllegalLamps() {
    // Loop through all the lamps one by one and check if any of them are illegal
    for (String lamp : lamps) {
      // Split the lamp string into row and column parts
      String[] parts = lamp.split(",");

      // Parse the row and column values
      int r = Integer.parseInt(parts[0]);
      int c = Integer.parseInt(parts[1]);

      // Check if the lamp is illegal
      if (isLampIllegal(r, c)) {
        return true;
      }
    }

    return false;
  }
}
