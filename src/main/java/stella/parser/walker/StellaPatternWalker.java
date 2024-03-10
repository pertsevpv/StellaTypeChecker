package stella.parser.walker;

import stella.pattern.*;
import stella.utils.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import static stella.parser.gen.StellaParser.*;

public class StellaPatternWalker {

  private static final Map<Class<? extends PatternContext>, Function<PatternContext, Pattern>> PATTERN_MAP = initMap();

  private static Map<Class<? extends PatternContext>, Function<PatternContext, Pattern>> initMap() {
    Map<Class<? extends PatternContext>, Function<PatternContext, Pattern>> map = new HashMap<>();
    map.put(PatternFalseContext.class, (ctx) -> new BoolPattern(false));
    map.put(PatternTrueContext.class, (ctx) -> new BoolPattern(true));
    map.put(PatternUnitContext.class, (ctx) -> new UnitPattern());
    map.put(PatternVarContext.class, (ctx) -> new VarPattern(((PatternVarContext) ctx).name.getText()));
    map.put(PatternIntContext.class, (ctx) -> new IntPattern(Integer.parseInt(((PatternIntContext) ctx).n.getText())));
    map.put(PatternSuccContext.class, (ctx) -> new SuccPattern(handlePattern(((PatternSuccContext) ctx).pattern())));
    map.put(ParenthesisedPatternContext.class, (ctx) -> handlePattern(((ParenthesisedPatternContext) ctx).pattern()));
    map.put(PatternInlContext.class, (ctx) -> new InlPattern(handlePattern(((PatternInlContext) ctx).pattern_)));
    map.put(PatternInrContext.class, (ctx) -> new InrPattern(handlePattern(((PatternInrContext) ctx).pattern_)));
    map.put(PatternListContext.class, StellaPatternWalker::handleList);
    map.put(PatternConsContext.class, StellaPatternWalker::handleCons);
    map.put(PatternTupleContext.class, StellaPatternWalker::handleTuple);
    map.put(PatternRecordContext.class, StellaPatternWalker::handleRecord);
    map.put(PatternVariantContext.class, StellaPatternWalker::handleVariant);
    return map;
  }

  public static Pattern handlePattern(PatternContext ctx) {
    if (!PATTERN_MAP.containsKey(ctx.getClass()))
      throw new IllegalArgumentException("Unknown pattern type: " + ctx.getClass());
    return PATTERN_MAP.get(ctx.getClass()).apply(ctx);
  }

  private static ListPattern handleList(PatternContext context) {
    var ctx = (PatternListContext) context;
    return new ListPattern(ctx.patterns.stream().map(StellaPatternWalker::handlePattern).toList());
  }

  private static ConsPattern handleCons(PatternContext context) {
    var ctx = (PatternConsContext) context;
    return new ConsPattern(handlePattern(ctx.head), handlePattern(ctx.tail));
  }

  private static TuplePattern handleTuple(PatternContext context) {
    var ctx = (PatternTupleContext) context;
    return new TuplePattern(ctx.patterns.stream().map(StellaPatternWalker::handlePattern).toList());
  }

  private static RecordPattern handleRecord(PatternContext context) {
    var ctx = (PatternRecordContext) context;
    return new RecordPattern(ctx.patterns.stream().map(
        p -> new Pair<>(p.label.getText(), handlePattern(p.pattern_))
    ).toList());
  }

  private static VariantPattern handleVariant(PatternContext context) {
    var ctx = (PatternVariantContext) context;
    return new VariantPattern(
        ctx.label.getText(),
        ctx.pattern_ != null ? handlePattern(ctx.pattern_) : new UnitPattern()
    );
  }

}
