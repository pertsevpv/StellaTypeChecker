package stella.exception;

import stella.expr.Expr;

public class AmbiguousListException extends TypeCheckingException{
  public AmbiguousListException(Expr expr) {
    super(
        """
            ERROR_AMBIGUOUS_LIST:
              cannot get type for list
                %s
            """.formatted(expr)
    );
  }
}
