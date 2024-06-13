package stella.pattern;

import stella.constraint.Constraint;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedPatternForTypeException;
import stella.expr.Expr;
import stella.expr.Succ;
import stella.type.Type;
import stella.type.Types;
import stella.type.VarType;
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
    if (expected != Types.NAT) throw new UnexpectedPatternForTypeException(this, expected);
    pattern.checkType(Types.NAT, collected);
  }

  @Override
  public void checkType(Type expected, List<Pair<String, Type>> collected, List<Constraint> constraints) throws TypeCheckingException {
    if (expected instanceof VarType) {
      constraints.add(new Constraint(expected, Types.NAT));
      pattern.checkType(Types.NAT, collected, constraints);
    } else {
      if (expected != Types.NAT) throw new UnexpectedPatternForTypeException(this, expected);
      pattern.checkType(Types.NAT, collected, constraints);
    }
  }
}
