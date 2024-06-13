package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.TypeCheckingException;
import stella.pattern.Pattern;
import stella.pattern.VarPattern;
import stella.type.Type;

import java.util.List;

public class Let extends Expr {

  String var;
  Expr rhs, body;

  public Let(Pattern pattern, Expr rhs, Expr body) {
    if (!(pattern instanceof VarPattern varr))
      throw new UnsupportedOperationException("#let-patterns is not supported yet");
    this.var = varr.var;
    this.rhs = rhs;
    this.body = body;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    context.enterGamma();
    context.put(var, rhs.infer(context));
    body.checkTypes(context, expected);
    context.exitGamma();
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    context.enterGamma();
    context.put(var, rhs.infer(context));
    var res = body.infer(context);
    context.exitGamma();
    return res;
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    context.enterGamma();
    context.put(var, rhs.collectConstraints(context, constraints));
    var res = body.collectConstraints(context, constraints);
    context.exitGamma();
    return res;
  }

  @Override
  public String toString() {
    return "let %s = %s in %s".formatted(var, rhs, body);
  }
}
