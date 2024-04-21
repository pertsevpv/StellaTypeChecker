package stella.type;

public interface Types {

  BaseType NAT = new BaseType(TypeEnum.NAT);
  BaseType BOOL = new BaseType(TypeEnum.BOOL);
  BaseType UNIT = new BaseType(TypeEnum.UNIT);
  BaseType TOP = new BaseType(TypeEnum.TOP);
  BaseType BOTTOM = new BaseType(TypeEnum.BOTTOM);

}
