package stella.exception;

import stella.type.RecordType;

public class UnexpectedFieldAccessException extends TypeCheckingException {

  public UnexpectedFieldAccessException(String label, RecordType record) {
    super(
        """
            ERROR_UNEXPECTED_FIELD_ACCESS:
              unexpected field access:
                %s
              for record type
                %s
            """.formatted(label, record)
    );
  }

}
