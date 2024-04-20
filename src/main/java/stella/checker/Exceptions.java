package stella.checker;

import stella.type.Type;

import java.util.HashSet;

public class Exceptions extends HashSet<Type> {

  public Exceptions parent;

  public Exceptions() {
  }

  public Exceptions(Exceptions parent) {
    this.parent = parent;
  }

  @Override
  public boolean contains(Object key) {
    return super.contains(key) || parent != null && parent.contains(key);
  }
}
