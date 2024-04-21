package stella.exception;

import stella.expr.Expr;

public class AmbiguousThrowTypeException extends TypeCheckingException {

  public AmbiguousThrowTypeException(Expr throwExpr) {
    super("""
        ERROR_AMBIGUOUS_THROW_TYPE
        Ambiguous throw type for:
          %s
        """.formatted(throwExpr));
  }
}
