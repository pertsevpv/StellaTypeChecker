package stella.type;

public enum TypeEnum {

  NAT("Nat"), BOOL("Bool"), UNIT("Unit");

  public final String value;

  TypeEnum(String value) {
    this.value = value;
  }

}
