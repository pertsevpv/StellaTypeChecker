package stella.type;

public abstract class Type {

  public boolean isSubtypeOf(Type parent) {
    if (parent == Types.TOP) return true;
    if (parent == Types.BOTTOM) return this == Types.BOTTOM;
    return checkSubtypeOf(parent);
  }

  protected abstract boolean checkSubtypeOf(Type parent);

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }
}

