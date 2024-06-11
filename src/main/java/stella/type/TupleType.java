package stella.type;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedSubtypeException;
import stella.exception.UnexpectedTupleLengthException;


public class TupleType extends Type {

  public List<Type> tuple;

  public TupleType(List<Type> tuple) {
    if (tuple.isEmpty()) throw new IllegalArgumentException("Tuple must not be empty");
    this.tuple = tuple;
  }

  public Type get(int i) {
    return tuple.get(i - 1);
  }

  public int size() {
    return tuple.size();
  }

  @Override
  public String toString() {
    return tuple.stream()
        .map(Type::toString)
        .collect(Collectors.joining(", ", "{", "}"));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    TupleType tupleType = (TupleType) o;
    return Objects.equals(tuple, tupleType.tuple);
  }

  @Override
  public int hashCode() {
    return Objects.hash(tuple);
  }

  @Override
  protected void checkSubtypeOf(Type parent) throws TypeCheckingException {
    if (!(parent instanceof TupleType parentTupleType)) throw new UnexpectedSubtypeException(this, parent);
    if (size() != parentTupleType.size()) throw new UnexpectedTupleLengthException(this, parentTupleType.size(), size());
    for (int i = 0; i < size(); i++) {
      tuple.get(i).isSubtypeOf(parentTupleType.tuple.get(i));
    }
  }

  @Override
  public Type sub(VarType toSub, Type sub) {
    return new TupleType(tuple.stream().map(type -> type.sub(toSub, sub)).toList());
  }
}
