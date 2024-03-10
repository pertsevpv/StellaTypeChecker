package stella.pattern;

import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedPatternForTypeException;
import stella.expr.Expr;
import stella.expr.Variant;
import stella.type.Type;
import stella.type.VariantType;
import stella.utils.Pair;

import java.util.List;

public class VariantPattern extends Pattern {

  public String label;
  public Pattern pattern;

  public VariantPattern(String label, Pattern pattern) {
    this.label = label;
    this.pattern = pattern;
  }

  @Override
  boolean match(Expr expr) {
    return expr instanceof Variant variant && label.equals(variant.label);
  }

  @Override
  public void checkType(Type expected, List<Pair<String, Type>> collected) throws TypeCheckingException {
    if (!(expected instanceof VariantType variantType) || !variantType.containLabel(label))
      throw new UnexpectedPatternForTypeException(this, expected);
    pattern.checkType(variantType.get(label), collected);
  }

  @Override
  public String toString() {
    return "<| %s = %s |>".formatted(label, pattern);
  }
}
