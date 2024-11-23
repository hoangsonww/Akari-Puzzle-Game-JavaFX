package org.example.akarigamejavafx.view;

import javafx.scene.Parent;

/** Represents a component that can be rendered in JavaFX */
public interface FXComponent {

  /**
   * Render the component and return the resulting Parent object
   *
   * @return Parent object representing the rendered component
   */
  Parent render();
}
