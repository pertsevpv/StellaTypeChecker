package stella.type;

import stella.exception.UnexpectedFieldAccessException;
import stella.utils.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class RecordType extends Type {

  public List<Pair<String, Type>> record;

  public RecordType(List<Pair<String, Type>> record) {
    this.record = new ArrayList<>(record);
//    this.record.sort(Comparator.comparing(a -> a.first));
  }

  public RecordType(List<String> labels, List<Type> types) {
    if (labels.isEmpty() || types.isEmpty() || labels.size() != types.size()) throw new IllegalArgumentException();
    this.record = new ArrayList<>();
    for (int i = 0; i < labels.size(); i++) {
      record.add(new Pair<>(labels.get(i), types.get(i)));
    }
  }

  public boolean containLabel(String label) {
    return !record.stream()
        .filter(f -> f.first.equals(label))
        .toList().isEmpty();
  }

  public Type get(String label) throws UnexpectedFieldAccessException {
    return record.stream()
        .filter(pair -> pair.first.equals(label))
        .findFirst()
        .orElseThrow(() -> new UnexpectedFieldAccessException(label, this))
        .second;
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
}
