package edu.cs3500.spreadsheets.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Given the class that we want to build, return the builder for that model.
 */
public class ModelBuilder {

  private Map<Class, WorksheetReader.WorksheetBuilder<IWorksheet>> models = new HashMap<>();

  private ModelBuilder() {
    /*
    We don't want a person to be able to construct this in a regular manner.
     */
  }

  /**
   * Builds a ModelBuilder.
   */
  public static class ModelBuilderBuilder {
    ModelBuilder m;

    /**
     * Start Building a ModelBuilder.
     */
    public ModelBuilderBuilder() {
      m = new ModelBuilder();
    }

    /**
     * Add one supported model to the models we can build with this model builder.
     *
     * @param c   the class of the model.
     * @param wsb the worksheet builder for the model.
     * @return the model builder builder for further building.
     */
    public ModelBuilderBuilder addModel(Class c, WorksheetReader.WorksheetBuilder<IWorksheet> wsb) {
      m.models.put(c, wsb);
      return this;
    }

    /**
     * Build the completed model builder.
     *
     * @return the complete model builder.
     */
    public ModelBuilder build() {
      return m;
    }

  }

  /**
   * Build a model of the given class.
   *
   * @param c the class we want to build a model for
   * @return the worksheet builder for that class
   */
  public WorksheetReader.WorksheetBuilder<IWorksheet> buildModel(Class c) {
    if (models.containsKey(c)) {
      return models.get(c);
    } else {
      throw new IllegalArgumentException("Model not supported");
    }
  }

}