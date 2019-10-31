package edu.cs3500.spreadsheets.model;

import java.util.List;

public interface Formula {

  List<Value> evaluate();

}
