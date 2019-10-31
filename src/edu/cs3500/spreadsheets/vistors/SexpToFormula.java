package edu.cs3500.spreadsheets.vistors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.formula.Formula;
import edu.cs3500.spreadsheets.model.formula.Reference;
import edu.cs3500.spreadsheets.model.formula.functions.ErrorFunction;
import edu.cs3500.spreadsheets.model.formula.functions.IFunction;
import edu.cs3500.spreadsheets.model.formula.value.VBoolean;
import edu.cs3500.spreadsheets.model.formula.value.VDouble;
import edu.cs3500.spreadsheets.model.formula.value.VString;
import edu.cs3500.spreadsheets.sexp.SList;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

/**
 * Converts an Sexp into a formula based on the fact that it had an = sign infront of it. Thus all
 * strings that look like functions should be treated as functions.
 */
public class SexpToFormula implements SexpVisitor<Formula> {

  private IWorksheet model;
  private Map<String, IFunction> functions;
  private String original;

  /**
   * Constructs an SexpToFormula.
   *
   * @param model     the model which to construct it over
   * @param original  the string that was used to construct this
   * @param functions the functions that are supported
   */
  public SexpToFormula(IWorksheet model, String original, Map<String, IFunction> functions) {
    this.model = model;
    this.original = original;
    this.functions = functions;
  }

  @Override
  public Formula visitBoolean(boolean b) {
    return new VBoolean(b);
  }

  @Override
  public Formula visitNumber(double d) {
    return new VDouble(d);
  }

  @Override
  public Formula visitSList(List<Sexp> l) {
    if (l.size() == 0) {
      throw new IllegalArgumentException("No function in formula expression");
    }
    String function = l.get(0).toString().toLowerCase();
    if (functions.containsKey(function)) {
      return functions.get(function).build(
              map(l.subList(1, l.size())), "=" + original);
    }
    return new ErrorFunction(new SList(l).toString());
  }

  private List<Formula> map(List<Sexp> l) {
    List<Formula> ret = new ArrayList<>();
    for (Sexp s: l) {
      ret.add(s.accept(this));
    }
    return ret;
  }

  @Override
  public Formula visitSymbol(String s) {
    return new Reference(model,  s);
  }

  @Override
  public Formula visitString(String s) {
    return new VString(s);
  }
}
