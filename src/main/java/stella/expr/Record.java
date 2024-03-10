package stella.expr;

import stella.checker.Gamma;
import stella.exception.*;
import stella.pattern.Pattern;
import stella.type.RecordType;
import stella.type.Type;
import stella.utils.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Record extends Expr {

  public List<Pair<String, Expr>> record;

  public Record(List<Pair<String, Expr>> record) {
    this.record = new ArrayList<>(record);
//    this.record.sort(Comparator.comparing(a -> a.first));
  }

  public boolean containLabel(String label) {
    return !record.stream()
        .filter(f -> f.first.equals(label))
        .toList().isEmpty();
  }

  public Expr get(String label) {
    return record.stream()
        .filter(pair -> pair.first.equals(label))
        .findFirst()
        .get()
        .second;
  }

  @Override
  public void checkTypes(Gamma gamma, Type expected) throws TypeCheckingException {
    if (!(expected instanceof RecordType expectedRecord))
      throw new UnexpectedRecordException(expected, this);
    for (var f: record) {
      if (!expectedRecord.containLabel(f.first)) {
        throw new UnexpectedRecordFieldException(expectedRecord, this, f.first);
      }
    }
    for (var f: expectedRecord.record) {
      if (!containLabel(f.first)) {
        throw new MissingRecordFieldsException(expectedRecord, this, f.first);
      }
    }
    for (var r: record) {
      var expectL = expectedRecord.get(r.first);
      var gotExpr = r.second;
      gotExpr.checkTypes(gamma, expectL);
    }
  }

  @Override
  public Type infer(Gamma gamma) throws TypeCheckingException {
    List<Pair<String, Type>> recordTypes = new ArrayList<>();
    for (var t: record) {
      var field = new Pair<>(t.first, t.second.infer(gamma));
      recordTypes.add(field);
    }
    return new RecordType(recordTypes);
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return new Record(record.stream().map(r -> new Pair<>(r.first, r.second.withPattern(pattern, to))).toList());
  }

  @Override
  public String toString() {
    return record.stream()
        .map(f -> "%s : %s".formatted(f.first, f.second))
        .collect(Collectors.joining(", ", "{", "}"));
  }
}
