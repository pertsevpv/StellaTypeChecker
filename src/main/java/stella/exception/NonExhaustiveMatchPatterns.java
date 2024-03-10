package stella.exception;

import stella.type.Type;

public class NonExhaustiveMatchPatterns extends TypeCheckingException {
  public NonExhaustiveMatchPatterns(Type type) {
    super(
        """
            ERROR_NONEXHAUSTIVE_MATCH_PATTERNS
              not enough cases for type
                %s
            """.formatted(type)
    );
  }
}
