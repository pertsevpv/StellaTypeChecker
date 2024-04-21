package stella.exception;

import stella.expr.Expr;
import stella.type.Type;

public class UnexpectedSubtypeException extends TypeCheckingException {

  public UnexpectedSubtypeException(Type actual, Type expected, Expr expr) {
    super("""
        ERROR_UNEXPECTED_SUBTYPE
        type
          %s
        is not subtype
          %s
        in expr
          %s
        """.formatted(actual, expected, expr)
    );
  }

  public UnexpectedSubtypeException(Type actual, Type expected) {
    super("""
        ERROR_UNEXPECTED_SUBTYPE
        type
          %s
        is not subtype
          %s
        """.formatted(actual, expected)
    );
  }
}
