package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.AmbiguousVariantException;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedVariantException;
import stella.type.Type;
import stella.type.Types;
import stella.type.VariantType;

import java.util.List;

public class Variant extends Expr {

  public String label;
  public Expr expr;

  public Variant(String label, Expr expr) {
    this.label = label;
    this.expr = expr;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    if (!(expected instanceof VariantType variantType))
      if (context.structuralSubtyping && expected == Types.TOP) return;
      else throw new UnexpectedVariantException(expected, this);
    expr.checkTypes(context, variantType.get(label));
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    if (context.ambiguousTypeAsBottom) return Types.BOTTOM;
    throw new AmbiguousVariantException(this);
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    return expr.collectConstraints(context, constraints);
  }

  @Override
  public String toString() {
    return "<%s = %s>".formatted(label, expr);
  }
}
