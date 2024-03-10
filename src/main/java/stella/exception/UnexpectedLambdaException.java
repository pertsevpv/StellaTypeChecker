package stella.exception;

import stella.expr.Expr;
import stella.type.Type;

public class UnexpectedLambdaException extends TypeCheckingException {
  public UnexpectedLambdaException(Type exp, Expr abs) {
    super(
        """
            ERROR_UNEXPECTED_LAMBDA:
              expected type
                %s
              for record
                %s
            """.formatted(exp, abs)
    );
  }
}
