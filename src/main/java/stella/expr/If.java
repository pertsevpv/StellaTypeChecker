package stella.expr;

import stella.checker.Gamma;
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
  public void checkTypes(Gamma gamma, Type expected) throws TypeCheckingException {
    cond.checkTypes(gamma, Types.BOOL);
    thenExpr.checkTypes(gamma, expected);
    elseExpr.checkTypes(gamma, expected);
  }

  @Override
  public Type infer(Gamma gamma) throws TypeCheckingException {
    cond.checkTypes(gamma, Types.BOOL);
    var thenType = thenExpr.infer(gamma);
    elseExpr.checkTypes(gamma, thenType);
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
