Interfaces -
- IWorksheet - An interface allowing for multiple implementations of Worksheets.
It should allow observation about the cells as well as changes to the cells. There are also
convenience functions like getActiveCells that allow for the model to be drawn more
efficiently.
- Formula - is one of A Value, Function, or Reference
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
- SexpToValue - this this Sexp into a Value (if it looks like a function, turn it into a string).
- DependencyVisitor - get the dependencies in this Sexp.