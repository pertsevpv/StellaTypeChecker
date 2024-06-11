package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.AmbiguousReferenceType;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedMemoryAddress;
import stella.type.RefType;
import stella.type.Type;
import stella.type.Types;

import java.util.List;

public class ConstMemory extends Expr {

  public String address;

  public ConstMemory(String address) {
    this.address = address;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    if (!(expected instanceof RefType))
      if (context.structuralSubtyping && expected == Types.TOP) return;
      else throw new UnexpectedMemoryAddress(this);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    if (context.ambiguousTypeAsBottom) return Types.BOTTOM;
    throw new AmbiguousReferenceType(this);
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    if (context.ambiguousTypeAsBottom) return Types.BOTTOM;
    throw new AmbiguousReferenceType(this);
  }

  @Override
  public String toString() {
    return address;
  }
}
