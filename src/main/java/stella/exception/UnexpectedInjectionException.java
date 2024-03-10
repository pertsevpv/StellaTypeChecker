package stella.exception;

import stella.expr.Expr;
import stella.type.Type;

public class UnexpectedInjectionException extends TypeCheckingException{
  public UnexpectedInjectionException(Type type, Expr expr) {
    super(
        """
            ERROR_UNEXPECTED_INJECTION
            unexpected type
              %s
            expected for
              %s
            """.formatted(type, expr)
    );
  }
}
