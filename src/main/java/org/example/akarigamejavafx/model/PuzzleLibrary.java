package org.example.akarigamejavafx.model;

/** Represents a collection (library) of Akari puzzles */
public interface PuzzleLibrary {

  /**
   * Adds the given puzzle so that it is the last puzzle in the library. Throws an
   * IllegalArgumentException if null is passed
   *
   * @param puzzle The puzzle to add
   */
  void addPuzzle(Puzzle puzzle);

  /**
   * Returns the puzzle at the specified index. Throws an IndexOutOfBoundsException if the specified
   * index is outside the bounds of the puzzle library
   *
   * @param index The index of the puzzle to return
   * @return The puzzle at the specified index
   */
  Puzzle getPuzzle(int index);

  /**
   * Returns the size of the puzzle library, which is the number of puzzles it contains
   *
   * @return The size of the puzzle library
   */
  int size();
}
