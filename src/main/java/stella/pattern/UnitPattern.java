package stella.pattern;

import stella.exception.UnexpectedPatternForTypeException;
import stella.expr.Expr;
import stella.expr.Unit;
import stella.type.Type;
import stella.type.Types;
import stella.utils.Pair;

import java.util.List;

public class UnitPattern extends Pattern {

  @Override
  public String toString() {
    return "unit";
  }

  @Override
  boolean match(Expr expr) {
    return expr instanceof Unit;
  }

  @Override
  public void checkType(Type expected, List<Pair<String, Type>> collected) throws UnexpectedPatternForTypeException {
    if (expected != Types.UNIT) throw new UnexpectedPatternForTypeException(this, expected);
  }
}
