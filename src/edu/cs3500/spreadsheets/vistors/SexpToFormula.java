package edu.cs3500.spreadsheets.vistors;

import java.util.ArrayList;
import java.util.List;

import edu.cs3500.spreadsheets.model.Formula.Formula;
import edu.cs3500.spreadsheets.model.Formula.Reference;
import edu.cs3500.spreadsheets.model.Formula.Value.VBoolean;
import edu.cs3500.spreadsheets.model.Formula.Value.VDouble;
import edu.cs3500.spreadsheets.model.Formula.Value.VString;
import edu.cs3500.spreadsheets.model.Formula.functions.ErrorFunction;
import edu.cs3500.spreadsheets.model.Formula.functions.LessThanFunc;
import edu.cs3500.spreadsheets.model.Formula.functions.LowerCase;
import edu.cs3500.spreadsheets.model.Formula.functions.ProductFunc;
import edu.cs3500.spreadsheets.model.Formula.functions.SumFunc;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.sexp.SList;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

public class SexpToFormula implements SexpVisitor<Formula> {

  private IWorksheet model;

  public SexpToFormula(IWorksheet model) {
    this.model = model;
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
   switch (l.get(0).toString().toLowerCase()) {
     case "lowercase":
       return new LowerCase(map(l.subList(1, l.size())), new SList(l).toString());
     case "sum":
       return new SumFunc(map(l.subList(1, l.size())), new SList(l).toString());
     case "product":
       return new ProductFunc(map(l.subList(1, l.size())), new SList(l).toString());
     case "<":
       return new LessThanFunc(map(l.subList(1, l.size())), new SList(l).toString());
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
