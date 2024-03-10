package stella.exception;

import stella.expr.Expr;

public class TupleIndexOutOfBoundsException extends TypeCheckingException{
  public TupleIndexOutOfBoundsException(Expr tuple, int index) {
    super(
        """
            ERROR_TUPLE_INDEX_OUT_OF_BOUNDS:
              wrong index:
                %d
              for tuple
                %s
            """.formatted(index, tuple)
    );
  }
}
