package stella.exception;

import stella.type.Type;

public class IllegalExpectedTypeArgumentException extends IllegalArgumentException {
  public IllegalExpectedTypeArgumentException(Type type) {
    super("Illegal expected type: " + type);
  }
}
