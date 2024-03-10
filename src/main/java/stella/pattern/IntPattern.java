package stella.pattern;

import stella.exception.UnexpectedPatternForTypeException;
import stella.expr.ConstNat;
import stella.expr.Expr;
import stella.type.Type;
import stella.type.Types;
import stella.utils.Pair;

import java.util.List;

public class IntPattern extends Pattern {

  public int value;

  public IntPattern(int value) {
    this.value = value;
  }

  @Override
  public String toString() {
    return String.valueOf(value);
  }

  @Override
  boolean match(Expr expr) {
    return expr instanceof ConstNat nat && value == nat.value;
  }

  @Override
  public void checkType(Type expected, List<Pair<String, Type>> collected) throws UnexpectedPatternForTypeException {
    if (expected != Types.NAT) throw new UnexpectedPatternForTypeException(this, expected);
  }
}
