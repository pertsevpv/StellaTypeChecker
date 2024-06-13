package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.TypeCheckingException;
import stella.type.Type;

import java.util.List;

public abstract class Expr {

  public abstract void checkTypes(Context context, Type expected) throws TypeCheckingException;

  public abstract Type infer(Context context) throws TypeCheckingException;

  public abstract Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException;

}
