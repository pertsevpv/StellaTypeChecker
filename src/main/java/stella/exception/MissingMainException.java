package stella.exception;

public class MissingMainException extends TypeCheckingException {

  public MissingMainException() {
    super(
        """
            ERROR_MISSING_MAIN:
            \tв программе отсутствует функция main
            """
    );
  }

}
