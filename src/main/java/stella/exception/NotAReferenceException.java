package stella.exception;

import stella.expr.Expr;
import stella.type.Type;

public class NotAReferenceException extends TypeCheckingException {

  public NotAReferenceException(Expr ref, Type got) {
    super("""
        ERROR_NOT_A_REFERENCE
          expected ref type for expr
            %s
          but got
            %s
        """.formatted(ref, got));
  }

  public NotAReferenceException(Expr ref) {
    super("""
        ERROR_NOT_A_REFERENCE
          expected ref type for expr
            %s
        """.formatted(ref));
  }
}
