package edu.cs3500.spreadsheets.model.formula.functions;

import java.util.List;

import edu.cs3500.spreadsheets.model.formula.Formula;

/**
 * A function within the spreadsheet that can be build other functions of the same type.
 */
public interface IFunction extends Formula {

  /**
   * Builds a new function with arguments from the original, following the builder template.
   *
   * @param contents the arguments to the function
   * @param original the string used to make the function
   * @return a function the same as the function that built it
   */
  Formula build(List<Formula> contents, String original);

}
