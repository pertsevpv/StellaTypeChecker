package stella.expr;

import stella.checker.Gamma;
import stella.exception.NotAListException;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedListException;
import stella.pattern.Pattern;
import stella.type.ListType;
import stella.type.Type;

public class Head extends Expr {

  public Expr list;

  public Head(Expr list) {
    this.list = list;
  }

  @Override
  public void checkTypes(Gamma gamma, Type expected) throws TypeCheckingException {
    list.checkTypes(gamma, new ListType(expected));
  }

  @Override
  public Type infer(Gamma gamma) throws TypeCheckingException {
    var type = list.infer(gamma);
    if (!(type instanceof ListType listType))
      throw new NotAListException(list, type);
    return listType.listType;
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return new Head(list.withPattern(pattern, to));
  }

  @Override
  public String toString() {
    return "head(%s)".formatted(list);
  }
}
