package stella.exception;

import stella.expr.Expr;

public class IncorrectNumberOfArgumentsException extends TypeCheckingException {
  public IncorrectNumberOfArgumentsException(Expr expr, int exp, int got) {
    super(
        """
            ERROR_INCORRECT_NUMBER_OF_ARGUMENTS
              incorrect number of arguments for application:
                %s
              expected:
                %s
              got
                %s
            """.formatted(expr, exp, got)
    );
  }
}
