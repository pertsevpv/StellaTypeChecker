package stella.type;

import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedSubtypeException;

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
}
