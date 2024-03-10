package stella.exception;

import stella.expr.Expr;
import stella.type.Type;

public class NotATupleException extends TypeCheckingException {

  public NotATupleException(Expr expr, Type type) {
    super("""
        ERROR_NOT_A_TUPLE:
          %s
        has type:
          %s
        """.formatted(expr, type)
    );
  }

}
