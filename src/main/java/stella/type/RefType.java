package stella.type;

import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedSubtypeException;

import java.util.Map;
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

  @Override
  protected void checkSubtypeOf(Type parent) throws TypeCheckingException {
    if (!(parent instanceof RefType parentRefType)) throw new UnexpectedSubtypeException(this, parent);
    refType.isSubtypeOf(parentRefType.refType);
    parentRefType.refType.isSubtypeOf(this.refType);
  }

  @Override
  public Type sub(VarType toSub, Type sub) {
    return new RefType(refType.sub(toSub, sub));
  }

  @Override
  public Type sub(Map<UniVarType, Type> map) {
    return new RefType(refType.sub(map));
  }
}
