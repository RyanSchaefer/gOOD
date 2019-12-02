package edu.cs3500.spreadsheets.controller.provider;

import java.io.IOException;

import edu.cs3500.spreadsheets.view.provider.ViewSpreadsheetInterface;

/**
 * Represents Controller features that can be added to any {@code ViewSpreadsheetInterface}s.
 */
public interface Features {

  /**
   * Select a cell that has been clicked on by the user at the given mouse coordinates. Highlight
   * the cell and display it's raw contents in the view.
   *
   * @param mouseX The x coordinate of the click.
   * @param mouseY The y coordinate of the click.
   */
  void selectCell(int mouseX, int mouseY);

  /**
   * Confirm the input that has been written by the user to the currently highlighted cell. Update
   * the view with the evaluation from the model. Update the model with the data if the input is
   * valid.
   *
   * @param text The user input text.
   */
  void confirmButton(String text);

  /**
   * Clear the input written by the user for the currently highlighted cell. Replace the input text
   * with the cell's original text.
   */
  void denyButton();

  /**
   * Delete the contents of the currently highlighted cell. Update the model so that the cell is
   * empty. Update the view to clear the text.
   */
  void deleteCell();

  /**
   * Change the highlighted selection based on the given direction.
   *
   * @param d The direction the highlighted selection should shift towards.
   */
  void shiftSelection(Directions d);

  /**
   * Set the view for the controller to control.
   *
   * @param v The view.
   */
  void setView(ViewSpreadsheetInterface v);

  /**
   * Start the controller, run the view.
   *
   * @throws IOException If the view cannot be run.
   */
  void run() throws IOException;
}
