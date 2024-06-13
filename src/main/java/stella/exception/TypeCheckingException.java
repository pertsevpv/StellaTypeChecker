package stella.exception;

public abstract class TypeCheckingException extends RuntimeException {
  public TypeCheckingException(String msg) {
    super(msg);
  }
}
