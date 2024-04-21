package stella.expr;

import stella.checker.Context;
import stella.exception.AmbiguousThrowTypeException;
import stella.exception.ExceptionTypeNotDeclaredException;
import stella.exception.TypeCheckingException;
import stella.pattern.Pattern;
import stella.type.Type;

public class Throw extends Expr {

  public Expr throwExpr;

  public Throw(Expr throwExpr) {
    this.throwExpr = throwExpr;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    throwExpr.infer(context);
    if (context.exceptionType == null)
      throw new ExceptionTypeNotDeclaredException(expected);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    throw new AmbiguousThrowTypeException(this);
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return null;
  }
}
