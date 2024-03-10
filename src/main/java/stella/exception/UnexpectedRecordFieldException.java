package stella.exception;

import stella.expr.Expr;
import stella.type.Type;

public class UnexpectedRecordFieldException extends TypeCheckingException {
  public UnexpectedRecordFieldException(Type expected, Expr record, String label) {
    super(
        """
            ERROR_UNEXPECTED_RECORD_FIELD:
              find extra field %s in record
                %s
              with expected type
                %s
            """.formatted(label, record, expected)
    );
  }
}
