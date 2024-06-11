package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.TypeCheckingException;
import stella.type.Type;
import stella.type.Types;
import stella.utils.Utils;

import java.util.List;

public class Succ extends Expr {

  public Expr expr;

  public Succ(Expr expr) {
    this.expr = expr;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    Utils.checkTypeInExpr(expected, Types.NAT, this, context.structuralSubtyping);
    expr.checkTypes(context, Types.NAT);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    expr.checkTypes(context, Types.NAT);
    return Types.NAT;
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    var t = expr.collectConstraints(context, constraints);
    constraints.add(new Constraint(t, Types.NAT));
    return Types.NAT;
  }

  @Override
  public String toString() {
    return "succ(%s)".formatted(expr);
  }
}
