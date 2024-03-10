package stella.checker;

import stella.type.Type;

import java.util.HashMap;

public class Gamma extends HashMap<String, Type> {

  public Gamma parent;

  @Override
  public boolean containsKey(Object key) {
    return super.containsKey(key) || parent != null && parent.containsKey(key);
  }

  @Override
  public Type get(Object key) {
    Type result = super.get(key);
    return result != null
        ? result
        : parent != null
        ? parent.get(key)
        : null;
  }
}
