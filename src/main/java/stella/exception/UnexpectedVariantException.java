package stella.exception;

import stella.expr.Expr;
import stella.type.Type;

public class UnexpectedVariantException extends TypeCheckingException{
  public UnexpectedVariantException(Type expected, Expr variant) {
    super(
        """
            ERROR_UNEXPECTED_VARIANT:
              expected type
                %s
              for variant
                %s
            """.formatted(expected, variant)
    );
  }
}
