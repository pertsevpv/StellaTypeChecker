package stella.expr;

import stella.checker.Context;
import stella.exception.AmbiguousSumTypeException;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedInjectionException;
import stella.pattern.Pattern;
import stella.type.SumType;
import stella.type.Type;

public class Inl extends Expr {

  public Expr expr;

  public Inl(Expr expr) {
    this.expr = expr;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    if (!(expected instanceof SumType sumType))
      throw new UnexpectedInjectionException(expected, this);
    expr.checkTypes(context, sumType.left);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    throw new AmbiguousSumTypeException(this);
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return expr.withPattern(pattern, to);
  }

  @Override
  public String toString() {
    return "inl(%s)".formatted(expr);
  }
}
