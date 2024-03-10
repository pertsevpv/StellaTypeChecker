package stella.expr;

import stella.checker.Gamma;
import stella.exception.TypeCheckingException;
import stella.pattern.Pattern;
import stella.type.Type;

public abstract class Expr {

  public abstract void checkTypes(Gamma gamma, Type expected) throws TypeCheckingException;
  public abstract Type infer(Gamma gamma) throws TypeCheckingException;

  public abstract Expr withPattern(Pattern pattern, Expr to);

}
