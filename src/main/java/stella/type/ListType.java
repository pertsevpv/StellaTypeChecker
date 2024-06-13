package stella.type;

import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedSubtypeException;

import java.util.Map;
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

  @Override
  protected void checkSubtypeOf(Type parent) throws TypeCheckingException {
    if (!(parent instanceof ListType parentListType)) throw new UnexpectedSubtypeException(this, parent);;
    listType.isSubtypeOf(parentListType.listType);
  }

  @Override
  public Type sub(VarType toSub, Type sub) {
    return new ListType(listType.sub(toSub, sub));
  }

  @Override
  public Type sub(Map<UniVarType, Type> map) {
    return new ListType(listType.sub(map));
  }
}
