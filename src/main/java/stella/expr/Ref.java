package stella.expr;

import stella.checker.Context;
import stella.exception.NotAReferenceException;
import stella.exception.TypeCheckingException;
import stella.pattern.Pattern;
import stella.type.RefType;
import stella.type.Type;
import stella.type.Types;

public class Ref extends Expr {

  public Expr ref;

  public Ref(Expr ref) {
    this.ref = ref;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    if (!(expected instanceof RefType expectedRefType))
      if (context.structuralSubtyping && expected == Types.TOP) return;
      else throw new NotAReferenceException(this, expected);
    ref.checkTypes(context, expectedRefType.refType);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    return new RefType(ref.infer(context));
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return null;
  }

  @Override
  public String toString() {
    return "new(%s)".formatted(ref);
  }
}
