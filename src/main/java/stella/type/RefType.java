package stella.type;

import java.util.Objects;

public class RefType extends Type {

  public Type refType;

  public RefType(Type refType) {
    this.refType = refType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    RefType refType1 = (RefType) o;
    return Objects.equals(refType, refType1.refType);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(refType);
  }

  @Override
  public String toString() {
    return "&%s".formatted(refType);
  }
}
