package stella.utils;

import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedSubtypeException;
import stella.exception.UnexpectedTypeForExpressionException;
import stella.expr.Expr;
import stella.type.Type;


public class Utils {

  public static boolean equals(
      Type expected, Type actual,
      boolean structuralSubtyping
  ) throws TypeCheckingException {
    if (actual == null) return true;
    if (structuralSubtyping) {
      expected.isSubtypeOf(actual);
      return true;
    } else return expected.equals(actual);
  }

  public static Type checkTypeInExpr(
      Type expected, Type actual,
      Expr expr, boolean structuralSubtyping
  ) throws TypeCheckingException {
    if (actual == null) return expected;
    if (structuralSubtyping) {
      actual.isSubtypeOf(expected);
    } else {
      if (!actual.equals(expected)) throw new UnexpectedTypeForExpressionException(expected, actual, expr);
    }
    return expected;
  }

}
