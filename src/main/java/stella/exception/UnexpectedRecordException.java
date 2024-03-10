package stella.exception;

import stella.expr.Expr;
import stella.type.Type;

public class UnexpectedRecordException extends TypeCheckingException {
  public UnexpectedRecordException(Type expected, Expr record) {
    super(
        """
            ERROR_UNEXPECTED_RECORD:
              expected type
                %s
              for record
                %s
            """.formatted(expected, record)
    );
  }
}
