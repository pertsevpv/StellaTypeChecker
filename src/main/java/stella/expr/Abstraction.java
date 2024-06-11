package stella.expr;

import stella.checker.Context;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedLambdaException;
import stella.exception.UnexpectedNumberOfParametersInLambdaException;
import stella.exception.UnexpectedTypeForParameterException;
import stella.type.FuncType;
import stella.type.Type;
import stella.type.Types;
import stella.utils.Pair;

import java.util.List;
import java.util.stream.Collectors;


public class Abstraction extends Expr {

  public Expr retExpr;
  public List<Pair<String, Type>> params;

  public Abstraction(List<Pair<String, Type>> params, Expr retExpr) {
    this.retExpr = retExpr;
    this.params = params;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    if (!(expected instanceof FuncType expectedFunc))
      if (context.structuralSubtyping && expected == Types.TOP) return;
      else throw new UnexpectedLambdaException(expected, this);
    if (params.size() != expectedFunc.params.size())
      throw new UnexpectedNumberOfParametersInLambdaException(this, expectedFunc.params.size(), params.size());

    for (int i = 0; i < params.size(); i++) {
      var exp = expectedFunc.params.get(i);
      var got = params.get(i).second;
      if (context.structuralSubtyping) {
        exp.isSubtypeOf(got);
      } else {
        if (!got.equals(exp)) throw new UnexpectedTypeForParameterException(params.get(i).first, expected, got, this);
      }
    }
    context.enterGamma();
    params.forEach(p -> context.put(p.first, p.second));
    retExpr.checkTypes(context, expectedFunc.ret);
    context.exitGamma();
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    context.enterGamma();
    params.forEach(p -> context.put(p.first, p.second));
    var res = new FuncType(params.stream().map(Pair::second).toList(), retExpr.infer(context));
    context.exitGamma();
    return res;
  }


  @Override
  public String toString() {
    return "fn(%s){return %s}".formatted(
        params.stream()
            .map(p -> "%s : %s".formatted(p.first, p.second))
            .collect(Collectors.joining(", ")),
        retExpr
    );
  }
}
