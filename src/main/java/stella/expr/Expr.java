package stella.expr;

import stella.checker.Context;
import stella.exception.TypeCheckingException;
import stella.type.Type;

public abstract class Expr {

  public abstract void checkTypes(Context context, Type expected) throws TypeCheckingException;

  public abstract Type infer(Context context) throws TypeCheckingException;

}
