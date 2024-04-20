package stella.expr;

import stella.checker.Context;
import stella.exception.TypeCheckingException;
import stella.pattern.Pattern;
import stella.type.Type;
import stella.type.Types;

public class If extends Expr {

  Expr cond, thenExpr, elseExpr;

  public If(Expr cond, Expr thenExpr, Expr elseExpr) {
    this.cond = cond;
    this.thenExpr = thenExpr;
    this.elseExpr = elseExpr;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    cond.checkTypes(context, Types.BOOL);
    thenExpr.checkTypes(context, expected);
    elseExpr.checkTypes(context, expected);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    cond.checkTypes(context, Types.BOOL);
    var thenType = thenExpr.infer(context);
    elseExpr.checkTypes(context, thenType);
    return thenType;
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return new If(
        cond.withPattern(pattern, to),
        thenExpr.withPattern(pattern, to),
        elseExpr.withPattern(pattern, to)
    );
  }

  @Override
  public String toString() {
    return "if %s then %s else %s".formatted(cond, thenExpr, elseExpr);
  }
}
