package stella.expr;

import stella.checker.Gamma;
import stella.exception.NotAListException;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedListException;
import stella.pattern.Pattern;
import stella.type.ListType;
import stella.type.Type;
import stella.type.Types;
import stella.utils.Utils;

public class IsEmpty extends Expr {

  public Expr list;

  public IsEmpty(Expr list) {
    this.list = list;
  }

  @Override
  public void checkTypes(Gamma gamma, Type expected) throws TypeCheckingException {
    Utils.checkTypeInExpr(Types.BOOL, expected, this);
    var type = list.infer(gamma);
    if (!(type instanceof ListType))
      throw new NotAListException(list, type);
  }

  @Override
  public Type infer(Gamma gamma) throws TypeCheckingException {
    var type = list.infer(gamma);
    if (!(type instanceof ListType))
      throw new NotAListException(list, type);
    return Types.BOOL;
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return new IsEmpty(list.withPattern(pattern, to));
  }

  @Override
  public String toString() {
    return "List::isempty(%s)".formatted(list);
  }
}
