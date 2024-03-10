package stella.pattern;

import stella.exception.TypeCheckingException;
import stella.expr.Expr;
import stella.expr.Succ;
import stella.type.Type;
import stella.type.Types;
import stella.utils.Pair;

import java.util.List;

public class SuccPattern extends Pattern {

  public Pattern pattern;

  public SuccPattern(Pattern pattern) {
    this.pattern = pattern;
  }

  @Override
  public String toString() {
    return "succ(%s)".formatted(pattern);
  }

  @Override
  boolean match(Expr expr) {
    return expr instanceof Succ succ && pattern.match(succ.expr);
  }

  @Override
  public void checkType(Type expected, List<Pair<String, Type>> collected) throws TypeCheckingException {
    pattern.checkType(Types.NAT, collected);
  }
}
