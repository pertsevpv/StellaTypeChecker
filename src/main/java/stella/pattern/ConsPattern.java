package stella.pattern;

import stella.constraint.Constraint;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedPatternForTypeException;
import stella.expr.ConsList;
import stella.expr.Expr;
import stella.type.ListType;
import stella.type.Type;
import stella.type.VarType;
import stella.utils.Pair;

import java.util.List;

public class ConsPattern extends Pattern {

  public Pattern head, tail;

  public ConsPattern(Pattern head, Pattern tail) {
    this.head = head;
    this.tail = tail;
  }

  @Override
  boolean match(Expr expr) {
    if (!(expr instanceof ConsList list)) return false;
    return head.match(list.head) && tail.match(list.tail);
  }

  @Override
  public void checkType(Type expected, List<Pair<String, Type>> collected) throws TypeCheckingException {
    if (!(expected instanceof ListType listType)) throw new UnexpectedPatternForTypeException(this, expected);
    head.checkType(listType.listType, collected);
    tail.checkType(listType, collected);
  }

  @Override
  public void checkType(Type expected, List<Pair<String, Type>> collected, List<Constraint> constraints) throws TypeCheckingException {
    if (expected instanceof VarType) {
      var th = new VarType();
      var tt = new VarType();
      constraints.add(new Constraint(expected, tt));
      constraints.add(new Constraint(expected, new ListType(th)));
      head.checkType(th, collected);
      tail.checkType(tt, collected, constraints);
    } else {
      if (!(expected instanceof ListType listType)) throw new UnexpectedPatternForTypeException(this, expected);
      head.checkType(listType.listType, collected);
      tail.checkType(listType, collected, constraints);
    }
  }

  @Override
  public String toString() {
    return "cons(%s, %s)".formatted(head, tail);
  }
}
