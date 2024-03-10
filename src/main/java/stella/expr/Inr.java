package stella.expr;

import stella.checker.Gamma;
import stella.exception.AmbiguousSumTypeException;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedInjectionException;
import stella.pattern.Pattern;
import stella.type.SumType;
import stella.type.Type;

public class Inr extends Expr {

  public Expr expr;

  public Inr(Expr expr) {
    this.expr = expr;
  }

  @Override
  public void checkTypes(Gamma gamma, Type expected) throws TypeCheckingException {
    if (!(expected instanceof SumType sumType))
      throw new UnexpectedInjectionException(expected, this);
    expr.checkTypes(gamma, sumType.right);
  }

  @Override
  public Type infer(Gamma gamma) throws TypeCheckingException {
    throw new AmbiguousSumTypeException(this);
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return expr.withPattern(pattern, to);
  }

  @Override
  public String toString() {
    return "inr(%s)".formatted(expr);
  }
}
