package stella.exception;

import stella.type.Type;

public class ExceptionTypeNotDeclaredException extends TypeCheckingException {

  public ExceptionTypeNotDeclaredException(Type type) {
    super("""
        ERROR_EXCEPTION_TYPE_NOT_DECLARED
        Exception type not declared
          %s
        """.formatted(type)
    );
  }

  public ExceptionTypeNotDeclaredException() {
    super("""
        ERROR_EXCEPTION_TYPE_NOT_DECLARED
        Exception type not declared
        """
    );
  }
}
