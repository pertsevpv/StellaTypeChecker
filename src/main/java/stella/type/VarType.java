package stella.type;

import stella.exception.TypeCheckingException;

import java.util.Map;
import java.util.Objects;

public class VarType extends Type {

  public int number;
  private static int varTypeCnt = 0;

  public VarType() {
    this(varTypeCnt++);
  }

  public VarType(int number) {
    this.number = number;
  }

  @Override
  protected void checkSubtypeOf(Type parent) throws TypeCheckingException {

  }

  @Override
  public Type sub(VarType toSub, Type sub) {
    return equals(toSub) ? sub : this;
  }

  @Override
  public Type sub(Map<UniVarType, Type> map) {
    return this;
  }

  @Override
  public String toString() {
    return "?T" + number;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    VarType varType = (VarType) o;
    return number == varType.number;
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(number);
  }
}
