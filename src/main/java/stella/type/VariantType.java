package stella.type;

import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedSubtypeException;
import stella.exception.UnexpectedVariantLabelException;
import stella.utils.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class VariantType extends Type {

  public List<Pair<String, Type>> variant;

  public VariantType(List<Pair<String, Type>> variant) {
    this.variant = new ArrayList<>(variant);
//    this.variant.sort(Comparator.comparing(a -> a.first));
  }

  public Pair<String, Type> getVariant(String label) {
    return variant.stream()
        .filter(pair -> pair.first.equals(label))
        .findFirst()
        .orElse(null);
  }

  public Type get(String label) throws UnexpectedVariantLabelException {
    var variant = getVariant(label);
    if (variant == null) throw new UnexpectedVariantLabelException(label, this);
    return variant.second;
  }

  public boolean containLabel(String label) {
    return variant.stream()
        .anyMatch(pair -> pair.first.equals(label));
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    VariantType that = (VariantType) o;
    return Objects.equals(variant, that.variant);
  }

  @Override
  public int hashCode() {
    return Objects.hash(variant);
  }

  @Override
  public String toString() {
    return variant.stream()
        .map(v -> "%s : %s".formatted(v.first, v.second))
        .collect(Collectors.joining(" | ", "<", ">"));
  }

  @Override
  protected void checkSubtypeOf(Type parent) throws TypeCheckingException {
    if (!(parent instanceof VariantType parentVariantType)) throw new UnexpectedSubtypeException(this, parent);;
    for (var subLabel: variant) {
      var parentVariant = parentVariantType.getVariant(subLabel.first());
      if (parentVariant == null) throw new UnexpectedVariantLabelException(subLabel.first, this);
      subLabel.second().isSubtypeOf(parentVariant.second);
    }
  }

  @Override
  public Type sub(VarType toSub, Type sub) {
    return new VariantType(
        variant.stream()
            .map(p -> new Pair<>(p.first, p.second.sub(toSub, sub)))
            .toList()
    );
  }

  @Override
  public Type sub(Map<UniVarType, Type> map) {
    return new VariantType(
        variant.stream()
            .map(p -> new Pair<>(p.first, p.second.sub(map)))
            .toList()
    );
  }
}
