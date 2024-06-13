package stella.type;

import stella.exception.TypeCheckingException;

import java.util.Map;
import java.util.Objects;

public class UniVarType extends Type {

  public String name;

  public UniVarType(String name) {
    this.name = name;
  }

  @Override
  protected void checkSubtypeOf(Type parent) throws TypeCheckingException {

  }

  @Override
  public Type sub(VarType toSub, Type sub) {
    return this;
  }

  @Override
  public Type sub(Map<UniVarType, Type> map) {
    return map.getOrDefault(this, this);
  }

  @Override
  public String toString() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UniVarType that = (UniVarType) o;
    return Objects.equals(name, that.name);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(name);
  }
}
