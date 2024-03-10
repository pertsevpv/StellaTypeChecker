package stella.pattern;

import stella.expr.Expr;
import stella.type.Type;
import stella.utils.Pair;

import java.util.List;

public class VarPattern extends Pattern {

  public String var;

  public VarPattern(String var) {
    this.var = var;
  }

  @Override
  public String toString() {
    return var;
  }

  @Override
  boolean match(Expr expr) {
    return true;
  }

  @Override
  public void checkType(Type expected, List<Pair<String, Type>> collected) {
    collected.add(new Pair<>(var, expected));
  }
}
