package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.AmbiguousThrowTypeException;
import stella.exception.ExceptionTypeNotDeclaredException;
import stella.exception.TypeCheckingException;
import stella.type.Type;
import stella.type.Types;

import java.util.List;

public class Throw extends Expr {

  public Expr throwExpr;

  public Throw(Expr throwExpr) {
    this.throwExpr = throwExpr;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    if (context.exceptionType == null)
      throw new ExceptionTypeNotDeclaredException();
    throwExpr.checkTypes(context, context.exceptionType);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    if (context.ambiguousTypeAsBottom) return Types.BOTTOM;
    throw new AmbiguousThrowTypeException(this);
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    if (context.ambiguousTypeAsBottom) return Types.BOTTOM;
    throw new AmbiguousThrowTypeException(this);
  }

  @Override
  public String toString() {
    return "throw(%s)".formatted(throwExpr);
  }
}
