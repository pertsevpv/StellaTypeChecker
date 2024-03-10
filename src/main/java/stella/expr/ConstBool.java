package stella.expr;

import stella.checker.Gamma;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedTypeForExpressionException;
import stella.pattern.BoolPattern;
import stella.pattern.Pattern;
import stella.type.Type;
import stella.type.Types;
import stella.utils.Utils;

public class ConstBool extends Expr {

  public boolean value;

  public ConstBool(boolean value) {
    this.value = value;
  }

  @Override
  public void checkTypes(Gamma gamma, Type expected) throws UnexpectedTypeForExpressionException {
    Utils.checkTypeInExpr(expected, Types.BOOL, this);
  }

  @Override
  public Type infer(Gamma gamma) throws TypeCheckingException {
    return Types.BOOL;
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    if (pattern instanceof BoolPattern boolPattern &&
        value == boolPattern.value) return to;
    else return this;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
