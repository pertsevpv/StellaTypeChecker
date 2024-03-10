package stella.exception;

import stella.expr.Expr;

public class IncorrectNumberOfArguments extends TypeCheckingException {
  public IncorrectNumberOfArguments(Expr expr, int exp, int got) {
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
