package stella.exception;

import stella.expr.Expr;

public class AmbiguousSumTypeException extends TypeCheckingException {
  public AmbiguousSumTypeException(Expr expr) {
    super(
        """
            ERROR_AMBIGUOUS_SUM_TYPE
            ambitious sum type for expr:
              %s
            """.formatted(expr)
    );
  }
}
