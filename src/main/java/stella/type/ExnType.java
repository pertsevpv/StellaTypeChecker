package stella.type;

public class ExnType extends Type {

  public Type exn;

  public ExnType(Type exn) {
    this.exn = exn;
  }

  @Override
  public String toString() {
    return "%s_exn".formatted(exn);
  }
}
