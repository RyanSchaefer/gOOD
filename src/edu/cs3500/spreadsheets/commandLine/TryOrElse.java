package edu.cs3500.spreadsheets.commandLine;

import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;

/**
 * Try on command or run the other command.
 */
public class TryOrElse implements CommandParser {

  private CommandParser cp1;
  private CommandParser cp2;

  TryOrElse(CommandParser cp1, CommandParser cp2) {
    this.cp1 = cp1;
    this.cp2 = cp2;
  }

  @Override
  public String parse(WorksheetReader.WorksheetBuilder<IWorksheet> builder, String[] args) {
    String res = cp1.parse(builder, args);
    if (res == null) {
      return cp2.parse(builder, args);
    }
    return res;
  }
}
