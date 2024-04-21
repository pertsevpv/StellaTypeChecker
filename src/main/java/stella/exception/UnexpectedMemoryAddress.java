package stella.exception;

import stella.expr.Expr;

public class UnexpectedMemoryAddress extends TypeCheckingException {

  public UnexpectedMemoryAddress(Expr expr) {
    super("""
        ERROR_UNEXPECTED_MEMORY_ADDRESS
          Unexpected memory address:
            %s
        """);
  }
}
