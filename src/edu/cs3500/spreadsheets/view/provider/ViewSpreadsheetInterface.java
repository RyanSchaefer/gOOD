package edu.cs3500.spreadsheets.view.provider;

import java.io.IOException;
import java.util.List;

import edu.cs3500.spreadsheets.controller.provider.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.provider.WorksheetInterface;

/**
 * Renders a {@link WorksheetInterface} in some manner.
 */
public interface ViewSpreadsheetInterface {
  /**
   * Renders a model in some manner (e.g. as text, or as graphics, etc.).
   *
   * @throws IOException if the rendering fails for some reason
   */
  void render() throws IOException;

  /**
   * Add controller features to the view.
   *
   * @param f The provided controller.
   */
  void addFeatures(Features f);

  /**
   * Select a cell based on the given position and return its contents.
   *
   * @param position   The position of the cell to select.
   * @param rawContent The raw contents of the cell.
   * @return The contents of the cell.
   */
  String selectCell(Coord position, String rawContent);

  /**
   * Update the view with the provided contents.
   *
   * @param update The cell contents to be updated.
   */
  void updateView(List<List<String>> update);

  /**
   * Get the distance the view is horizontally away from it's origin.
   *
   * @return The distance the view is horizontally away from it's origin.
   */
  int getXScroll();

  /**
   * Get the distance the view is vertically away from it's origin.
   *
   * @return The distance the view is vertically away from it's origin.
   */
  int getYScroll();

}
