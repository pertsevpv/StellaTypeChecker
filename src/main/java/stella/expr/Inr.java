package stella.expr;

import stella.checker.Context;
import stella.exception.AmbiguousSumTypeException;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedInjectionException;
import stella.type.SumType;
import stella.type.Type;
import stella.type.Types;

public class Inr extends Expr {

  public Expr expr;

  public Inr(Expr expr) {
    this.expr = expr;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    if (!(expected instanceof SumType sumType))
      if (context.structuralSubtyping && expected == Types.TOP) return;
      else throw new UnexpectedInjectionException(expected, this);
    expr.checkTypes(context, sumType.right);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    if (context.ambiguousTypeAsBottom) return new SumType(Types.BOTTOM, expr.infer(context));
    throw new AmbiguousSumTypeException(this);
  }


  @Override
  public String toString() {
    return "inr(%s)".formatted(expr);
  }
}
