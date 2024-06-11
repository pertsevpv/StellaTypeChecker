package stella.expr;

import stella.checker.Context;
import stella.exception.TypeCheckingException;
import stella.type.Type;
import stella.type.Types;
import stella.utils.Utils;

public class Unit extends Expr {

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    Utils.checkTypeInExpr(expected, Types.UNIT, this, context.structuralSubtyping);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    return Types.UNIT;
  }


  @Override
  public String toString() {
    return "unit";
  }
}
