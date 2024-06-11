package stella.type;

import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedSubtypeException;

import java.util.Objects;


public class SumType extends Type {

  public Type left, right;

  public SumType(Type left, Type right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    SumType sumType = (SumType) o;
    return Objects.equals(left, sumType.left) && Objects.equals(right, sumType.right);
  }

  @Override
  public int hashCode() {
    return Objects.hash(left, right);
  }

  @Override
  public String toString() {
    return "%s + %s".formatted(left, right);
  }

  @Override
  protected void checkSubtypeOf(Type parent) throws TypeCheckingException {
    if (!(parent instanceof SumType parentSumType)) throw new UnexpectedSubtypeException(this, parent);
    left.isSubtypeOf(parentSumType.left);
    right.isSubtypeOf(parentSumType.right);
  }

  @Override
  public Type sub(VarType toSub, Type sub) {
    return new SumType(left.sub(toSub, sub), right.sub(toSub, sub));
  }
}
