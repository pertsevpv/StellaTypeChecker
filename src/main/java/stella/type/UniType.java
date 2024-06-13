package stella.type;

import stella.exception.TypeCheckingException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class UniType extends Type {

  public List<UniVarType> vars;
  public Type type;

  public UniType(List<UniVarType> vars, Type type) {
    this.vars = vars;
    this.type = type;
  }

  @Override
  protected void checkSubtypeOf(Type parent) throws TypeCheckingException {

  }

  @Override
  public Type sub(VarType toSub, Type sub) {
    return this;
  }

  @Override
  @SuppressWarnings("all")
  public Type sub(Map<UniVarType, Type> map) {
    var sub = type.sub(map);
    if (!this.vars.containsAll(map.keySet())) {
      return new UniType(vars, sub);
    } else return sub;
  }

  @Override
  public String toString() {
    return "forall %s.%s".formatted(
        vars.stream().map(UniVarType::toString).collect(Collectors.joining(", ")),
        type
    );
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UniType uniType = (UniType) o;
    return Objects.equals(vars, uniType.vars) && Objects.equals(type, uniType.type);
  }

  @Override
  public int hashCode() {
    return Objects.hash(vars, type);
  }

}
