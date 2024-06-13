package stella.type;

import stella.exception.TypeCheckingException;

import java.util.Map;

public abstract class Type {

  public void isSubtypeOf(Type parent) throws TypeCheckingException {
    if (parent == Types.TOP) return;
    if (this == Types.BOTTOM) return;
    checkSubtypeOf(parent);
  }

  protected abstract void checkSubtypeOf(Type parent) throws TypeCheckingException;

  public abstract Type sub(VarType toSub, Type sub);

  public abstract Type sub(Map<UniVarType, Type> map);

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
}

