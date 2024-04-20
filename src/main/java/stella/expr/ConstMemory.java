package stella.expr;

import stella.checker.Context;
import stella.exception.AmbiguousReferenceType;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedMemoryAddress;
import stella.pattern.Pattern;
import stella.type.RefType;
import stella.type.Type;

public class ConstMemory extends Expr {

  public String address;

  public ConstMemory(String address) {
    this.address = address;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    if (!(expected instanceof RefType refType))
      throw new UnexpectedMemoryAddress(this);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    throw new AmbiguousReferenceType(this);
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return null;
  }

  @Override
  public String toString() {
    return address;
  }
}
