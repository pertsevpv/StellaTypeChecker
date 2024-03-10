package stella.checker;

import stella.pattern.*;
import stella.type.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExhChecker {

  public static boolean check(Type type, List<Pattern> patterns) {
    if (patterns == null || patterns.isEmpty()) return false;
    if (type == Types.NAT) return checkNat(patterns);
    if (type == Types.BOOL) return checkBool(patterns);
    if (type == Types.UNIT) return checkUnit(patterns);
    if (type instanceof SumType sumType) return checkSumType(sumType, patterns);
    if (type instanceof VariantType variant) return checkVariant(variant, patterns);
    if (type instanceof TupleType tupleType) return checkTuple(tupleType, patterns);
    if (type instanceof RecordType recordType) return checkRecord(recordType, patterns);
    if (type instanceof ListType listType) return checkList(listType, patterns);
    return patterns.stream().anyMatch(p -> p instanceof VarPattern);
  }

  public static boolean checkNat(List<Pattern> patterns) {
    boolean zero = false, succ = false;
    for (var p: patterns) {
      if (p instanceof VarPattern) return true;
      if (p instanceof IntPattern intPattern && intPattern.value == 0) zero = true;
      if (p instanceof SuccPattern succPattern && succPattern.pattern instanceof VarPattern) succ = true;
    }
    return zero && succ;
  }

  public static boolean checkBool(List<Pattern> patterns) {
    boolean ttrue = false, ffalse = false;
    for (var p: patterns) {
      if (p instanceof VarPattern) return true;
      if (p instanceof BoolPattern boolPattern) {
        if (boolPattern.value) ttrue = true;
        else ffalse = true;
      }
    }
    return ttrue && ffalse;
  }

  public static boolean checkUnit(List<Pattern> patterns) {
    for (var p: patterns) {
      if (p instanceof VarPattern) return true;
      if (p instanceof UnitPattern) return true;
    }
    return false;
  }

  public static boolean checkTuple(TupleType tupleType, List<Pattern> patterns) {
    List<List<Pattern>> typePatterns = new ArrayList<>();
    for (int i = 0; i < tupleType.size(); i++) typePatterns.add(new ArrayList<>());
    for (var p: patterns) {
      if (p instanceof VarPattern) return true;
      if (p instanceof TuplePattern tuplePattern &&
          tupleType.tuple.size() == tuplePattern.patterns.size()) {
        for (int i = 0; i < tuplePattern.patterns.size(); i++)
          typePatterns.get(i).add(tuplePattern.patterns.get(i));
      }
    }
    for (int i = 0; i < tupleType.size(); i++) if (!check(tupleType.tuple.get(i), typePatterns.get(i))) return false;
    return true;
  }

  public static boolean checkRecord(RecordType record, List<Pattern> patterns) {
    Map<String, List<Pattern>> typePatterns = new HashMap<>();
    for (var p: patterns) {
      if (p instanceof VarPattern) return true;
      if (p instanceof RecordPattern recordPattern &&
          record.size() == recordPattern.patterns.size()) {
        for (var f: record.record) {
          typePatterns.putIfAbsent(f.first, new ArrayList<>());
          typePatterns.get(f.first).add(recordPattern.get(f.first));
        }
      }
    }
    for (var f: record.record) if (!typePatterns.containsKey(f.first)) return false;
    for (var f: record.record) if (!check(f.second, typePatterns.get(f.first))) return false;
    return true;
  }

  public static boolean checkSumType(SumType sumType, List<Pattern> patterns) {
    List<Pattern> inl = new ArrayList<>();
    List<Pattern> inr = new ArrayList<>();
    for (var p: patterns) {
      if (p instanceof VarPattern) return true;
      if (p instanceof InlPattern inlPattern) inl.add(inlPattern.pattern);
      if (p instanceof InrPattern inrPattern) inr.add(inrPattern.pattern);
    }
    if (inl.isEmpty() || inr.isEmpty()) return false;
    else return check(sumType.left, inl) && check(sumType.right, inr);
  }

  public static boolean checkVariant(VariantType variant, List<Pattern> patterns) {
    Map<String, List<Pattern>> variantMap = new HashMap<>();
    for (var p: patterns) {
      if (p instanceof VarPattern) return true;
      if (p instanceof VariantPattern variantPattern) {
        variantMap.putIfAbsent(variantPattern.label, new ArrayList<>());
        variantMap.get(variantPattern.label).add(variantPattern.pattern);
      }
    }
    for (var v: variant.variant) if (!variantMap.containsKey(v.first)) return false;
    for (var v: variant.variant) if (!check(v.second, variantMap.get(v.first))) return false;
    return true;
  }

  public static boolean checkList(ListType list, List<Pattern> patterns) {
    boolean empty = false;
    List<Pattern> head = new ArrayList<>();
    List<Pattern> tail = new ArrayList<>();
    for (var p: patterns) {
      if (p instanceof VarPattern) return true;
      if (p instanceof ListPattern listPattern) {
        if (listPattern.patterns.isEmpty()) empty = true;
        else {
          head.add(listPattern.patterns.get(0));
          tail.add(new ListPattern(listPattern.patterns.subList(1, listPattern.patterns.size())));
        }
      }
      if (p instanceof ConsPattern consPattern) {
        head.add(consPattern.head);
        tail.add(consPattern.tail);
      }
    }
    return empty && check(list.listType, head) && check(list, tail);
  }

}
