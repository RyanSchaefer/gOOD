package edu.cs3500.spreadsheets;

import java.util.HashMap;
import java.util.Map;

import edu.cs3500.spreadsheets.commandline.BlankEditableGUICommand;
import edu.cs3500.spreadsheets.commandline.BlankGuiCommand;
import edu.cs3500.spreadsheets.commandline.CommandParser;
import edu.cs3500.spreadsheets.commandline.Commands;
import edu.cs3500.spreadsheets.commandline.EditableGUICommand;
import edu.cs3500.spreadsheets.commandline.EvalCommand;
import edu.cs3500.spreadsheets.commandline.GuiCommand;
import edu.cs3500.spreadsheets.commandline.SaveCommand;
import edu.cs3500.spreadsheets.model.BasicWorksheet;
import edu.cs3500.spreadsheets.model.ModelBuilder;
import edu.cs3500.spreadsheets.model.formula.functions.IFunction;
import edu.cs3500.spreadsheets.model.formula.functions.LessThanFunc;
import edu.cs3500.spreadsheets.model.formula.functions.LowerCase;
import edu.cs3500.spreadsheets.model.formula.functions.ProductFunc;
import edu.cs3500.spreadsheets.model.formula.functions.SumFunc;

/**
 * The main class for our program.
 */
public class BeyondGood {
  /**
   * The main entry point.
   * @param args any command-line arguments
   */
  public static void main(String[] args) {
    Map<String, IFunction> functionsSupported = new HashMap<>();
    functionsSupported.put("lowercase", new LowerCase());
    functionsSupported.put("<", new LessThanFunc());
    functionsSupported.put("product", new ProductFunc());
    functionsSupported.put("sum", new SumFunc());

    ModelBuilder mb = new ModelBuilder.ModelBuilderBuilder()
            .addModel(BasicWorksheet.class,
                    new BasicWorksheet.BasicWorksheetBuilder(functionsSupported))
            .build();

    CommandParser cp = new Commands.CommandsBuilder()
            .newCommand(new EvalCommand())
            .newCommand(new SaveCommand())
            .newCommand(new GuiCommand())
            .newCommand(new BlankGuiCommand())
            .newCommand(new BlankEditableGUICommand())
            .newCommand(new EditableGUICommand()).build();


    System.out.print(cp.parse(mb.buildModel(BasicWorksheet.class), args));

  }
}
