package stella.type;

import stella.exception.UnexpectedVariantLabelException;
import stella.utils.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class VariantType extends Type {

  public List<Pair<String, Type>> variant;

  public VariantType(List<Pair<String, Type>> variant) {
    this.variant = new ArrayList<>(variant);
//    this.variant.sort(Comparator.comparing(a -> a.first));
  }

  public Type get(String label) throws UnexpectedVariantLabelException {
    return variant.stream()
        .filter(pair -> pair.first.equals(label))
        .findFirst()
        .orElseThrow(() -> new UnexpectedVariantLabelException(label, this))
        .second;
  }

  public boolean containLabel(String label){
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
}
