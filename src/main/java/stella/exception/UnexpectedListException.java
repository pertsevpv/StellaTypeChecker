package stella.exception;

import stella.expr.Expr;
import stella.type.Type;

public class UnexpectedListException extends TypeCheckingException {
  public UnexpectedListException(Type type, Expr expr) {
    super(
        """
            ERROR_UNEXPECTED_LIST:
              expected type
                %s
              for list
                %s
            """.formatted(type, expr)
    );
  }
}
