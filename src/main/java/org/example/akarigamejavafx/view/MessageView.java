package org.example.akarigamejavafx.view;

import org.example.akarigamejavafx.controller.ControllerImpl;

import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

/** MessageView class to represent the message view (message label) of the game */
public class MessageView implements FXComponent {

  /** ControllerImpl object to represent the controller */
  private final ControllerImpl controller;

  /**
   * Constructor for the MessageView class
   *
   * @param controller ControllerImpl object to represent the controller
   */
  public MessageView(ControllerImpl controller) {
    this.controller = controller;
  }

  /**
   * Render the message view
   *
   * @return Parent object representing the rendered message view
   */
  @Override
  public Parent render() {
    // Create an HBox to hold the message label
    HBox messageBox = new HBox();
    messageBox.setAlignment(Pos.CENTER);
    messageBox.setStyle("-fx-background-color: #4CAF50; -fx-padding: 10px;");

    // Determine the message based on the controller's state
    Label messageLabel = new Label();

    // If the puzzle is solved, show the congratulations message
    if (controller.isSolved()) {
      messageLabel.setText("🎉 Congratulations! Puzzle Solved! 🎉");
      messageLabel.setStyle(
          "-fx-font-size: 18px; -fx-font-weight: bold; -fx-text-fill: white; -fx-font-family: 'Poppins';");
    } else {
      // If the puzzle isn't solved, we don't show the message box
      messageBox.setStyle("-fx-padding: 0; -fx-background-color: transparent;");
      messageLabel.setText(""); // Empty message
    }

    // Add the message label to the message box and return the box
    messageBox.getChildren().add(messageLabel);
    return messageBox;
  }
}
