package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.TypeCheckingException;
import stella.type.Type;
import stella.type.Types;

import java.util.List;

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
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    var t1 = cond.collectConstraints(context, constraints);
    var t2 = thenExpr.collectConstraints(context, constraints);
    var t3 = elseExpr.collectConstraints(context, constraints);
    constraints.add(new Constraint(t1, Types.BOOL));
    constraints.add(new Constraint(t2, t3));
    return t2;
  }

  @Override
  public String toString() {
    return "if %s then %s else %s".formatted(cond, thenExpr, elseExpr);
  }
}
