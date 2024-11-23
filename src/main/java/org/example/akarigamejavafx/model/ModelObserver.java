package org.example.akarigamejavafx.model;

/** This interface is used to observe changes in the Model class */
public interface ModelObserver {

  /**
   * When a model value is changed, the model calls update() on all active ModelObserver objects
   *
   * @param model The model that has been updated
   */
  void update(Model model);
}
