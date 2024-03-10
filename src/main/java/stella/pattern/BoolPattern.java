package stella.pattern;

import stella.exception.UnexpectedPatternForTypeException;
import stella.expr.ConstBool;
import stella.expr.Expr;
import stella.type.Type;
import stella.type.Types;
import stella.utils.Pair;

import java.util.List;

public class BoolPattern extends Pattern {

  public boolean value;

  public BoolPattern(boolean value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }
  @Override
  boolean match(Expr expr) {
    return expr instanceof ConstBool bool && value == bool.value;
  }

  @Override
  public void checkType(Type expected, List<Pair<String, Type>> collected) throws UnexpectedPatternForTypeException {
    if (expected != Types.BOOL) throw new UnexpectedPatternForTypeException(this, expected);
  }
}
