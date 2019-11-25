package edu.cs3500.spreadsheets.commandline;

import edu.cs3500.spreadsheets.controller.SheetController;
import edu.cs3500.spreadsheets.model.BasicSlimWorksheet;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.EditableView;

/**
 * Command to open a new editable GUI (-edit).
 */
public class BlankEditableGUICommand implements CommandParser {
  @Override
  public String parse(WorksheetReader.WorksheetBuilder<IWorksheet> builder,
                      String[] args) {
    if (args.length < 1) {
      return null;
    }
    if (args[0].equals("-edit")) {
      IWorksheet model = builder.createWorksheet();
      new SheetController(model, new EditableView(new BasicSlimWorksheet(model))).displayView();
      return "Running controller\nTerminating...";
    }
    return null;
  }
}
