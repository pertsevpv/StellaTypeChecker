package stella.exception;

public class UndefinedVariableException extends TypeCheckingException {
  public UndefinedVariableException(String var) {
    super(
        """
            ERROR_UNDEFINED_VARIABLE:
              %s
            """.formatted(var)
    );
  }
}
