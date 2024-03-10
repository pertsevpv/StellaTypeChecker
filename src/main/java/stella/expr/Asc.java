package stella.expr;

import stella.checker.Gamma;
import stella.exception.TypeCheckingException;
import stella.pattern.Pattern;
import stella.type.Type;
import stella.utils.Utils;

public class Asc extends Expr {

  Expr expr;
  Type asc;

  public Asc(Expr expr, Type asc) {
    this.expr = expr;
    this.asc = asc;
  }

  @Override
  public void checkTypes(Gamma gamma, Type expected) throws TypeCheckingException {
    expr.checkTypes(gamma, asc);
    Utils.checkTypeInExpr(expected, asc, this);
  }

  @Override
  public Type infer(Gamma gamma) throws TypeCheckingException {
    expr.checkTypes(gamma, asc);
    return asc;
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return new Asc(expr, asc);
  }

  @Override
  public String toString() {
    return "%s as %s".formatted(expr, asc);
  }
}
