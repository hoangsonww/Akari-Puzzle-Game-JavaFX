package org.example.akarigamejavafx.view;

import org.example.akarigamejavafx.controller.ControllerImpl;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

/** ControlView class to represent the control view (control buttons) of the game */
public class ControlView implements FXComponent {

  /** ControllerImpl object to represent the controller */
  private final ControllerImpl controller;

  /**
   * Constructor for the ControlView class
   *
   * @param controller ControllerImpl object to represent the controller
   */
  public ControlView(ControllerImpl controller) {
    this.controller = controller;
  }

  /**
   * Render the control view
   *
   * @return Parent object representing the rendered control view
   */
  @Override
  public Parent render() {
    // Create a horizontal box to hold the control buttons
    HBox controls = new HBox();
    controls.setAlignment(Pos.CENTER);
    controls.setSpacing(10);

    // Create the Previous button and make it call the clickPrevPuzzle method in the controller
    Button prevButton = new Button("Previous");
    prevButton.setOnAction(event -> controller.clickPrevPuzzle());

    // Create the Next button and make it call the clickNextPuzzle method in the controller
    Button nextButton = new Button("Next");
    nextButton.setOnAction(event -> controller.clickNextPuzzle());

    // Create the Random button and make it call the clickRandPuzzle method in the controller
    Button randomButton = new Button("Random");
    randomButton.setOnAction(event -> controller.clickRandPuzzle());

    // Create the Reset button and make it call the clickResetPuzzle method in the controller
    Button resetButton = new Button("Reset");
    resetButton.setOnAction(event -> controller.clickResetPuzzle());

    // Create the Hint button and display the hint in an alert dialog
    Button hintButton = new Button("Hint");
    hintButton.setOnAction(
        event -> {
          String hint = controller.getHint();
          Alert hintAlert = new Alert(AlertType.INFORMATION);
          hintAlert.setTitle("Hint");
          hintAlert.setHeaderText("Here's a hint!");
          hintAlert.setContentText(hint);
          hintAlert.showAndWait();
        });

    // Create the Exit button and make it close the application
    Button exitButton = new Button("Exit");
    exitButton.setOnAction(event -> System.exit(0));

    // Add the buttons to the horizontal box and return the box
    controls
        .getChildren()
        .addAll(prevButton, nextButton, randomButton, hintButton, resetButton, exitButton);
    return controls;
  }
}
