package stella.checker;

import stella.type.Type;

public class Context {

  public Gamma gamma;
  public Type exceptionType;
  public boolean structuralSubtyping;
  public boolean ambiguousTypeAsBottom;

  public Context() {
    this.gamma = new Gamma();
  }

  public void put(String key, Type type) {
    gamma.put(key, type);
  }

  public Type get(String key) {
    return gamma.get(key);
  }

  public void enterGamma() {
    gamma = new Gamma(gamma);
  }

  public void exitGamma() {
    gamma = gamma.parent;
  }
}
