package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.TypeCheckingException;
import stella.type.Type;
import stella.type.Types;
import stella.utils.Utils;

import java.util.List;

public class ConstBool extends Expr {

  public boolean value;

  public ConstBool(boolean value) {
    this.value = value;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    Utils.checkTypeInExpr(expected, Types.BOOL, this, context.structuralSubtyping);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    return Types.BOOL;
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    return Types.BOOL;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
}
