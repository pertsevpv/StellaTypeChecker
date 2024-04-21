package stella.expr;

import stella.checker.Context;
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
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    var gotType = expr.infer(context);
    var expArgType = new FuncType(List.of(expected), expected);
    if (!(gotType instanceof FuncType gotFunc) || gotFunc.params.size() != 1)
      throw new NotAFunctionException(this, expr, expArgType);
    Utils.checkTypeInExpr(expArgType, gotFunc, this, context.structuralSubtyping);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    var exprType = expr.infer(context);
    if (!(exprType instanceof FuncType gotFunc) || gotFunc.params.size() != 1)
      throw new NotAFunctionException(this, expr, exprType);
    Utils.checkTypeInExpr(gotFunc.params.get(0), gotFunc.ret, this, context.structuralSubtyping);
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
