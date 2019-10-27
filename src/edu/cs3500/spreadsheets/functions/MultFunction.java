package edu.cs3500.spreadsheets.functions;

import java.util.function.BiFunction;

public class MultFunction implements BiFunction<Double, Double, Double> {
  @Override
  public Double apply(Double aDouble, Double aDouble2) {
    return aDouble * aDouble2;
  }
}
