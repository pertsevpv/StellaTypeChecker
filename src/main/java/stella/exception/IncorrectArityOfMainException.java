package stella.exception;

public class IncorrectArityOfMainException extends TypeCheckingException {

  public IncorrectArityOfMainException(int arity) {
    super("""
        ERROR_INCORRECT_ARITY_OF_MAIN:
          %s
        """.formatted(arity)
    );
  }

}
