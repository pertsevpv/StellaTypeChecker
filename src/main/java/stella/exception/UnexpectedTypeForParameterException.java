package stella.exception;

import stella.expr.Expr;
import stella.type.Type;

public class UnexpectedTypeForParameterException extends TypeCheckingException {
  public UnexpectedTypeForParameterException(String paramName, Type expected, Type got, Expr expr) {
    super(
        """
            ERROR_UNEXPECTED_TYPE_FOR_PARAMETER:
              wrong type for param %s:
              expected:
                %s
              but got:
                %s
              in
                %s
            """
    );
  }
}
