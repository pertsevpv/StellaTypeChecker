package stella.checker;

import stella.type.Type;

public class Context {

  public Gamma gamma;
  public Exceptions exceptions;

  public Context() {
    this.gamma = new Gamma();
    this.exceptions = new Exceptions();
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

  public void enterExc() {
    exceptions = new Exceptions(exceptions);
  }

  public void exitExc() {
    exceptions = exceptions.parent;
  }
}
