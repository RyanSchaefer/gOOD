package edu.cs3500.spreadsheets.view;

import java.io.IOException;

import edu.cs3500.spreadsheets.controller.Features;

/**
 * The interface that all views must implement.
 */
public interface IView {

  /**
   * Render the entire spreadsheet.
   *
   * @throws IOException some issue in what we are rendering to
   */
  void renderSpreadsheet() throws IOException;

  /**
   * Displays this view to the user.
   */
  void makeVisible();

  void addFeatures(Features f);

}
