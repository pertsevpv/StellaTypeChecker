package stella.exception;

import stella.expr.Expr;
import stella.type.Type;

public class NotAGenericFunctionException extends TypeCheckingException {

  public NotAGenericFunctionException(Expr apply, Expr fun, Type got) {
    super("""
        ERROR_NOT_A_GENERIC_FUNCTION:
          in expr
            %s
          %s expected to have gen func type, but got:
            %s
        """.formatted(apply, fun, got)
    );
  }

  public NotAGenericFunctionException(Expr apply, Expr fun) {
    super("""
        ERROR_NOT_A_GENERIC_FUNCTION:
          in expr
            %s
          %s expected to have gen func type
        """.formatted(apply, fun)
    );
  }
}
