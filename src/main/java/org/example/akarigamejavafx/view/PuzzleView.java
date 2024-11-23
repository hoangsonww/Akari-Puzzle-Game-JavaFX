package org.example.akarigamejavafx.view;

import org.example.akarigamejavafx.controller.ControllerImpl;
import org.example.akarigamejavafx.model.CellType;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * PuzzleView class to represent the puzzle view (game board) of the game
 */
public class PuzzleView implements FXComponent {

  /**
   * ControllerImpl object to represent the controller
   */
  private final ControllerImpl controller;

  /**
   * Image object to represent the light bulb image
   */
  private final Image bulbImage;

  /**
   * Constructor for the PuzzleView class
   *
   * @param controller ControllerImpl object to represent the controller
   */
  public PuzzleView(ControllerImpl controller) {
    this.controller = controller;
    this.bulbImage = new Image("light-bulb.png");
  }

  /**
   * Render the puzzle view
   *
   * @return Parent object representing the rendered puzzle view
   */
  @Override
  public Parent render() {
    // Create a GridPane to hold the puzzle cells
    GridPane grid = new GridPane();
    grid.setAlignment(Pos.CENTER);
    grid.setHgap(5);
    grid.setVgap(5);

    // Get the height and width of the active puzzle
    int height = controller.getActivePuzzle().getHeight();
    int width = controller.getActivePuzzle().getWidth();

    // Loop through each cell in the puzzle and create a cell container
    for (int row = 0; row < height; row++) {
      for (int col = 0; col < width; col++) {
        // Get the cell type and create a label and background for the cell
        CellType type = controller.getActivePuzzle().getCellType(row, col);
        Label cellLabel = new Label();
        Rectangle cellBackground = new Rectangle(40, 40);

        // Set the cell label and background based on the cell type
        if (type == CellType.CORRIDOR) {
          // If the cell is a lamp, set the label to a light bulb image and style it accordingly
          if (controller.isLamp(row, col)) {
            ImageView bulb = new ImageView(bulbImage);
            cellBackground.setFill(Color.LIGHTYELLOW);
            bulb.setFitWidth(30);
            bulb.setFitHeight(30);
            cellLabel.setGraphic(bulb);

            // Check if the lamp is illegally placed
            if (controller.isLampIllegal(row, col)) {
              cellBackground.setFill(Color.RED);
            } else {
              cellBackground.setFill(
                  Color.LIGHTYELLOW);
            }

            GridPane.setHalignment(cellLabel, javafx.geometry.HPos.CENTER);
            GridPane.setValignment(cellLabel, javafx.geometry.VPos.CENTER);
          } else if (controller.isLit(row, col)) {
            // If the cell is lit, set the background to light yellow
            cellBackground.setFill(Color.LIGHTYELLOW);
          } else {
            // Otherwise, set the background to white
            cellBackground.setFill(Color.WHITE);
          }
          cellBackground.setStroke(Color.BLACK);

          // Add hover effect to the cells
          int finalR2 = row;
          int finalC2 = col;
          cellBackground.setOnMouseEntered(
              event -> {
                if (!controller.isLamp(finalR2, finalC2)) {
                  cellBackground.setFill(Color.LIGHTBLUE);
                  cellBackground.setCursor(javafx.scene.Cursor.HAND);
                } else {
                  cellBackground.setCursor(javafx.scene.Cursor.HAND);
                }
              });
          cellBackground.setOnMouseExited(
              event -> {
                if (!controller.isLamp(finalR2, finalC2)) {
                  cellBackground.setFill(
                      controller.isLit(finalR2, finalC2)
                          ? Color.LIGHTYELLOW
                          : Color.WHITE);
                }
              });

          // Add hover effect to the cells
          cellLabel.setOnMouseEntered(
              event -> {
                cellLabel.setCursor(javafx.scene.Cursor.HAND);

                // Highlight only if the cell does not have a lamp
                if (!controller.isLamp(finalR2, finalC2)) {
                  cellBackground.setFill(Color.LIGHTBLUE);
                }
              });
          cellLabel.setOnMouseExited(
              event -> {
                // Reset the background color when the mouse exits
                if (!controller.isLamp(finalR2, finalC2)) {
                  cellBackground.setFill(
                      controller.isLit(finalR2, finalC2)
                          ? Color.LIGHTYELLOW
                          : Color.WHITE);
                }
              });

          // Add click event to the cells to ensure they are clickable and handle the cell clicks
          int finalR = row;
          int finalC = col;
          cellLabel.setOnMouseClicked(event -> controller.clickCell(finalR, finalC));
          cellBackground.setOnMouseClicked(event -> controller.clickCell(finalR, finalC));
        } else if (type == CellType.CLUE) {
          // If the cell is a clue, set the label to the clue number and style it accordingly
          int clue = controller.getActivePuzzle().getClue(row, col);
          cellLabel.setText(String.valueOf(clue));
          cellLabel.setStyle(
              "-fx-font-size: 18px; -fx-font-weight: bold; -fx-font-family: 'Poppins';");
          cellBackground.setFill(Color.LIGHTGRAY);
          cellBackground.setStroke(Color.BLACK);

          // Check if the clue is satisfied
          if (controller.isClueSatisfied(row, col)) {
            cellBackground.setFill(Color.LIGHTGREEN);
          } else {
            cellBackground.setFill(Color.LIGHTGRAY);
          }

          GridPane.setHalignment(cellLabel, javafx.geometry.HPos.CENTER);
          GridPane.setValignment(cellLabel, javafx.geometry.VPos.CENTER);
        } else if (type == CellType.WALL) {
          // If the cell is a wall, set the background to black
          cellBackground.setFill(Color.BLACK);
        }

        // Each cell is a GridPane with a label and background
        GridPane cell = new GridPane();
        cell.setAlignment(Pos.CENTER);
        cell.add(cellBackground, 0, 0);
        cell.add(cellLabel, 0, 0);

        // Add the cell to the grid
        grid.add(cell, col, row);
      }
    }

    // Return the puzzle grid
    return grid;
  }
}
