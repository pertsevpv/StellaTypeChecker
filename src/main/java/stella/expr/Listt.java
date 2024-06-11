package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.AmbiguousListException;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedListException;
import stella.type.ListType;
import stella.type.Type;
import stella.type.Types;
import stella.type.VarType;

import java.util.List;
import java.util.stream.Collectors;

public class Listt extends Expr {

  public List<Expr> listt;

  public Listt(List<Expr> listt) {
    this.listt = listt;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    if (!(expected instanceof ListType listType))
      if (context.structuralSubtyping && expected == Types.TOP) return;
      else throw new UnexpectedListException(expected, this);
    for (var e: listt) e.checkTypes(context, listType.listType);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    if (listt.isEmpty()) {
      if (context.ambiguousTypeAsBottom) return Types.BOTTOM;
      throw new AmbiguousListException(this);
    }
    var expected = listt.get(0).infer(context);
    for (int i = 1; i < listt.size(); i++) listt.get(i).checkTypes(context, expected);
    return new ListType(expected);
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    if (listt.isEmpty()) {
      return new VarType();
    } else {
      var first = listt.get(0).collectConstraints(context, constraints);
      for (int i = 1; i < listt.size(); i++) {
        var t = listt.get(i).collectConstraints(context, constraints);
        constraints.add(new Constraint(first, t));
      }
      return new ListType(first);
    }
  }

  @Override
  public String toString() {
    return listt.stream().map(Expr::toString).collect(Collectors.joining(", ", "[", "]"));
  }

}
