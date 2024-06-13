package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.AmbiguousSumTypeException;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedInjectionException;
import stella.type.*;

import java.util.List;

public class Inl extends Expr {

  public Expr expr;

  public Inl(Expr expr) {
    this.expr = expr;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    if (!(expected instanceof SumType sumType)) {
      if (expected instanceof UniVarType) return;
      if (context.structuralSubtyping && expected == Types.TOP) return;
      else throw new UnexpectedInjectionException(expected, this);
    }
    expr.checkTypes(context, sumType.left);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    if (context.ambiguousTypeAsBottom) return new SumType(expr.infer(context), Types.BOTTOM);
    throw new AmbiguousSumTypeException(this);
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    var t = expr.collectConstraints(context, constraints);
    var tx = new VarType();
    return new SumType(t, tx);
  }

  @Override
  public String toString() {
    return "inl(%s)".formatted(expr);
  }
}
