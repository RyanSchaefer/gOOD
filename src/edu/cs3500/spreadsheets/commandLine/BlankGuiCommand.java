package edu.cs3500.spreadsheets.commandline;


import edu.cs3500.spreadsheets.model.BasicSlimWorksheet;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.ScrollView;

/**
 * Opens a new blank GUI (-gui).
 */
public class BlankGuiCommand implements CommandParser {
  @Override
  public String parse(WorksheetReader.WorksheetBuilder<IWorksheet> builder, String[] args) {
    if (args.length < 1) {
      return null;
    }
    if (args[0].equals("-gui")) {
      IWorksheet model = builder.createWorksheet();
      new ScrollView(new BasicSlimWorksheet(model)).makeVisible();
      return "GUI Running.\nTerminating...";
    }
    return null;
  }
}
