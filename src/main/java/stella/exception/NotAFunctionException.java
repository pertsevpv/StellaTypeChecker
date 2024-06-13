package stella.exception;

import stella.expr.Expr;
import stella.type.Type;

public class NotAFunctionException extends TypeCheckingException {

  public NotAFunctionException(Expr apply, Expr fun, Type got) {
    super("""
        ERROR_NOT_A_FUNCTION:
          in expr
            %s
          %s expected to have 1-arg func type, but got:
            %s
        """.formatted(apply, fun, got)
    );
  }

  public NotAFunctionException(Expr apply, Expr fun) {
    super("""
        ERROR_NOT_A_FUNCTION:
          in expr
            %s
          %s expected to have 1-arg func type
        """.formatted(apply, fun)
    );
  }
}
