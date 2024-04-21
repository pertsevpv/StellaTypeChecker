package stella.exception;

public class ExceptionTypeNotDeclaredException extends TypeCheckingException {

  public ExceptionTypeNotDeclaredException() {
    super("""
        ERROR_EXCEPTION_TYPE_NOT_DECLARED
        Exception type not declared
        """
    );
  }
}
