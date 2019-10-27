package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.vistors.DependencyVisitor;
import edu.cs3500.spreadsheets.vistors.EvalVisitor;
import edu.cs3500.spreadsheets.vistors.PrintVisitor;

public class BasicCell implements ICell {
  private List<Coord> dependencies = new ArrayList<>();
  private Sexp evaluated;
  private Sexp original;
  private IWorksheet model;

  public BasicCell(Sexp original, IWorksheet model) {
    this.original = original;
    this.dependencies.addAll(original.accept(new DependencyVisitor()));
    this.model = model;
  }

  @Override
  public Sexp getValue() throws IllegalArgumentException {
    return this.evaluated;
  }

  @Override
  public String getExpressionText() {
    return original.accept(new PrintVisitor());
  }

  @Override
  public void reevaluate(List<Coord> changed) {
    if (Collections.disjoint(changed, dependencies)) {
      /*
        We don't need to do anything because none of its dependencies have changed
       */
    } else {
      this.evaluated.accept(new EvalVisitor(this.model));
    }
  }

  @Override
  public void evaluate() {
    this.evaluated = this.original.accept(new EvalVisitor(this.model));
  }
}
