package stella.expr;

import stella.checker.Gamma;
import stella.exception.TypeCheckingException;
import stella.pattern.Pattern;
import stella.type.Type;
import stella.type.Types;


public class Sequence extends Expr {

  Expr expr1, expr2;

  public Sequence(Expr expr1, Expr expr2) {
    this.expr1 = expr1;
    this.expr2 = expr2;
  }

  @Override
  public void checkTypes(Gamma gamma, Type expected) throws TypeCheckingException {
    expr1.checkTypes(gamma, Types.UNIT);
    expr2.checkTypes(gamma, expected);
  }

  @Override
  public Type infer(Gamma gamma) throws TypeCheckingException {
    expr1.infer(gamma);
    return expr2.infer(gamma);
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return new Sequence(
        expr1.withPattern(pattern, to),
        expr2.withPattern(pattern, to)
    );
  }

  @Override
  public String toString() {
    return "%s;%s".formatted(expr1, expr2);
  }
}
