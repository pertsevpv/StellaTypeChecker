package stella.exception;

import stella.expr.Expr;
import stella.type.Type;

public class MissingRecordFieldsException extends TypeCheckingException{
  public MissingRecordFieldsException(Type expected, Expr record, String label) {
    super(
        """
            ERROR_UNEXPECTED_RECORD_FIELD:
              doesnt find field %s in record
                %s
              with expected type
                %s
            """.formatted(label, record, expected)
    );
  }
}
