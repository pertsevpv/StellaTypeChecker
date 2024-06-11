package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.TypeCheckingException;
import stella.type.Type;

import java.util.List;

public class TryWith extends Expr {

  public Expr tryExpr, fallbackExpr;

  public TryWith(Expr tryExpr, Expr fallbackExpr) {
    this.tryExpr = tryExpr;
    this.fallbackExpr = fallbackExpr;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    tryExpr.checkTypes(context, expected);
    fallbackExpr.checkTypes(context, expected);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    var type = tryExpr.infer(context);
    fallbackExpr.checkTypes(context, type);
    return type;
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    var tt = tryExpr.collectConstraints(context, constraints);
    var tf = fallbackExpr.collectConstraints(context, constraints);
    constraints.add(new Constraint(tt, tf));
    return tt;
  }

  @Override
  public String toString() {
    return "try {%s} with {%s}".formatted(tryExpr, fallbackExpr);
  }
}
