package stella.exception;

import stella.expr.Expr;

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
}
