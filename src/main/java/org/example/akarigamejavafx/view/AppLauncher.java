package org.example.akarigamejavafx.view;

import org.example.akarigamejavafx.controller.ControllerImpl;
import org.example.akarigamejavafx.model.Model;
import org.example.akarigamejavafx.SamplePuzzles;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import org.example.akarigamejavafx.model.*;

import java.util.Objects;

/** AppLauncher class to launch the JavaFX application */
public class AppLauncher extends Application {

  /** Default constructor for the AppLauncher class */
  public AppLauncher() {}

  /** Stage object to represent the main window of the application */
  private Stage stage;

  /** Scene object to represent the game scene */
  private Scene gameScene;

  /** Scene object to represent the instructions scene */
  private Scene instructionsScene;

  /** Scene object to represent the welcome scene */
  private Scene welcomeScene;

  /**
   * Start the JavaFX application
   *
   * @param stage Stage object to represent the main window of the application
   */
  @Override
  public void start(Stage stage) {
    this.stage = stage;

    // Load the Poppins font from Google Fonts
    Font.loadFont(getClass().getResourceAsStream("/Poppins-Regular.ttf"), 12);

    // Create the puzzle library of sample puzzles
    PuzzleLibrary library = new PuzzleLibraryImpl();
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_01));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_02));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_03));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_04));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_05));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_06));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_07));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_08));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_09));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_10));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_11));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_12));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_13));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_14));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_15));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_16));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_17));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_18));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_19));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_20));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_21));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_22));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_23));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_24));
    library.addPuzzle(new PuzzleImpl(SamplePuzzles.PUZZLE_25));

    // Create the model and controller
    Model model = new ModelImpl(library);
    ControllerImpl controller = new ControllerImpl(model);

    // Create the game scene
    gameScene = createGameScene(controller, model);

    // Create the instructions scene
    instructionsScene = createInstructionsScene();

    // Create the welcome scene
    welcomeScene = createWelcomeScene();

    // Load the light bulb icon
    Image icon =
        new Image(Objects.requireNonNull(getClass().getResourceAsStream("/light-bulb.png")));

    // Set up the stage - start with the Welcome scene
    // Flow: Welcome Scene -> Game Scene -> Instructions Scene
    // or: Welcome Scene -> Instructions Scene -> Game Scene ( -> Instructions Scene)
    stage.setTitle("The Akari Puzzle Game");
    stage.getIcons().add(icon);
    stage.setScene(welcomeScene);
    stage.show();
  }

  /**
   * Helper method to create the game scene
   *
   * @param controller Controller object to control the game
   * @param model Model object to represent the game state
   * @return Scene object to represent the game scene
   */
  private Scene createGameScene(ControllerImpl controller, Model model) {
    // Create the main UI components from PuzzleView, ControlView, and MessageView
    PuzzleView puzzleView = new PuzzleView(controller);
    ControlView controlView = new ControlView(controller);
    MessageView messageView = new MessageView(controller);

    // Header for the game scene
    HBox header = createHeader();

    // Footer for the game scene
    HBox footer = createFooter();

    // Label to display active puzzle information
    Label puzzleInfoLabel = new Label();
    puzzleInfoLabel.setStyle(
        "-fx-font-size: 16px; -fx-font-family: 'Poppins'; -fx-text-fill: black;");
    puzzleInfoLabel.setAlignment(Pos.CENTER);
    updatePuzzleInfoLabel(puzzleInfoLabel, model);

    // Puzzle and control components - include puzzle info label, puzzle view, and control view
    VBox puzzleAndControls = new VBox();
    puzzleAndControls.setAlignment(Pos.CENTER);
    puzzleAndControls.setSpacing(10);
    puzzleAndControls
        .getChildren()
        .addAll(puzzleInfoLabel, puzzleView.render(), controlView.render());

    // Append the UI components to the root layout:
    // Header at the top, puzzle and controls in the center (with puzzle info label),
    // and footer at the bottom
    BorderPane root = new BorderPane();
    root.setCenter(puzzleAndControls);
    root.setBottom(footer);
    root.setTop(header);

    // Register the model observer to update the UI dynamically
    model.addObserver(
        updatedModel -> {
          // Update the puzzle info label
          updatePuzzleInfoLabel(puzzleInfoLabel, model);

          // Update the puzzle view and control view and the puzzle info label
          puzzleAndControls
              .getChildren()
              .setAll(puzzleInfoLabel, puzzleView.render(), controlView.render());

          // Update the header and footer based on the controller's isSolved state
          if (controller.isSolved()) {
            root.setTop(messageView.render());
          } else {
            root.setTop(header);
          }

          // Update the footer
          root.setBottom(footer);
        });

    // Return the game scene
    Scene scene = new Scene(root, 800, 750);
    scene.getStylesheets().add("main.css");
    return scene;
  }

  /**
   * Helper method to create the instructions scene
   *
   * @return Scene object to represent the instructions scene
   */
  private Scene createInstructionsScene() {
    // Create the layout for the instructions screen
    VBox instructionsLayout = new VBox();
    instructionsLayout.setAlignment(Pos.CENTER);
    instructionsLayout.setSpacing(20);
    instructionsLayout.setStyle("-fx-padding: 20px;");

    // Instructions title and content
    Label instructionsTitle = new Label("Instructions for The Akari Puzzle Game");
    instructionsTitle.setStyle(
        "-fx-font-size: 20px; -fx-font-weight: bold; -fx-font-family: 'Poppins';");
    Label instructionsContent =
        new Label(
            "1. Place lamps on empty cells to light up corridors.\n"
                + "2. A lamp illuminates all cells in its row and column until a wall or clue blocks it.\n"
                + "3. Clues (numbered cells) must have exactly that many lamps adjacent to them.\n"
                + "4. No two lamps can see each other in the same row or column.\n"
                + "5. The puzzle is solved when all corridors are lit, all clues are satisfied, and no illegal lamps exist.\n"
                + "6. Use the control buttons below the puzzle to navigate between puzzles and reset the current puzzle (if you get stuck).\n"
                + "7. Click the 'Hint' button to get a hint for the current puzzle.");
    instructionsContent.setStyle(
        "-fx-font-size: 14px; -fx-font-family: 'Poppins'; -fx-text-alignment: center;");
    instructionsContent.setWrapText(true);

    // Back to Game button
    Button backButton = new Button("Start Solving!");
    backButton.getStyleClass().add("button");
    backButton.setOnAction(event -> stage.setScene(gameScene));

    // Back to Welcome button
    Button backWelcomeButton = new Button("Back to Welcome");
    backWelcomeButton.getStyleClass().add("button");
    backWelcomeButton.setOnAction(event -> stage.setScene(welcomeScene));

    // Add all components to the layout
    instructionsLayout.getChildren().addAll(instructionsTitle, instructionsContent, backButton, backWelcomeButton);
    Scene instructionsScene = new Scene(instructionsLayout, 800, 600);
    instructionsScene.getStylesheets().add("main.css");
    return instructionsScene;
  }

  /**
   * Helper method to create the welcome scene with styled buttons and a dark gray background
   *
   * @return Scene object to represent the welcome scene
   */
  private Scene createWelcomeScene() {
    // Create the layout for the welcome screen
    VBox layout = new VBox();
    layout.setAlignment(Pos.CENTER);
    layout.setSpacing(30);
    layout.setStyle("-fx-background-color: #2b2b2b; -fx-padding: 50px;");

    // Title for the welcome screen
    Label title = new Label("Welcome to The Akari Puzzle Game");
    title.getStyleClass().add("label");
    title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");

    // Start Game button -> Directs to the game scene
    Button startGameButton = new Button("Start Game");
    startGameButton.getStyleClass().add("button");
    startGameButton.setOnAction(event -> stage.setScene(gameScene));

    // Instructions button -> Directs to the instructions scene
    Button instructionsButton = new Button("Instructions");
    instructionsButton.getStyleClass().add("button");
    instructionsButton.setOnAction(event -> stage.setScene(instructionsScene));

    // Exit button
    Button exitButton = new Button("Exit");
    exitButton.getStyleClass().add("button");
    exitButton.setOnAction(event -> System.exit(0));

    // Add all components to the layout
    layout.getChildren().addAll(title, startGameButton, instructionsButton, exitButton);

    // Return the welcome scene
    Scene welcomeScene = new Scene(layout, 800, 600);
    welcomeScene.getStylesheets().add("main.css");
    return welcomeScene;
  }

  /**
   * Helper method to create the header for the game scene
   *
   * @return HBox object to represent the header
   */
  private HBox createHeader() {
    // Create the header with a title and instructions button
    HBox header = new HBox();
    header.setAlignment(Pos.CENTER);
    header.setSpacing(20);
    header.setStyle("-fx-background-color: #333333; -fx-padding: 10px;");

    // Title label
    Label titleLabel = new Label("The Akari Puzzle Game");
    titleLabel.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-family: 'Poppins';");

    // Instructions button
    Button instructionsButton = new Button("Instructions");
    instructionsButton.setStyle("-fx-font-family: 'Poppins';");
    instructionsButton.setOnAction(event -> stage.setScene(instructionsScene));

    // Add the title and instructions button to the header
    header.getChildren().addAll(titleLabel, instructionsButton);
    return header;
  }

  /**
   * Helper method to create the footer for the game scene
   *
   * @return HBox object to represent the footer
   */
  private HBox createFooter() {
    // Create the footer with a label
    HBox footer = new HBox();
    footer.setAlignment(Pos.CENTER);
    footer.setStyle("-fx-background-color: #333333; -fx-padding: 10px;");

    // Footer label
    Label footerLabel = new Label("Enjoy solving the puzzles! © 2024 Son Nguyen");
    footerLabel.setStyle("-fx-text-fill: white; -fx-font-family: 'Poppins';");

    // Add the footer label to the footer
    footer.getChildren().add(footerLabel);
    return footer;
  }

  /**
   * Helper method to update the puzzle info label
   *
   * @param puzzleInfoLabel Label object to display the active puzzle index
   * @param model Model object to get the current puzzle state
   */
  private void updatePuzzleInfoLabel(Label puzzleInfoLabel, Model model) {
    // 1-based index for the current puzzle
    int currentIndex = model.getActivePuzzleIndex() + 1;
    int totalPuzzles = model.getPuzzleLibrarySize();
    puzzleInfoLabel.setText("Puzzle " + currentIndex + " of " + totalPuzzles);
  }
}
