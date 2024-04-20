package stella.expr;

import stella.checker.Context;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedTypeForExpressionException;
import stella.pattern.IntPattern;
import stella.pattern.Pattern;
import stella.type.Type;
import stella.type.Types;
import stella.utils.Utils;

public class ConstNat extends Expr {

  public int value;

  public ConstNat(int value) {
    this.value = value;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws UnexpectedTypeForExpressionException {
    Utils.checkTypeInExpr(expected, Types.NAT, this);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    return Types.NAT;
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    if (pattern instanceof IntPattern intPattern &&
        value == intPattern.value) return to;
    else return this;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
