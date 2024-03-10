package stella.expr;

import stella.checker.Gamma;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedLambdaException;
import stella.exception.UnexpectedNumberOfParametersInLambda;
import stella.exception.UnexpectedTypeForParameterException;
import stella.pattern.Pattern;
import stella.type.FuncType;
import stella.type.Type;
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
  public void checkTypes(Gamma gamma, Type expected) throws TypeCheckingException {
    if (!(expected instanceof FuncType expectedFunc))
      throw new UnexpectedLambdaException(expected, this);
    if (params.size() != expectedFunc.params.size())
      throw new UnexpectedNumberOfParametersInLambda(this, expectedFunc.params.size(), params.size());

    for (int i = 0; i < params.size(); i++) {
      var exp = expectedFunc.params.get(i);
      var got = params.get(i).second;
      if (!exp.equals(got))
        throw new UnexpectedTypeForParameterException(params.get(i).first, expected, got, this);
    }
    var newG = new Gamma();
    newG.parent = gamma;
    params.forEach(p -> newG.put(p.first, p.second));
    retExpr.checkTypes(newG, expectedFunc.ret);
  }

  @Override
  public Type infer(Gamma gamma) throws TypeCheckingException {
    var newG = new Gamma();
    newG.parent = gamma;
    params.forEach(p -> newG.put(p.first, p.second));
    return new FuncType(params.stream().map(Pair::second).toList(), retExpr.infer(newG));
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return retExpr.withPattern(pattern, to);
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
