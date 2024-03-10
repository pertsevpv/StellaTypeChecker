package stella.pattern;

import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedPatternForTypeException;
import stella.expr.Expr;
import stella.expr.Tuple;
import stella.type.TupleType;
import stella.type.Type;
import stella.utils.Pair;

import java.util.List;
import java.util.stream.Collectors;

public class TuplePattern extends Pattern {

  public List<Pattern> patterns;

  public TuplePattern(List<Pattern> patterns) {
    this.patterns = patterns;
  }

  @Override
  boolean match(Expr expr) {
    if (!(expr instanceof Tuple tuple)) return false;
    for (int i = 0; i < patterns.size(); i++) if (!patterns.get(i).match(tuple.tuple.get(i))) return false;
    return false;
  }

  @Override
  public void checkType(Type expected, List<Pair<String, Type>> collected) throws TypeCheckingException {
    if (!(expected instanceof TupleType tupleType) ||
        tupleType.size() != patterns.size()
    ) throw new UnexpectedPatternForTypeException(this, expected);
    for (int i = 0; i < patterns.size(); i++) patterns.get(i).checkType(tupleType.tuple.get(i), collected);
  }

  @Override
  public String toString() {
    return patterns.stream()
        .map(Pattern::toString)
        .collect(Collectors.joining(", ", "{", "}"));
  }
}
