package org.example.akarigamejavafx.model;

/** Represents the type of a cell in an Akari puzzle */
public enum CellType {
  /** Represents a cell that contains a clue - value 0-4 */
  CLUE,

  /** Represents a cell that is empty - value 6 */
  CORRIDOR,

  /** Represents a cell that is a wall - value 5 */
  WALL,
}
