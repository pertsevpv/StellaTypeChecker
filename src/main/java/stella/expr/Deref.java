package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.NotAReferenceException;
import stella.exception.TypeCheckingException;
import stella.type.RefType;
import stella.type.Type;

import java.util.List;

public class Deref extends Expr {

  public Expr deref;

  public Deref(Expr deref) {
    this.deref = deref;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    deref.checkTypes(context, new RefType(expected));
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    var derefType = deref.infer(context);
    if (!(derefType instanceof RefType refType))
      throw new NotAReferenceException(deref);
    return refType.refType;
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    throw new UnsupportedOperationException();
  }


  @Override
  public String toString() {
    return "*%s".formatted(deref);
  }
}
