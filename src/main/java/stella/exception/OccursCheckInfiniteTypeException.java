package stella.exception;

import stella.constraint.Constraint;

public class OccursCheckInfiniteTypeException extends TypeCheckingException {
  public OccursCheckInfiniteTypeException(Constraint constraint) {
    super("""
        ERROR_OCCURS_CHECK_INFINITE_TYPE
          Occurs check infinite type for constraint:
            %s
        """.formatted(constraint)
    );
  }
}
