package stella.expr;

import stella.checker.Context;
import stella.exception.IncorrectNumberOfArgumentsException;
import stella.exception.NotAFunctionException;
import stella.exception.TypeCheckingException;
import stella.pattern.Pattern;
import stella.type.FuncType;
import stella.type.Type;
import stella.utils.Utils;

import java.util.List;
import java.util.stream.Collectors;

public class Application extends Expr {

  public Expr func;
  public List<Expr> args;

  public Application(Expr func, List<Expr> args) {
    this.func = func;
    this.args = args;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    var t1 = func.infer(context);
    if (!(t1 instanceof FuncType funcType))
      throw new NotAFunctionException(this, func, t1);
    if (args.size() != funcType.params.size())
      throw new IncorrectNumberOfArgumentsException(this, funcType.params.size(), args.size());
    Utils.checkTypeInExpr(expected, funcType.ret, this, context.structuralSubtyping);
    for (int i = 0; i < args.size(); i++) {
      var exp = funcType.params.get(i);
      args.get(i).checkTypes(context, exp);
    }
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    var t1 = func.infer(context);
    if (!(t1 instanceof FuncType funcType))
      throw new NotAFunctionException(this, func, t1);
    if (args.size() != funcType.params.size())
      throw new IncorrectNumberOfArgumentsException(this, funcType.params.size(), args.size());
    for (int i = 0; i < args.size(); i++) {
      var exp = funcType.params.get(i);
      args.get(i).checkTypes(context, exp);
    }
    return funcType.ret;
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return new Application(
        func.withPattern(pattern, to),
        args.stream().map(a -> a.withPattern(pattern, to)).toList()
    );
  }

  @Override
  public String toString() {
    return "%s(%s)".formatted(
        func,
        args.stream().map(Expr::toString).collect(Collectors.joining(", "))
        );
  }
}
