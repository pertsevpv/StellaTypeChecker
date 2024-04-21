package stella.exception;

import stella.expr.Expr;

public class UnexpectedNumberOfParametersInLambdaException extends TypeCheckingException {
  public UnexpectedNumberOfParametersInLambdaException(Expr expr, int exp, int got) {
    super(
        """
            ERROR_UNEXPECTED_NUMBER_OF_PARAMETERS_IN_LAMBDA
              Unexpected number of parameters in
                %s
              expected
                %s
              but got
                %s
            """.formatted(expr, exp, got)
    );
  }
}
