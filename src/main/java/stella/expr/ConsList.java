package stella.expr;

import stella.checker.Gamma;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedListException;
import stella.pattern.Pattern;
import stella.type.ListType;
import stella.type.Type;
import stella.utils.Utils;

public class ConsList extends Expr {

  public Expr head, tail;

  public ConsList(Expr head, Expr tail) {
    this.head = head;
    this.tail = tail;
  }

  @Override
  public void checkTypes(Gamma gamma, Type expected) throws TypeCheckingException {
    if (!(expected instanceof ListType listType))
      throw new UnexpectedListException(expected, this);
    head.checkTypes(gamma, listType.listType);
    tail.checkTypes(gamma, expected);
  }

  @Override
  public Type infer(Gamma gamma) throws TypeCheckingException {
    var headType = head.infer(gamma);
    Type tailType;
    if (headType == null) {
      tailType = tail.infer(gamma);
      if (!(tailType instanceof ListType))
        throw new UnexpectedListException(tailType, this);
    } else {
      tailType = new ListType(headType);
      tail.checkTypes(gamma, tailType);
    }
    return tailType;
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return new ConsList(
        head.withPattern(pattern, to),
        tail.withPattern(pattern, to)
    );
  }

  @Override
  public String toString() {
    return "cons(%s, %s)".formatted(head, tail);
  }
}
