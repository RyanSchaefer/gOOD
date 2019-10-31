package edu.cs3500.spreadsheets.vistors;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import edu.cs3500.spreadsheets.model.Formula.Formula;
import edu.cs3500.spreadsheets.model.Formula.Reference;
import edu.cs3500.spreadsheets.model.Formula.Value.VBoolean;
import edu.cs3500.spreadsheets.model.Formula.Value.VDouble;
import edu.cs3500.spreadsheets.model.Formula.Value.VString;
import edu.cs3500.spreadsheets.model.Formula.functions.AbstractFunction;
import edu.cs3500.spreadsheets.model.Formula.functions.ErrorFunction;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.sexp.SList;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

public class SexpToFormula implements SexpVisitor<Formula> {

  private IWorksheet model;
  private Map<String, AbstractFunction> functions;

  public SexpToFormula(IWorksheet model, Map<String, AbstractFunction> functions) {
    this.model = model;
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
              map(l.subList(1, l.size())), "=" + new SList(l).toString());
    }
    return new ErrorFunction(new SList(l).toString());
  }

  public List<Formula> map(List<Sexp> l) {
    List<Formula> ret = new ArrayList<>();
    for (Sexp s: l) {
      ret.add(s.accept(this));
    }
    return ret;
  }

  @Override
  public Formula visitSymbol(String s) {
    return new Reference(model, s);
  }

  @Override
  public Formula visitString(String s) {
    return new VString(s);
  }
}
