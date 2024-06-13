package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.NotATupleException;
import stella.exception.TupleIndexOutOfBoundsException;
import stella.exception.TypeCheckingException;
import stella.type.TupleType;
import stella.type.Type;
import stella.type.VarType;
import stella.utils.Utils;

import java.util.List;

public class DotTuple extends Expr {

  public Expr tuple;
  public int label;

  public DotTuple(Expr tuple, int label) {
    this.tuple = tuple;
    this.label = label;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    var exprType = tuple.infer(context);
    if (!(exprType instanceof TupleType tupleType)) throw new NotATupleException(tuple, exprType);
    if (tupleType.size() < label) throw new TupleIndexOutOfBoundsException(tuple, label);
    Utils.checkTypeInExpr(expected, tupleType.get(label), this, context.structuralSubtyping);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    var exprType = tuple.infer(context);
    if (!(exprType instanceof TupleType tupleType)) throw new NotATupleException(tuple, exprType);
    return tupleType.get(label);
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    var t = tuple.collectConstraints(context, constraints);
    if (!(t instanceof TupleType tupleType)) {
      if (t instanceof VarType tx) {
        if (label < 0 || label > 2) throw new TupleIndexOutOfBoundsException(tuple, label);
        var x = new VarType();
        var x2 = new VarType();
        Type tx2 = switch (label) {
          case 1 -> new TupleType(List.of(x, x2));
          case 2 -> new TupleType(List.of(x2, x));
          default -> throw new TupleIndexOutOfBoundsException(tuple, label);
        };
        constraints.add(new Constraint(tx, tx2));
        return x;
      } else throw new NotATupleException(tuple, t);
    } else {
      if (tupleType.size() < label) throw new TupleIndexOutOfBoundsException(tuple, label);
      return tupleType.get(label);
    }
  }


  @Override
  public String toString() {
    return "%s.%s".formatted(tuple, label);
  }
}
