package edu.cs3500.spreadsheets.commandLine;

import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;

public interface CommandParser {

  /**
   * Parse the given command with this command parser.
   *
   * @param builder if we need to build a model, how should we build it?
   * @param args    the command string.
   * @return If a matching command has been found.
   */
  String parse(WorksheetReader.WorksheetBuilder<IWorksheet> builder, String[] args);

}
