package stella.exception;

import stella.expr.Expr;
import stella.type.Type;

public class NotARecordException extends TypeCheckingException {

  public NotARecordException(Expr expr, Type type) {
    super("""
        ERROR_NOT_A_RECORD:
          %s
        has type:
          %s
        """.formatted(expr, type)
    );
  }

}
