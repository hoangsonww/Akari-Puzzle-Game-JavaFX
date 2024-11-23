package org.example.akarigamejavafx;

import org.example.akarigamejavafx.view.AppLauncher;

import javafx.application.Application;

/** Main class to launch the JavaFX application */
public class Main {

  /** Default constructor for the Main class */
  public Main() {}

  /**
   * Main method to launch the JavaFX application
   *
   * @param args Command line arguments
   */
  public static void main(String[] args) {
    Application.launch(AppLauncher.class);
  }
}
