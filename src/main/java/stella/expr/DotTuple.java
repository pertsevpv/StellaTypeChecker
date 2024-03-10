package stella.expr;

import stella.checker.Gamma;
import stella.exception.NotATupleException;
import stella.exception.TupleIndexOutOfBoundsException;
import stella.exception.TypeCheckingException;
import stella.pattern.Pattern;
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
  public void checkTypes(Gamma gamma, Type expected) throws TypeCheckingException {
    var exprType = tuple.infer(gamma);
    if (!(exprType instanceof TupleType tupleType)) throw new NotATupleException(tuple, exprType);
    if (tupleType.size() < label) throw new TupleIndexOutOfBoundsException(tuple, label);
    Utils.checkTypeInExpr(expected, tupleType.get(label), this);
  }

  @Override
  public Type infer(Gamma gamma) throws TypeCheckingException {
    var exprType = tuple.infer(gamma);
    if (!(exprType instanceof TupleType tupleType)) throw new NotATupleException(tuple, exprType);
    return tupleType.get(label);
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return new DotTuple(tuple.withPattern(pattern, to), label);
  }

  @Override
  public String toString() {
    return "%s.%s".formatted(tuple, label);
  }
}
