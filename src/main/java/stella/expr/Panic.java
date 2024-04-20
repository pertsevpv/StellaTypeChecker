package stella.expr;

import stella.checker.Context;
import stella.exception.AmbiguousPanicType;
import stella.exception.TypeCheckingException;
import stella.pattern.Pattern;
import stella.type.Type;

public class Panic extends Expr {

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {

  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    throw new AmbiguousPanicType();
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return null;
  }
}
