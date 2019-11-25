package edu.cs3500.spreadsheets.commandline;

import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;

/**
 * A command that can build a worksheet should the need arise and can parse the arguments to the
 * program.
 */
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
