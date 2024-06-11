package stella.expr;

import stella.checker.Context;
import stella.checker.ExhChecker;
import stella.constraint.Constraint;
import stella.exception.ExceptionTypeNotDeclaredException;
import stella.exception.TypeCheckingException;
import stella.pattern.Pattern;
import stella.type.Type;
import stella.utils.Pair;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TryCatch extends Expr {

  public Expr tryExpr;
  public Pattern pattern;
  public Expr expr;

  public TryCatch(Expr tryExpr, Pattern pattern, Expr expr) {
    this.tryExpr = tryExpr;
    this.pattern = pattern;
    this.expr = expr;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    if (context.exceptionType == null)
      throw new ExceptionTypeNotDeclaredException();
    tryExpr.checkTypes(context, expected);
    ExhChecker.check(expected, Collections.singletonList(pattern));

    List<Pair<String, Type>> collected = new ArrayList<>();
    pattern.checkType(context.exceptionType, collected);
    context.enterGamma();
    expr.checkTypes(context, expected);
    context.exitGamma();
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    if (context.exceptionType == null)
      throw new ExceptionTypeNotDeclaredException();
    var type = tryExpr.infer(context);

    ExhChecker.check(type, Collections.singletonList(pattern));

    List<Pair<String, Type>> collected = new ArrayList<>();
    pattern.checkType(context.exceptionType, collected);
    context.enterGamma();
    expr.checkTypes(context, type);
    context.exitGamma();
    return type;
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    if (context.exceptionType == null)
      throw new ExceptionTypeNotDeclaredException();
    var type = tryExpr.collectConstraints(context, constraints);

    ExhChecker.check(type, Collections.singletonList(pattern));

    List<Pair<String, Type>> collected = new ArrayList<>();
    pattern.checkType(context.exceptionType, collected);
    context.enterGamma();
    constraints.add(new Constraint(expr.collectConstraints(context, constraints), type));
    context.exitGamma();
    return type;
  }

  @Override
  public String toString() {
    return "try {%s} catch {%s => %s}".formatted(tryExpr, pattern, expr);
  }
}
