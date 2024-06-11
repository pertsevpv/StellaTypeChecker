package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.TypeCheckingException;
import stella.exception.UndefinedVariableException;
import stella.type.Type;
import stella.utils.Utils;

import java.util.List;

public class Var extends Expr {

  public String var;

  public Var(String var) {
    this.var = var;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    var type = context.get(var);
    if (type == null) throw new UndefinedVariableException(var);
    Utils.checkTypeInExpr(expected, type, this, context.structuralSubtyping);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    var type = context.get(var);
    if (type == null) throw new UndefinedVariableException(var);
    return type;
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    var type = context.get(var);
    if (type == null) throw new UndefinedVariableException(var);
    return type;
  }

  @Override
  public String toString() {
    return var;
  }
}
