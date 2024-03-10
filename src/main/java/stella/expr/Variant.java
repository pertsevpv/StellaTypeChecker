package stella.expr;

import stella.checker.Gamma;
import stella.exception.AmbiguousVariantException;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedVariantException;
import stella.pattern.Pattern;
import stella.type.Type;
import stella.type.VariantType;

public class Variant extends Expr {

  public String label;
  public Expr expr;

  public Variant(String label, Expr expr) {
    this.label = label;
    this.expr = expr;
  }

  @Override
  public void checkTypes(Gamma gamma, Type expected) throws TypeCheckingException {
    if (!(expected instanceof VariantType variantType))
      throw new UnexpectedVariantException(expected, this);
    expr.checkTypes(gamma, variantType.get(label));
  }

  @Override
  public Type infer(Gamma gamma) throws TypeCheckingException {
    throw new AmbiguousVariantException(this);
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return new Variant(label, expr.withPattern(pattern, to));
  }

  @Override
  public String toString() {
    return "<%s = %s>".formatted(label, expr);
  }
}
