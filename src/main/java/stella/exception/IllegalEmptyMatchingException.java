package stella.exception;

import stella.expr.Expr;

public class IllegalEmptyMatchingException extends TypeCheckingException{
  public IllegalEmptyMatchingException(Expr match) {
    super(
        """
            ERROR_ILLEGAL_EMPTY_MATCHING
              match
                %s
              has no cases
            """.formatted(match)
    );
  }
}
