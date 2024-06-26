package stella.pattern;

import stella.constraint.Constraint;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedPatternForTypeException;
import stella.expr.Expr;
import stella.expr.Inr;
import stella.type.SumType;
import stella.type.Type;
import stella.type.VarType;
import stella.utils.Pair;

import java.util.List;

public class InrPattern extends Pattern{

  public Pattern pattern;

  public InrPattern(Pattern pattern) {
    this.pattern = pattern;
  }

  @Override
  boolean match(Expr expr) {
    return expr instanceof Inr inr && pattern.match(inr.expr);
  }

  @Override
  public void checkType(Type expected, List<Pair<String, Type>> collected) throws TypeCheckingException {
    if (!(expected instanceof SumType sumType)) throw new UnexpectedPatternForTypeException(this, expected);
    pattern.checkType(sumType.right, collected);
  }

  @Override
  public void checkType(Type expected, List<Pair<String, Type>> collected, List<Constraint> constraints) throws TypeCheckingException {
    if (expected instanceof VarType) {
      var tl = new VarType();
      var tr = new VarType();
      constraints.add(new Constraint(expected, new SumType(tl, tr)));
      pattern.checkType(tr, collected, constraints);
    } else {
      if (!(expected instanceof SumType sumType)) throw new UnexpectedPatternForTypeException(this, expected);
      pattern.checkType(sumType.right, collected);
    }
  }

  @Override
  public String toString() {
    return "inr(%s)".formatted(pattern);
  }
}
