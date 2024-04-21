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
      return expected.isSubtypeOf(actual);
    } else return expected.equals(actual);
  }

  public static Type checkTypeInExpr(
      Type expected, Type actual,
      Expr expr, boolean structuralSubtyping
  ) throws TypeCheckingException {
    if (actual == null) return expected;
    if (structuralSubtyping) {
      if (!actual.isSubtypeOf(expected)) {
        System.out.println();
        throw new UnexpectedSubtypeException(expected, actual, expr);
      }
    } else {
      if (!actual.equals(expected)) throw new UnexpectedTypeForExpressionException(expected, actual, expr);
    }
    return expected;
  }

}
