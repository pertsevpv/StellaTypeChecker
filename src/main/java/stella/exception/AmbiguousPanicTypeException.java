package stella.exception;

public class AmbiguousPanicTypeException extends TypeCheckingException {
  public AmbiguousPanicTypeException() {
    super("""
        ERROR_AMBIGUOUS_PANIC_TYPE
        """);
  }
}
