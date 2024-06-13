package stella.type;

import stella.exception.MissingRecordFieldsException;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedFieldAccessException;
import stella.exception.UnexpectedSubtypeException;
import stella.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RecordType extends Type {

  public List<Pair<String, Type>> record;

  public RecordType(List<Pair<String, Type>> record) {
    this.record = new ArrayList<>(record);
//    this.record.sort(Comparator.comparing(a -> a.first));
  }

  public boolean containLabel(String label) {
    return !record.stream()
        .filter(f -> f.first.equals(label))
        .toList().isEmpty();
  }

  public Type get(String label) throws UnexpectedFieldAccessException {
    var field = getField(label);
    if (field == null) throw new UnexpectedFieldAccessException(label, this);
    return field.second();
  }

  public Pair<String, Type> getField(String label) {
    return record.stream()
        .filter(pair -> pair.first.equals(label))
        .findFirst()
        .orElse(null);
  }

  public int size() {
    return record.size();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RecordType that = (RecordType) o;
    return Objects.equals(record, that.record);
  }

  @Override
  public int hashCode() {
    return Objects.hash(record);
  }

  @Override
  public String toString() {
    return record.stream()
        .map(f -> "%s : %s".formatted(f.first, f.second))
        .collect(Collectors.joining(", ", "{", "}"));
  }

  @Override
  protected void checkSubtypeOf(Type parent) throws TypeCheckingException {
    if (!(parent instanceof RecordType parentRecordType)) throw new UnexpectedSubtypeException(this, parent);
    for (var parentField: parentRecordType.record) {
      var subField = getField(parentField.first);
      if (subField == null) throw new MissingRecordFieldsException(parent, this, parentField.first);
      subField.second().isSubtypeOf(parentField.second);
    }
  }

  @Override
  public Type sub(VarType toSub, Type sub) {
    return new RecordType(
        record.stream()
            .map(p -> new Pair<>(p.first, p.second.sub(toSub, sub)))
            .toList()
    );
  }

  @Override
  public Type sub(Map<UniVarType, Type> map) {
    return new RecordType(
        record.stream()
            .map(p -> new Pair<>(p.first, p.second.sub(map)))
            .toList()
    );
  }
}
