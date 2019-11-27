Assignment 7:
Changes
- added a way to easily construct new models, ModelBuilder.
- added a way to easily add new command line arguments, CommandParser.
- added ability to use visitors for Values.
- added a delete feature to delete the contents of a cell by pressing the delete key.
- added the ability to name and save  a file.
- added the ability to navigate the cells by pressing "W" "A" "S" "D"
  (up, left, down, right respectively).

Assignment 6:
Changes - Added ICell type
IView - One of several views of the model (e.g. text / gui)
CellView - A view of a normal cell.
ErrorCell - A view for a cell that has an error in it (will always be unevaluated)
ColumnHeaders - A panel that tells us which column we are in.
RowHeader - A panel that tells us which row we are in.
TextualView - a view which saves the spreadsheet to a file.
ScrollView - a gui representation of the view.

Assignment 5:
Interfaces -
- IWorksheet - An interface allowing for multiple implementations of Worksheets.
It should allow observation about the cells as well as changes to the cells. There are also
convenience functions like getActiveCells that allow for the model to be drawn more
efficiently.
- Formula - is one of A Value, IFunction, or Reference
- IFunction - represent any type of function
- Value - is one of a VBoolean, VString, or VDouble

Classes
- Basic Worksheet - Basic worksheet implements IWorksheet using a Map<Coord, Formula>. In this
environment, everything is essentially a function which evaluates to the value of the cell.
- Reference - A Reference, evaluates to a list of value
- AbstractValue - all Values extend this.
- VBoolean - evaluates to a Boolean and null otherwise.
- VDouble - evaluates to a Double and null otherwise.
- VString - evaluates to a String and null otherwise.
- ValueHolder - holds an evaluated list of value for use elsewhere.
- ErrorFunction - this cell could not be converted to one of the other functions so it should
- - always throw an error when evaluating and return the string which was malformed and used
- - to create it
- LessThanFunc - takes in two formula which strictly evaluates to Double and returns a list
- - VBoolean in it.
- LowerCase - takes in one formula which strictly evaluates to a String and returns the lowercase
- - VString of that String.
- ProductFunc - takes in an arbitrary amount of Formula that evaluate to anything and ignores them
- - if they aren't numbers. Defaults to 0. Multiplies them together.
- ProductFunc - takes in an arbitrary amount of Formula that evaluate to anything and ignores them
- - if they aren't numbers. Defaults to 0. Sums them together.

Visitors
- SexpToFormula - turns this Sexp into a Formula
- SexpToValue - this Sexp into a Value (if it looks like a function, turn it into a string).
- DependencyVisitor - get the dependencies in this Sexp.