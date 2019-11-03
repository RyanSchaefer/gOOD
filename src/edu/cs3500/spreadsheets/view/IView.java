package edu.cs3500.spreadsheets.view;

import java.io.IOException;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * The interface that all views must implement.
 */
public interface IView {

  /**
   * Renders only the cells that have changed.
   *
   * @param cells the cells that have changed
   * @throws IOException some issue in what we are rendering to
   */
  void renderChanges(List<Coord> cells) throws IOException;

  /**
   * Render the entire spreadsheet.
   *
   * @throws IOException some issue in what we are rendering to
   */
  void renderSpreadsheet() throws IOException;

}
