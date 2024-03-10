package stella.utils;

import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedTypeForExpressionException;
import stella.expr.Expr;
import stella.type.Type;

import java.util.function.Supplier;

public class Utils {

  public static Type checkTypeInExpr(Type expected, Type got, Expr expr) throws UnexpectedTypeForExpressionException {
    if (!expected.equals(got)) throw new UnexpectedTypeForExpressionException(expected, got, expr);
    return expected;
  }

  public static void checkTypes(Type expected, Type got, Expr expr, Supplier<TypeCheckingException> supplier) {
    if (expected == null) return;
    if (!expected.equals(got)) supplier.get();
  }

}
