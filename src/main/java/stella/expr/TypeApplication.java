package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.IncorrectNumberOfTypeArgumentsException;
import stella.exception.NotAGenericFunctionException;
import stella.exception.TypeCheckingException;
import stella.type.Type;
import stella.type.UniType;
import stella.type.UniVarType;
import stella.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TypeApplication extends Expr {

  public Expr func;
  public List<Type> generics;

  public TypeApplication(Expr func, List<Type> generics) {
    this.func = func;
    this.generics = generics;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    var t1 = func.infer(context);

    if (!(t1 instanceof UniType uniType))
      throw new NotAGenericFunctionException(this, func, t1);
    if (uniType.vars.size() != generics.size())
      throw new IncorrectNumberOfTypeArgumentsException(this, uniType.vars.size(), generics.size());

    Map<UniVarType, Type> map = new HashMap<>();
    for (int i = 0; i < uniType.vars.size(); i++) {
      map.put(uniType.vars.get(i), generics.get(i));
    }
    var got = expected.sub(map);
    Utils.checkTypeInExpr(expected, got, this, context.structuralSubtyping);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    var t1 = func.infer(context);

    if (!(t1 instanceof UniType uniType))
      throw new NotAGenericFunctionException(this, func, t1);
    if (uniType.vars.size() != generics.size())
      throw new IncorrectNumberOfTypeArgumentsException(this, uniType.vars.size(), generics.size());

    Map<UniVarType, Type> map = new HashMap<>();
    for (int i = 0; i < uniType.vars.size(); i++) {
      map.put(uniType.vars.get(i), generics.get(i));
    }
    return uniType.sub(map);
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    return null;
  }

  @Override
  public String toString() {
    return "%s [%s]".formatted(
        func,
        generics.stream().map(Type::toString).collect(Collectors.joining(", "))
    );
  }
}
