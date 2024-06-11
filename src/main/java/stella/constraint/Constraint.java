package stella.constraint;

import stella.type.Type;

public class Constraint {

  public Type left, right;

  public Constraint(Type left, Type right) {
    this.left = left;
    this.right = right;
  }

  @Override
  public String toString() {
    return "%s = %s".formatted(left, right);
  }
}
