package stella.expr;

import stella.checker.Context;
import stella.exception.NotAListException;
import stella.exception.TypeCheckingException;
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
  public String toString() {
    return "List::isempty(%s)".formatted(list);
  }
}
