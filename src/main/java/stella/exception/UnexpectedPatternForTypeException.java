package stella.exception;

import stella.pattern.Pattern;
import stella.type.Type;

public class UnexpectedPatternForTypeException extends TypeCheckingException {
  public UnexpectedPatternForTypeException(Pattern pattern, Type type) {
    super(
        """
            ERROR_UNEXPECTED_PATTERN_FOR_TYPE
              unexpected pattern
                %s
              for type
                %s
            """.formatted(pattern, type)
    );
  }
}
