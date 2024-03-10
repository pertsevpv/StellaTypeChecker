package stella.exception;

import stella.expr.Expr;
import stella.type.Type;

public class UnexpectedTupleException extends TypeCheckingException{
  public UnexpectedTupleException(Type expected, Expr tuple) {
    super(
        """
            ERROR_UNEXPECTED_TUPLE:
              expected type
                %s
              for tuple
                %s
            """.formatted(expected, tuple)
    );
  }
}
