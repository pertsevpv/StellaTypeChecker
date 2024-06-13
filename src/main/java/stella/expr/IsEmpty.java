package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.NotAListException;
import stella.exception.TypeCheckingException;
import stella.type.ListType;
import stella.type.Type;
import stella.type.Types;
import stella.type.VarType;
import stella.utils.Utils;

import java.util.List;

public class IsEmpty extends Expr {

  public Expr list;

  public IsEmpty(Expr list) {
    this.list = list;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    Utils.checkTypeInExpr(Types.BOOL, expected, this, context.structuralSubtyping);
    var type = list.infer(context);
    if (!(type instanceof ListType))
      throw new NotAListException(list, type);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    var type = list.infer(context);
    if (!(type instanceof ListType))
      throw new NotAListException(list, type);
    return Types.BOOL;
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    var t = list.collectConstraints(context, constraints);
    var x = new VarType();
    constraints.add(new Constraint(t, new ListType(x)));
    return Types.BOOL;
  }

  @Override
  public String toString() {
    return "List::isempty(%s)".formatted(list);
  }
}
