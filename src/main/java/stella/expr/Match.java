package stella.expr;

import stella.checker.Context;
import stella.checker.ExhChecker;
import stella.constraint.Constraint;
import stella.constraint.ConstraintSolver;
import stella.exception.IllegalEmptyMatchingException;
import stella.exception.NonExhaustiveMatchPatterns;
import stella.exception.TypeCheckingException;
import stella.pattern.Pattern;
import stella.type.Type;
import stella.type.VarType;
import stella.utils.Pair;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class Match extends Expr {

  public Expr expr;
  public List<Pair<Pattern, Expr>> cases;

  public Match(Expr expr, List<Pair<Pattern, Expr>> cases) {
    this.expr = expr;
    this.cases = cases;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    if (cases.isEmpty()) throw new IllegalEmptyMatchingException(this);
    var exprType = expr.infer(context);
    for (var mCase: cases) {
      List<Pair<String, Type>> list = new ArrayList<>();
      mCase.first.checkType(exprType, list);
      context.enterGamma();
      for (var p: list) context.put(p.first, p.second);
      mCase.second.checkTypes(context, expected);
      context.exitGamma();
    }
    if (!ExhChecker.check(exprType, cases.stream().map(Pair::first).toList()))
      throw new NonExhaustiveMatchPatterns(exprType);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    if (cases.isEmpty()) throw new IllegalEmptyMatchingException(this);
    var exprType = expr.infer(context);
    Type expected = null;
    for (var mCase: cases) {
      List<Pair<String, Type>> list = new ArrayList<>();
      mCase.first.checkType(exprType, list);
      context.enterGamma();
      for (var p: list) context.put(p.first, p.second);
      if (expected == null) expected = mCase.second.infer(context);
      else mCase.second.checkTypes(context, expected);
      mCase.second.checkTypes(context, expected);
      context.exitGamma();
    }
    if (!ExhChecker.check(exprType, cases.stream().map(Pair::first).toList()))
      throw new NonExhaustiveMatchPatterns(exprType);
    return expected;
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    if (cases.isEmpty()) throw new IllegalEmptyMatchingException(this);
    var t = expr.collectConstraints(context, constraints);

    Type ct = null;
    for (var mCase: cases) {
      List<Pair<String, Type>> list = new ArrayList<>();
      mCase.first.checkType(t, list, constraints);
      context.enterGamma();
      for (var p: list) context.put(p.first, p.second);
      if (ct == null) ct = mCase.second.collectConstraints(context, constraints);
      else constraints.add(new Constraint(ct, mCase.second.collectConstraints(context, constraints)));
      context.exitGamma();
    }

    if (t instanceof VarType varType) t = check(varType, constraints);
    else if (!ExhChecker.check(t, cases.stream().map(Pair::first).toList(), (LinkedList) constraints))
      throw new NonExhaustiveMatchPatterns(t);

    return ct;
  }

  private Type check(
      VarType type,
      List<Constraint> constraints
  ) throws TypeCheckingException {
    if (cases == null || cases.isEmpty()) throw new NonExhaustiveMatchPatterns(type);
    var patterns = cases.stream().map(Pair::first).toList();
    var solved = new ConstraintSolver().solve((LinkedList<Constraint>) constraints);
    var solvedType = solved.stream()
        .filter(it -> it.first.equals(type))
        .findAny();
    if (solvedType.filter(pair -> ExhChecker.check(pair.second, patterns)).isPresent())
      return solvedType.get().second();
    throw new NonExhaustiveMatchPatterns(type);
  }

  @Override
  public String toString() {
    return "match %s {%s}".formatted(expr, cases.stream().map(p -> "%s => %s".formatted(p.first, p.second)).collect(Collectors.joining(" | ")));
  }
}
