package stella.expr;

import stella.checker.ExhChecker;
import stella.checker.Gamma;
import stella.exception.IllegalEmptyMatchingException;
import stella.exception.NonExhaustiveMatchPatterns;
import stella.exception.TypeCheckingException;
import stella.pattern.InlPattern;
import stella.pattern.InrPattern;
import stella.pattern.Pattern;
import stella.pattern.VarPattern;
import stella.type.SumType;
import stella.type.Type;
import stella.utils.Pair;

import java.util.ArrayList;
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
  public void checkTypes(Gamma gamma, Type expected) throws TypeCheckingException {
    if (cases.isEmpty()) throw new IllegalEmptyMatchingException(this);
    var exprType = expr.infer(gamma);
    for (var mCase: cases) {
      List<Pair<String, Type>> list = new ArrayList<>();
      mCase.first.checkType(exprType, list);
      var newG = new Gamma();
      newG.parent = gamma;
      for (var p: list) newG.put(p.first, p.second);
      mCase.second.checkTypes(newG, expected);
    }
    if (!ExhChecker.check(exprType, cases.stream().map(Pair::first).toList()))
      throw new NonExhaustiveMatchPatterns(exprType);
  }

  @Override
  public Type infer(Gamma gamma) throws TypeCheckingException {
    if (cases.isEmpty()) throw new IllegalEmptyMatchingException(this);
    var exprType = expr.infer(gamma);
    Type expected = null;
    for (var mCase: cases) {
      List<Pair<String, Type>> list = new ArrayList<>();
      mCase.first.checkType(exprType, list);
      var newG = new Gamma();
      newG.parent = gamma;
      for (var p: list) newG.put(p.first, p.second);
      if (expected == null) expected = mCase.second.infer(newG);
      else mCase.second.checkTypes(newG, expected);
    }
    if (!ExhChecker.check(exprType, cases.stream().map(Pair::first).toList()))
      throw new NonExhaustiveMatchPatterns(exprType);
    return expected;
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return new Match(
        expr.withPattern(pattern, to),
        cases.stream().map(p -> new Pair<>(p.first, p.second.withPattern(pattern, to))).toList()
    );
  }

  @Override
  public String toString() {
    return "match %s {%s}".formatted(expr, cases.stream().map(p -> "%s => %s".formatted(p.first, p.second)).collect(Collectors.joining(" | ")));
  }
}
