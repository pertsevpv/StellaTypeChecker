package stella.exception;

import stella.expr.Expr;
import stella.type.Type;

public class UnexpectedTupleLengthException extends TypeCheckingException{
  public UnexpectedTupleLengthException(Expr tuple, int expected, int got) {
    super(
        """
            ERROR_UNEXPECTED_TUPLE_LENGTH:
              unexpected tuple length for tuple:
                %s
              expected:
                %d
              but got:
                %d
            """.formatted(tuple, expected, got)
    );
  }

  public UnexpectedTupleLengthException(Type tuple, int expected, int got) {
    super(
        """
            ERROR_UNEXPECTED_TUPLE_LENGTH:
              unexpected tuple length for tuple:
                %s
              expected:
                %d
              but got:
                %d
            """.formatted(tuple, expected, got)
    );
  }
}
