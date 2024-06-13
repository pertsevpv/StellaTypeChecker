package stella.exception;

public class UndefinedTypeVariableException extends TypeCheckingException {
  public UndefinedTypeVariableException(String typeName) {
    super(
        """
        ERROR_UNDEFINED_TYPE_VARIABLE
          Undefined type variable %s
        """.formatted(typeName)
    );
  }
}
