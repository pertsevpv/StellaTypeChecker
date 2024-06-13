package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.TypeCheckingException;
import stella.type.Type;
import stella.utils.Utils;

import java.util.List;

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
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    var t1 = asc;
    var t2 = expr.collectConstraints(context, constraints);
    constraints.add(new Constraint(t1, t2));
    return t1;
  }

  @Override
  public String toString() {
    return "%s as %s".formatted(expr, asc);
  }
}
