package stella.expr;

import stella.checker.Context;
import stella.exception.TypeCheckingException;
import stella.pattern.Pattern;
import stella.pattern.VarPattern;
import stella.type.Type;

public class Let extends Expr {

  String var;
  Expr rhs, body;

  public Let(Pattern pattern, Expr rhs, Expr body) {
    if (!(pattern instanceof VarPattern varr)) throw new UnsupportedOperationException("#let-patterns is not supported yet");
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
  public Expr withPattern(Pattern pattern, Expr to) {
    return new Let(
        pattern,  // todo apply pattern in another pattern
        rhs.withPattern(pattern, to),
        body.withPattern(pattern, to)
    );
  }

  @Override
  public String toString() {
    return "let %s = %s in %s".formatted(var, rhs, body);
  }
}
