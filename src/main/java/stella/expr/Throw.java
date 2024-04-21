package stella.expr;

import stella.checker.Context;
import stella.exception.AmbiguousThrowTypeException;
import stella.exception.ExceptionTypeNotDeclaredException;
import stella.exception.TypeCheckingException;
import stella.pattern.Pattern;
import stella.type.Type;
import stella.type.Types;

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
  public Expr withPattern(Pattern pattern, Expr to) {
    return null;
  }

  @Override
  public String toString() {
    return "throw(%s)".formatted(throwExpr);
  }
}
