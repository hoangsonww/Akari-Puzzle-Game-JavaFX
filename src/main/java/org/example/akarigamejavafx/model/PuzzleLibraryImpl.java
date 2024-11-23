package org.example.akarigamejavafx.model;

import java.util.ArrayList;
import java.util.List;

/** This class represents a library of Akari puzzles */
public class PuzzleLibraryImpl implements PuzzleLibrary {

  /** The list of puzzles in the library */
  private final List<Puzzle> puzzles;

  /** Constructor for the PuzzleLibraryImpl class */
  public PuzzleLibraryImpl() {
    this.puzzles = new ArrayList<>();
  }

  /**
   * Adds the given puzzle so that it is the last puzzle in the library. Throws an
   * IllegalArgumentException if null is passed
   *
   * @param puzzle The puzzle to add
   */
  @Override
  public void addPuzzle(Puzzle puzzle) {
    if (puzzle == null) {
      throw new IllegalArgumentException("Cannot add null Puzzle to the library");
    }

    puzzles.add(puzzle);
  }

  /**
   * Returns the puzzle at the specified index. Throws an IndexOutOfBoundsException if the specified
   * index is outside the bounds of the puzzle library
   *
   * @param index The index of the puzzle to return
   * @return The puzzle at the specified index
   */
  @Override
  public Puzzle getPuzzle(int index) {
    return puzzles.get(index);
  }

  /**
   * Returns the size of the puzzle library, which is the number of puzzles it contains
   *
   * @return The size of the puzzle library
   */
  @Override
  public int size() {
    return puzzles.size();
  }
}
