package stella.expr;

import stella.checker.Context;
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
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    expr.checkTypes(context, asc);
    Utils.checkTypeInExpr(expected, asc, this, context.structuralSubtyping);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    expr.checkTypes(context, asc);
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
