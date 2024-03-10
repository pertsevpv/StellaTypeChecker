package stella.pattern;

import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedPatternForTypeException;
import stella.expr.Expr;
import stella.expr.Listt;
import stella.type.ListType;
import stella.type.Type;
import stella.utils.Pair;

import java.util.List;
import java.util.stream.Collectors;

public class ListPattern extends Pattern {

  public List<Pattern> patterns;

  public ListPattern(List<Pattern> patterns) {
    this.patterns = patterns;
  }

  @Override
  boolean match(Expr expr) {
    if (!(expr instanceof Listt list) || list.listt.size() != patterns.size()) return false;
    for (int i = 0; i < patterns.size(); i++) if (!patterns.get(0).match(list.listt.get(0))) return false;
    return true;
  }

  @Override
  public void checkType(Type expected, List<Pair<String, Type>> collected) throws TypeCheckingException {
    if (!(expected instanceof ListType listType)) throw new UnexpectedPatternForTypeException(this, expected);
    for (var p: patterns) p.checkType(listType.listType, collected);
  }

  @Override
  public String toString() {
    return patterns.stream().map(Pattern::toString).collect(Collectors.joining(", ", "[", "]"));
  }
}
