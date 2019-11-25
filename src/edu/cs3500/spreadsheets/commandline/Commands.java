package edu.cs3500.spreadsheets.commandline;

import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;

/**
 * A complete set of commands to run.
 */
public class Commands implements CommandParser {

  private CommandParser strategy;

  private Commands(CommandParser strategy) {
    this.strategy = strategy;
  }

  /**
   * Convenience constructor for a bunch of commands.
   */
  public static class CommandsBuilder {
    CommandParser c;

    public CommandsBuilder() {
      c = (WorksheetReader.WorksheetBuilder<IWorksheet> builder,
           String[] args) -> "No matching command found";
    }

    public CommandsBuilder newCommand(CommandParser command) {
      c = new TryOrElse(command, c);
      return this;
    }

    public Commands build() {
      return new Commands(c);
    }

  }

  @Override
  public String parse(WorksheetReader.WorksheetBuilder<IWorksheet> builder, String[] args) {
    return strategy.parse(builder, args);
  }
}
