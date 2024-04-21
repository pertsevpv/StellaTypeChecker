package stella.expr;

import stella.checker.Context;
import stella.exception.MissingRecordFieldsException;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedRecordException;
import stella.exception.UnexpectedRecordFieldException;
import stella.pattern.Pattern;
import stella.type.RecordType;
import stella.type.Type;
import stella.type.Types;
import stella.utils.Pair;

import java.util.ArrayList;
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
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    if (!(expected instanceof RecordType expectedRecord))
      if (context.structuralSubtyping && expected == Types.TOP) return;
      else throw new UnexpectedRecordException(expected, this);
    for (var f: record) {
      if (!context.structuralSubtyping && !expectedRecord.containLabel(f.first)) {
        throw new UnexpectedRecordFieldException(expectedRecord, this, f.first);
      }
    }
    for (var f: expectedRecord.record) {
      if (!containLabel(f.first)) {
        throw new MissingRecordFieldsException(expectedRecord, this, f.first);
      }
    }
    for (var r: expectedRecord.record) {
      var gotExpr = get(r.first);
      gotExpr.checkTypes(context, r.second);
    }
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    List<Pair<String, Type>> recordTypes = new ArrayList<>();
    for (var t: record) {
      var field = new Pair<>(t.first, t.second.infer(context));
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
