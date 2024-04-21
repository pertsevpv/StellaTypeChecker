package stella.type;

import stella.exception.UnexpectedFieldAccessException;
import stella.utils.Pair;

import java.util.ArrayList;
import java.util.List;
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
  protected boolean checkSubtypeOf(Type parent) {
    if (!(parent instanceof RecordType parentRecordType)) return false;
    for (var parentField: parentRecordType.record) {
      var subField = getField(parentField.first);
      if (subField == null) return false;
      if (!subField.second().isSubtypeOf(parentField.second)) return false;
    }
    return true;
  }
}
