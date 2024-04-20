package stella.expr;

import stella.checker.Context;
import stella.exception.NotAReferenceException;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedTypeForExpressionException;
import stella.pattern.Pattern;
import stella.type.RefType;
import stella.type.Type;
import stella.type.Types;

public class Assign extends Expr {

  Expr var, value;

  public Assign(Expr var, Expr value) {
    this.var = var;
    this.value = value;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    if (expected != Types.UNIT)
      throw new UnexpectedTypeForExpressionException(expected, Types.UNIT, this);
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
  public Expr withPattern(Pattern pattern, Expr to) {
    return null;
  }

  @Override
  public String toString() {
    return "%s := %s".formatted(var, value);
  }
}
