package stella.type;

import java.util.Objects;

public class ListType extends Type {

  public Type listType;

  public ListType(Type listType) {
    this.listType = listType;
  }

  @Override
  public String toString() {
    return "[%s]".formatted(listType);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ListType listType1 = (ListType) o;
    return Objects.equals(listType, listType1.listType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(listType);
  }
}
