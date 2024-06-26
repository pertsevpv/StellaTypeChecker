package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.AmbiguousPanicTypeException;
import stella.exception.TypeCheckingException;
import stella.type.Type;
import stella.type.Types;

import java.util.List;

public class Panic extends Expr {

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {

  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    if (context.ambiguousTypeAsBottom) return Types.BOTTOM;
    throw new AmbiguousPanicTypeException();
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    if (context.ambiguousTypeAsBottom) return Types.BOTTOM;
    throw new AmbiguousPanicTypeException();
  }

}
