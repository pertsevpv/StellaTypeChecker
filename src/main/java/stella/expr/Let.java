package stella.expr;

import stella.checker.Gamma;
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
  public void checkTypes(Gamma gamma, Type expected) throws TypeCheckingException {
    Gamma newG = new Gamma();
    newG.parent = gamma;
    newG.put(var, rhs.infer(gamma));
    body.checkTypes(newG, expected);
  }

  @Override
  public Type infer(Gamma gamma) throws TypeCheckingException {
    Gamma newG = new Gamma();
    newG.parent = gamma;
    newG.put(var, rhs.infer(gamma));
    return body.infer(newG);
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
