package edu.cs3500.spreadsheets.functions;

import java.util.function.BiFunction;
import java.util.function.Function;

import edu.cs3500.spreadsheets.sexp.Sexp;

public class SumFunction implements BiFunction<Double, Double, Double> {
  @Override
  public Double apply(Double aDouble, Double aDouble2) {
    return aDouble + aDouble2;
  }
}

