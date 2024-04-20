package stella.expr;

import stella.checker.Context;
import stella.exception.TypeCheckingException;
import stella.pattern.Pattern;
import stella.pattern.SuccPattern;
import stella.type.Type;
import stella.type.Types;
import stella.utils.Utils;

public class Succ extends Expr {

  public Expr expr;

  public Succ(Expr expr) {
    this.expr = expr;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    Utils.checkTypeInExpr(expected, Types.NAT, this);
    expr.checkTypes(context, Types.NAT);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    expr.checkTypes(context, Types.NAT);
    return Types.NAT;
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    if (pattern instanceof SuccPattern succPattern) return withPattern(succPattern.pattern, to);
    else return this;
  }

  @Override
  public String toString() {
    return "succ(%s)".formatted(expr);
  }
}
