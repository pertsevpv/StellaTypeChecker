package stella.exception;

import stella.expr.Expr;
import stella.type.Type;

public class UnexpectedTypeForExpressionException extends TypeCheckingException {

  public UnexpectedTypeForExpressionException(Type expected, Type got, Expr expr) {
    super(
        """
            ERROR_UNEXPECTED_TYPE_FOR_EXPRESSION:
              ожидается тип
                %s
              но получен тип
                %s
              для выражения
                %s
            """.formatted(expected, got, expr)
    );
  }

}
