package stella.type;

import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedSubtypeException;

import java.util.Map;

class BaseType extends Type {

  public TypeEnum type;

  BaseType(TypeEnum type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return type.value;
  }

  @Override
  protected void checkSubtypeOf(Type parent) throws TypeCheckingException {
    if (this != parent) throw new UnexpectedSubtypeException(this, parent);
  }

  @Override
  public Type sub(VarType toSub, Type sub) {
    return this;
  }

  @Override
  public Type sub(Map<UniVarType, Type> map) {
    return this;
  }
}
