package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.NotAListException;
import stella.exception.TypeCheckingException;
import stella.type.ListType;
import stella.type.Type;
import stella.type.VarType;

import java.util.List;

public class Head extends Expr {

  public Expr list;

  public Head(Expr list) {
    this.list = list;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    list.checkTypes(context, new ListType(expected));
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    var type = list.infer(context);
    if (!(type instanceof ListType listType))
      throw new NotAListException(list, type);
    return listType.listType;
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
    } else return listType.listType;
  }


  @Override
  public String toString() {
    return "head(%s)".formatted(list);
  }
}
