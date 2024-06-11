package stella.expr;

import stella.checker.Context;
import stella.exception.TypeCheckingException;
import stella.type.Type;
import stella.type.Types;


public class Sequence extends Expr {

  Expr expr1, expr2;

  public Sequence(Expr expr1, Expr expr2) {
    this.expr1 = expr1;
    this.expr2 = expr2;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    expr1.checkTypes(context, Types.UNIT);
    expr2.checkTypes(context, expected);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    expr1.infer(context);
    return expr2.infer(context);
  }


  @Override
  public String toString() {
    return "%s;%s".formatted(expr1, expr2);
  }
}
