package stella.type;

import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedSubtypeException;

public abstract class Type {

  public void isSubtypeOf(Type parent) throws TypeCheckingException {
    if (parent == Types.TOP) return;
    if (parent == Types.BOTTOM) {
      if (this != Types.BOTTOM) throw new UnexpectedSubtypeException(this, parent);
      return;
    }
    checkSubtypeOf(parent);
  }

  protected abstract void checkSubtypeOf(Type parent) throws TypeCheckingException;

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
}

