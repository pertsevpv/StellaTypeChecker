package stella.exception;

import stella.expr.Expr;
import stella.type.Type;

public class NotAListException extends TypeCheckingException {
  public NotAListException(Expr expr, Type type) {
    super(
        """
            ERROR_UNEXPECTED_LIST
              expected list for
                %s
              but got:
                %s
            """.formatted(expr, type)
    );
  }
}
