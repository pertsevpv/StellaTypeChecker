package stella.type;

class BaseType extends Type {

  public TypeEnum type;

  BaseType(TypeEnum type) {
    this.type = type;
  }

  @Override
  public String toString() {
    return type.value;
  }
}
