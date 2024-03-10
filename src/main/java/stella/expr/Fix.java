package stella.expr;

import stella.checker.Gamma;
import stella.exception.NotAFunctionException;
import stella.exception.TypeCheckingException;
import stella.pattern.Pattern;
import stella.type.FuncType;
import stella.type.Type;
import stella.utils.Utils;

import java.util.List;

public class Fix extends Expr {

  Expr expr;

  public Fix(Expr expr) {
    this.expr = expr;
  }

  @Override
  public void checkTypes(Gamma gamma, Type expected) throws TypeCheckingException {
    var gotType = expr.infer(gamma);
    var expArgType = new FuncType(List.of(expected), expected);
    if (!(gotType instanceof FuncType gotFunc) || gotFunc.params.size() != 1)
      throw new NotAFunctionException(this, expr, expArgType);
    Utils.checkTypeInExpr(expArgType, gotFunc, this);
  }

  @Override
  public Type infer(Gamma gamma) throws TypeCheckingException {
    var exprType = expr.infer(gamma);
    if (!(exprType instanceof FuncType gotFunc) || gotFunc.params.size() != 1)
      throw new NotAFunctionException(this, expr, exprType);
    Utils.checkTypeInExpr(gotFunc.params.get(0), gotFunc.ret, this);
    return gotFunc.ret;
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return null;
  }

  @Override
  public String toString() {
    return "fix(%s)".formatted(expr);
  }
}
