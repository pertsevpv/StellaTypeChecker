package stella.type;

import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedSubtypeException;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class FuncType extends Type {

  public List<Type> params;
  public Type ret;

  public FuncType(List<Type> params, Type ret) {
    this.params = params;
    this.ret = ret;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    FuncType func = (FuncType) o;
    return Objects.equals(params, func.params) && Objects.equals(ret, func.ret);
  }

  @Override
  public int hashCode() {
    return Objects.hash(params, ret);
  }

  @Override
  public String toString() {
    return "fn(%s)->%s".formatted(
        params.stream().map(Type::toString).collect(Collectors.joining(", ")),
        ret
    );
  }

  @Override
  protected void checkSubtypeOf(Type parent) throws TypeCheckingException {
    if (!(parent instanceof FuncType funcType)) throw new UnexpectedSubtypeException(this, parent);
    if (params.size() != funcType.params.size()) throw new UnexpectedSubtypeException(this, parent);
    for (int i = 0; i < params.size(); i++)
      params.get(i).isSubtypeOf(funcType.params.get(i));
    ret.isSubtypeOf(funcType.ret);
  }

  @Override
  public Type sub(VarType toSub, Type sub) {
    return new FuncType(
        params.stream().map(type -> type.sub(toSub, sub)).toList(),
        ret.sub(toSub, sub)
    );
  }
}
