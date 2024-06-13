package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.NotAListException;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedListException;
import stella.type.ListType;
import stella.type.Type;
import stella.type.Types;
import stella.type.VarType;

import java.util.List;

public class Tail extends Expr {

  public Expr list;

  public Tail(Expr list) {
    this.list = list;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    if (!(expected instanceof ListType listType))
      if (context.structuralSubtyping && expected == Types.TOP) return;
      else throw new UnexpectedListException(expected, this);
    list.checkTypes(context, listType);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    var type = list.infer(context);
    if (!(type instanceof ListType))
      throw new NotAListException(list, type);
    return type;
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    var t = list.collectConstraints(context, constraints);
    if (!(t instanceof ListType listType)) {
      if (t instanceof VarType tx) {
        var x = new VarType();
        constraints.add(new Constraint(tx, new ListType(x)));
        return x;
      } else throw new NotAListException(list, t);
    } else return listType;
  }


  @Override
  public String toString() {
    return "tail(%s)".formatted(list);
  }
}
