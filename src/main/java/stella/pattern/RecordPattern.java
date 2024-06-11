package stella.pattern;

import stella.constraint.Constraint;
import stella.exception.*;
import stella.expr.Expr;
import stella.expr.Record;
import stella.type.RecordType;
import stella.type.TupleType;
import stella.type.Type;
import stella.utils.Pair;

import java.util.List;
import java.util.stream.Collectors;

public class RecordPattern extends Pattern {

  public List<Pair<String, Pattern>> patterns;

  public RecordPattern(List<Pair<String, Pattern>> patterns) {
    this.patterns = patterns;
  }

  @Override
  boolean match(Expr expr) {
    if (!(expr instanceof Record record)) return false;
    for (var f: patterns) {
      if (!record.containLabel(f.first)) return false;
    }
    for (var f: record.record) {
      if (!containLabel(f.first)) return false;
    }
    for (var p: patterns) {
      var expectL = record.get(p.first);
      if (!p.second.match(expectL)) return false;
    }
    return true;
  }

  @Override
  public void checkType(Type expected, List<Pair<String, Type>> collected) throws TypeCheckingException {
    if (!(expected instanceof RecordType recordType)) throw new UnexpectedPatternForTypeException(this, expected);
    for (var f: patterns) {
      if (!recordType.containLabel(f.first)) throw new UnexpectedPatternForTypeException(this, expected);
    }
    for (var f: recordType.record) {
      if (!containLabel(f.first)) throw new UnexpectedPatternForTypeException(this, expected);
    }
    for (var p: patterns) {
      var expectL = recordType.get(p.first);
      p.second.checkType(expectL, collected);
    }
  }

  @Override
  public void checkType(Type expected, List<Pair<String, Type>> collected, List<Constraint> constraints) throws TypeCheckingException {
    throw new UnsupportedOperationException();
  }

  public Pattern get(String label) {
    return patterns.stream()
        .filter(pair -> pair.first.equals(label))
        .findFirst()
        .get()
        .second;
  }

  public boolean containLabel(String label) {
    return !patterns.stream()
        .filter(f -> f.first.equals(label))
        .toList().isEmpty();
  }

  @Override
  public String toString() {
    return patterns.stream()
        .map(f -> "%s = %s".formatted(f.first, f.second))
        .collect(Collectors.joining(", ", "{", "}"));
  }
}
