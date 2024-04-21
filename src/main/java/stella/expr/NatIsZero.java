package stella.expr;

import stella.checker.Context;
import stella.exception.TypeCheckingException;
import stella.pattern.Pattern;
import stella.type.Type;
import stella.type.Types;
import stella.utils.Utils;

public class NatIsZero extends Expr {

  public Expr expr;

  public NatIsZero(Expr expr) {
    this.expr = expr;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    Utils.checkTypeInExpr(expected, Types.BOOL, this, context.structuralSubtyping);
    expr.checkTypes(context, Types.NAT);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    expr.checkTypes(context, Types.NAT);
    return Types.BOOL;
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return expr.withPattern(pattern, to);
  }

  @Override
  public String toString() {
    return "Nat::iszero(%s)".formatted(expr);
  }
}
