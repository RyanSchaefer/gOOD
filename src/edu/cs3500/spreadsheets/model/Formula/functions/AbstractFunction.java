package edu.cs3500.spreadsheets.model.Formula.functions;

import java.util.List;

import edu.cs3500.spreadsheets.model.Formula.Formula;
import edu.cs3500.spreadsheets.model.Formula.Value.Value;

abstract public class AbstractFunction implements Formula {
  /**
   * Builds a new function with arguments from the original, following the builder template.
   *
   * @param contents the arguments to the function
   * @param original the string used to make the function
   * @return a function the same as the function that built it
   */
  abstract public Formula build(List<Formula> contents, String original);

  @Override
  abstract public List<Value> evaluate();
}
