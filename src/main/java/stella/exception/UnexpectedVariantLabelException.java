package stella.exception;

import stella.expr.Expr;
import stella.type.Type;

public class UnexpectedVariantLabelException extends TypeCheckingException {
  public UnexpectedVariantLabelException(String label, Type variant) {
    super(
        """
            ERROR_UNEXPECTED_VARIANT_LABEL:
              variant
                %s
              doesn't contains label
                %s
            """.formatted(variant, label)
    );
  }
}
