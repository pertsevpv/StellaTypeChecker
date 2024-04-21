package stella.exception;

import stella.expr.Expr;

public class AmbiguousReferenceType extends TypeCheckingException {

  public AmbiguousReferenceType(Expr expr) {
    super("""
        ERROR_AMBIGUOUS_REFERENCE_TYPE
          Ambiguous reference type:
            %s
        """.formatted(expr));
  }
}
