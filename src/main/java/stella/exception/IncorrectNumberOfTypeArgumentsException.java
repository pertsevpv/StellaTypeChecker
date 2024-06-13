package stella.exception;

import stella.expr.Expr;

public class IncorrectNumberOfTypeArgumentsException extends TypeCheckingException {

  public IncorrectNumberOfTypeArgumentsException(Expr expr, int expected, int actual) {
    super(
        """
            ERROR_INCORRECT_NUMBER_OF_TYPE_ARGUMENTS
              Incorrect number of type arguments
              expected:
                %s
              but got:
                %s
              in
                %s
            """.formatted(expected, actual, expr)
    );
  }
}
