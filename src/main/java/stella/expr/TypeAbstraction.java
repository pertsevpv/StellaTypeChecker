package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedTypeForExpressionException;
import stella.type.Type;
import stella.type.UniType;
import stella.type.UniVarType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class TypeAbstraction extends Expr {

  public Expr expr;
  public List<UniVarType> generics;

  public TypeAbstraction(Expr expr, List<UniVarType> generics) {
    this.expr = expr;
    this.generics = generics;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    if (!(expected instanceof UniType uniType))
      throw new UnexpectedTypeForExpressionException(expected, new UniType(generics, expr.infer(context)), this);

    Map<UniVarType, Type> subMap = new HashMap<>();
    for (int i = 0; i < generics.size(); i++)
      subMap.put(uniType.vars.get(i), generics.get(i));
    expr.checkTypes(context, uniType.type.sub(subMap));
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    return new UniType(generics, expr.infer(context));
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    return null;
  }

  @Override
  public String toString() {
    return "generic [%s] fn (%s)".formatted(
        generics.stream().map(Type::toString).collect(Collectors.joining(", ")),
        expr
    );
  }
}
