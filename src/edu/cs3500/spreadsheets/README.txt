Interfaces -
- IWorksheet - An interface allowing for multiple implementations of Worksheets.
It should allow observation about the cells as well as changes to the cells. There are also
convenience functions like getActiveCells that allow for the model to be drawn more
efficiently.
- Should we need to change how the actual behind the scenes of Worksheets is implemented (say we
wanted to change how cells are implemented) we wouldn't have to change the view or the model since
they are coded against the interface and not the specific implementation.

Classes
- Basic Worksheet - Basic worksheet implements IWorksheet using a Map<Coord, Sexp>. In this
environment, everything is essentially a function which returns to the value of the cell. This means
that every cell can accept any visitor that it needs to to transform the expression.

Visitors
- All of the visitors created are essentially just functions we want to run over cells
- Most of the visitors must be evaluated in some context, and that context is the model it ends
up being constructed with
- Specific visitors
- - Dependency Visitor - needed to evaluate cycles, gets all of the cells that this Sexp depends
upon
- - EvalVisitor - evaluates the Sexp to its simplest form given the model context it should be
evaluated in
- - NumberVisitor - turns this Sexp into a Number no matter what. If this Sexp cannot be converted
directly into a number, then it should return the base number provided when it was created. This
function also takes in a function which takes in two numbers and converts them into one number for
ranges of numbers.
- - PrintVisitor - only needed since Sexp doubles don't format to %f.
- - StrictNumberVisitor - only visits numbers otherwise throws an error because the caller of this
needs Numbers only to preserve types.
- - StringVisitor - only visits strings otherwise throws an error because the call of this expected
to deal with only numbers.

Functions
- function objects that do what they say on the tin.