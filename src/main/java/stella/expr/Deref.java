package stella.expr;

import stella.checker.Context;
import stella.exception.NotAReferenceException;
import stella.exception.TypeCheckingException;
import stella.pattern.Pattern;
import stella.type.RefType;
import stella.type.Type;

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
  public Expr withPattern(Pattern pattern, Expr to) {
    return null;
  }

  @Override
  public String toString() {
    return "*%s".formatted(deref);
  }
}
