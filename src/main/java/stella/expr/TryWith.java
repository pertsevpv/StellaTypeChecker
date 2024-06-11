package stella.expr;

import stella.checker.Context;
import stella.exception.TypeCheckingException;
import stella.type.Type;

public class TryWith extends Expr {

  public Expr tryExpr, fallbackExpr;

  public TryWith(Expr tryExpr, Expr fallbackExpr) {
    this.tryExpr = tryExpr;
    this.fallbackExpr = fallbackExpr;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    tryExpr.checkTypes(context, expected);
    fallbackExpr.checkTypes(context, expected);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    var type = tryExpr.infer(context);
    fallbackExpr.checkTypes(context, type);
    return type;
  }


  @Override
  public String toString() {
    return "try {%s} with {%s}".formatted(tryExpr, fallbackExpr);
  }
}
