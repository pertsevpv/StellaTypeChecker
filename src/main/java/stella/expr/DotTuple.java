package stella.expr;

import stella.checker.Context;
import stella.exception.NotATupleException;
import stella.exception.TupleIndexOutOfBoundsException;
import stella.exception.TypeCheckingException;
import stella.type.TupleType;
import stella.type.Type;
import stella.utils.Utils;

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
  public String toString() {
    return "%s.%s".formatted(tuple, label);
  }
}
