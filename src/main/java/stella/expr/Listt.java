package stella.expr;

import stella.checker.Gamma;
import stella.exception.AmbiguousListException;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedListException;
import stella.pattern.Pattern;
import stella.type.ListType;
import stella.type.Type;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Listt extends Expr {

  public List<Expr> listt;

  public Listt(List<Expr> listt) {
    this.listt = listt;
  }

  @Override
  public void checkTypes(Gamma gamma, Type expected) throws TypeCheckingException {
    if (!(expected instanceof ListType listType))
      throw new UnexpectedListException(expected, this);
    for (var e: listt) e.checkTypes(gamma, listType.listType);
  }

  @Override
  public Type infer(Gamma gamma) throws TypeCheckingException {
    if (listt.isEmpty()) throw new AmbiguousListException(this);
    var expected = listt.get(0).infer(gamma);
    for (int i = 1; i < listt.size(); i++) listt.get(i).checkTypes(gamma, expected);
    return new ListType(expected);
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return new Listt(listt.stream().map(e -> e.withPattern(pattern, to)).toList());
  }

  @Override
  public String toString() {
    return listt.stream().map(Expr::toString).collect(Collectors.joining(", ", "[", "]"));
  }

}
