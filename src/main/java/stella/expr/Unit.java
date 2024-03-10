package stella.expr;

import stella.checker.Gamma;
import stella.exception.TypeCheckingException;
import stella.pattern.Pattern;
import stella.pattern.UnitPattern;
import stella.type.Type;
import stella.type.Types;
import stella.utils.Utils;

public class Unit extends Expr {

  @Override
  public void checkTypes(Gamma gamma, Type expected) throws TypeCheckingException {
    Utils.checkTypeInExpr(expected, Types.UNIT, this);
  }

  @Override
  public Type infer(Gamma gamma) throws TypeCheckingException {
    return Types.UNIT;
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    if (pattern instanceof UnitPattern) return to;
    else return this;
  }

  @Override
  public String toString() {
    return "unit";
  }
}
