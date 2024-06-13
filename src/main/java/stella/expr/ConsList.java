package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedListException;
import stella.type.ListType;
import stella.type.Type;
import stella.type.Types;
import stella.type.UniVarType;

import java.util.List;

public class ConsList extends Expr {

  public Expr head, tail;

  public ConsList(Expr head, Expr tail) {
    this.head = head;
    this.tail = tail;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    if (!(expected instanceof ListType listType)) {
      if (expected instanceof UniVarType) return;
      if (context.structuralSubtyping && expected == Types.TOP) return;
      else throw new UnexpectedListException(expected, this);
    }
    head.checkTypes(context, listType.listType);
    tail.checkTypes(context, expected);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    var headType = head.infer(context);
    Type tailType;
    if (headType == null) {
      tailType = tail.infer(context);
      if (!(tailType instanceof ListType))
        throw new UnexpectedListException(tailType, this);
    } else {
      tailType = new ListType(headType);
      tail.checkTypes(context, tailType);
    }
    return tailType;
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    var th = head.collectConstraints(context, constraints);
    var tt = tail.collectConstraints(context, constraints);
    constraints.add(new Constraint(tt, new ListType(th)));
    return tt;
  }


  @Override
  public String toString() {
    return "cons(%s, %s)".formatted(head, tail);
  }
}
