package stella.expr;

import stella.checker.Context;
import stella.exception.TypeCheckingException;
import stella.type.Type;
import stella.type.Types;
import stella.utils.Utils;

public class ConstNat extends Expr {

  public int value;

  public ConstNat(int value) {
    this.value = value;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    Utils.checkTypeInExpr(expected, Types.NAT, this, context.structuralSubtyping);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    return Types.NAT;
  }


  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
