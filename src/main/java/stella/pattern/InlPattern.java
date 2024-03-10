package stella.pattern;

import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedPatternForTypeException;
import stella.expr.Expr;
import stella.expr.Inl;
import stella.type.SumType;
import stella.type.Type;
import stella.utils.Pair;

import java.util.List;

public class InlPattern extends Pattern{

  public Pattern pattern;

  public InlPattern(Pattern pattern) {
    this.pattern = pattern;
  }

  @Override
  boolean match(Expr expr) {
    return expr instanceof Inl inl && pattern.match(inl.expr);
  }

  @Override
  public void checkType(Type expected, List<Pair<String, Type>> collected) throws TypeCheckingException {
    if (!(expected instanceof SumType sumType)) throw new UnexpectedPatternForTypeException(this, expected);
    pattern.checkType(sumType.left, collected);
  }

  @Override
  public String toString() {
    return "inl(%s)".formatted(pattern);
  }
}
