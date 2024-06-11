package stella.expr;

import stella.checker.Context;
import stella.exception.NotAReferenceException;
import stella.exception.TypeCheckingException;
import stella.type.RefType;
import stella.type.Type;
import stella.type.Types;
import stella.utils.Utils;

public class Assign extends Expr {

  Expr var, value;

  public Assign(Expr var, Expr value) {
    this.var = var;
    this.value = value;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    Utils.checkTypeInExpr(expected, Types.UNIT, this, context.structuralSubtyping);
    var varType = var.infer(context);
    if (!(varType instanceof RefType refType))
      throw new NotAReferenceException(var, varType);
    value.checkTypes(context, refType.refType);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    var varType = var.infer(context);
    if (!(varType instanceof RefType refType))
      throw new NotAReferenceException(var, varType);
    value.checkTypes(context, refType.refType);
    return Types.UNIT;
  }


  @Override
  public String toString() {
    return "%s := %s".formatted(var, value);
  }
}
