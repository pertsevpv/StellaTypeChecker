package stella.exception;

public class AmbiguousPanicType extends TypeCheckingException {
  public AmbiguousPanicType() {
    super("""
        ERROR_AMBIGUOUS_PANIC_TYPE
        """);
  }
}
