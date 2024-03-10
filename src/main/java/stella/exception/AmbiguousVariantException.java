package stella.exception;

import stella.expr.Expr;

public class AmbiguousVariantException extends TypeCheckingException {
  public AmbiguousVariantException(Expr expr) {
    super(
        """
            ERROR_AMBIGUOUS_VARIANT
            ambitious variant for expr:
              %s
            """.formatted(expr)
    );
  }
}
