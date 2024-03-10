package stella.parser.walker;

import stella.expr.Record;
import stella.expr.*;
import stella.utils.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static stella.parser.gen.StellaParser.*;
import static stella.parser.walker.StellaPatternWalker.handlePattern;
import static stella.parser.walker.StellaTypeWalker.handleType;

public class StellaExprWalker {

  private static final Map<Class<? extends ExprContext>, Function<ExprContext, Expr>> EXPR_MAP = initMap();

  private static Map<Class<? extends ExprContext>, Function<ExprContext, Expr>> initMap() {
    Map<Class<? extends ExprContext>, Function<ExprContext, Expr>> map = new HashMap<>();
    map.put(DotRecordContext.class, StellaExprWalker::handleDotRecord);
    map.put(DotTupleContext.class, StellaExprWalker::handleDotTuple);
    map.put(ConstFalseContext.class, (ctx) -> new ConstBool(false));
    map.put(ConstTrueContext.class, (ctx) -> new ConstBool(true));
    map.put(ConstUnitContext.class, (ctx) -> new Unit());
    map.put(ConstIntContext.class, StellaExprWalker::handleNat);
    map.put(VarContext.class, StellaExprWalker::handleVar);
    map.put(IfContext.class, StellaExprWalker::handleIf);
    map.put(ApplicationContext.class, StellaExprWalker::handleApplication);
    map.put(AbstractionContext.class, StellaExprWalker::handleAbstraction);
    map.put(SuccContext.class, StellaExprWalker::handleSucc);
    map.put(PredContext.class, StellaExprWalker::handlePred);
    map.put(IsZeroContext.class, StellaExprWalker::handleIsZero);
    map.put(NatRecContext.class, StellaExprWalker::handleNatRec);
    map.put(LogicNotContext.class, StellaExprWalker::handleNot);
    map.put(LetContext.class, StellaExprWalker::handleLet);
    map.put(ParenthesisedExprContext.class, (ctx) -> handleExpr(((ParenthesisedExprContext) ctx).expr_));
    map.put(TerminatingSemicolonContext.class, (ctx) -> handleExpr(((TerminatingSemicolonContext) ctx).expr_));
    map.put(SequenceContext.class, StellaExprWalker::handleSequence);
    map.put(TupleContext.class, StellaExprWalker::handleTuple);
    map.put(RecordContext.class, StellaExprWalker::handleRecord);
    map.put(TypeAscContext.class, StellaExprWalker::handleAsc);
    map.put(InlContext.class, StellaExprWalker::handleInl);
    map.put(InrContext.class, StellaExprWalker::handleInr);
    map.put(MatchContext.class, StellaExprWalker::handleMatch);
    map.put(FixContext.class, (ctx) -> new Fix(handleExpr(((FixContext) ctx).expr_)));
    map.put(ListContext.class, StellaExprWalker::handleList);
    map.put(ConsListContext.class, StellaExprWalker::handleConsList);
    map.put(HeadContext.class, (ctx) -> new Head(handleExpr(((HeadContext) ctx).list)));
    map.put(TailContext.class, (ctx) -> new Tail(handleExpr(((TailContext) ctx).list)));
    map.put(IsEmptyContext.class, (ctx) -> new IsEmpty(handleExpr(((IsEmptyContext) ctx).list)));
    map.put(VariantContext.class, StellaExprWalker::handleVariant);
    return map;
  }

  public static Expr handleExpr(ExprContext ctx) {
    if (!EXPR_MAP.containsKey(ctx.getClass()))
      throw new IllegalArgumentException("Unknown expr type: " + ctx.getClass());
    return EXPR_MAP.get(ctx.getClass()).apply(ctx);
  }

  private static DotRecord handleDotRecord(ExprContext context) {
    var ctx = (DotRecordContext) context;
    return new DotRecord(handleExpr(ctx.expr_), ctx.label.getText());
  }

  private static DotTuple handleDotTuple(ExprContext context) {
    var ctx = (DotTupleContext) context;
    return new DotTuple(handleExpr(ctx.expr_), Integer.parseInt(ctx.index.getText()));
  }

  private static ConstNat handleNat(ExprContext context) {
    var ctx = (ConstIntContext) context;
    return new ConstNat(Integer.parseInt(ctx.n.getText()));
  }

  private static Var handleVar(ExprContext context) {
    var ctx = (VarContext) context;
    return new Var(ctx.name.getText());
  }

  private static If handleIf(ExprContext context) {
    var ctx = (IfContext) context;
    var cond = handleExpr(ctx.condition);
    var thenE = handleExpr(ctx.thenExpr);
    var elseE = handleExpr(ctx.elseExpr);
    return new If(cond, thenE, elseE);
  }

  private static Application handleApplication(ExprContext context) {
    var ctx = (ApplicationContext) context;
    var func = handleExpr(ctx.fun);
    LinkedList<Expr> args = ctx.args.stream()
        .map(StellaExprWalker::handleExpr)
        .collect(Collectors.toCollection(LinkedList::new));
    return new Application(func, args);
  }

  private static Abstraction handleAbstraction(ExprContext context) {
    var ctx = (AbstractionContext) context;
    var params = ctx.paramDecls.stream()
        .map(param -> new Pair<>(
            param.name.getText(),
            StellaTypeWalker.handleType(param.paramType))
        ).collect(Collectors.toCollection(LinkedList::new));
    var retExpr = handleExpr(ctx.returnExpr);
    return new Abstraction(params, retExpr);
  }

  private static Succ handleSucc(ExprContext context) {
    var ctx = (SuccContext) context;
    return new Succ(handleExpr(ctx.n));
  }

  private static Pred handlePred(ExprContext context) {
    var ctx = (PredContext) context;
    return new Pred(handleExpr(ctx.n));
  }

  private static NatIsZero handleIsZero(ExprContext context) {
    var ctx = (IsZeroContext) context;
    return new NatIsZero(handleExpr(ctx.n));
  }

  private static NatRec handleNatRec(ExprContext context) {
    var ctx = (NatRecContext) context;
    return new NatRec(handleExpr(ctx.n), handleExpr(ctx.initial), handleExpr(ctx.step));
  }

  private static Not handleNot(ExprContext context) {
    var ctx = (LogicNotContext) context;
    return new Not(handleExpr(ctx.expr_));
  }

  private static Let handleLet(ExprContext context) {
    var ctx = (LetContext) context;
    var patterns = ctx.patternBindings.stream()
        .map(pattern ->
            new Pair<>(handlePattern(pattern.pat), handleExpr(pattern.rhs))
        ).collect(Collectors.toCollection(LinkedList::new));
    var expr = handleExpr(ctx.expr());
    var pattern = patterns.removeLast();
    var result = new Let(pattern.first, pattern.second, expr);
    while (!patterns.isEmpty()) {
      pattern = patterns.removeLast();
      result = new Let(pattern.first, pattern.second, result);
    }
    return result;
  }

  private static Tuple handleTuple(ExprContext context) {
    var ctx = (TupleContext) context;
    return new Tuple(ctx.exprs.stream().map(StellaExprWalker::handleExpr).toList());
  }

  private static Record handleRecord(ExprContext context) {
    var ctx = (RecordContext) context;
    return new Record(
        ctx.bindings.stream().map(
            b -> new Pair<>(b.name.getText(), handleExpr(b.rhs))
        ).toList()
    );
  }

  private static Asc handleAsc(ExprContext context) {
    var ctx = (TypeAscContext) context;
    return new Asc(handleExpr(ctx.expr_), handleType(ctx.type_));
  }

  private static Inl handleInl(ExprContext context) {
    var ctx = (InlContext) context;
    return new Inl(handleExpr(ctx.expr_));
  }

  private static Inr handleInr(ExprContext context) {
    var ctx = (InrContext) context;
    return new Inr(handleExpr(ctx.expr_));
  }

  private static Match handleMatch(ExprContext context) {
    var ctx = (MatchContext) context;
    var expr = handleExpr(ctx.expr());
    var cases = ctx.cases.stream()
        .map(c -> new Pair<>(handlePattern(c.pattern_), handleExpr(c.expr_)))
        .toList();
    return new Match(expr, cases);
  }

  private static Listt handleList(ExprContext context) {
    var ctx = (ListContext) context;
    var exprs = ctx.exprs.stream()
        .map(StellaExprWalker::handleExpr)
        .toList();
    return new Listt(exprs);
  }

  private static ConsList handleConsList(ExprContext context) {
    var ctx = (ConsListContext) context;
    return new ConsList(handleExpr(ctx.head), handleExpr(ctx.tail));
  }

  private static Sequence handleSequence(ExprContext context) {
    var ctx = (SequenceContext) context;
    return new Sequence(
        handleExpr(ctx.expr1),
        ctx.expr2 != null ? handleExpr(ctx.expr2) : null
    );
  }

  private static Variant handleVariant(ExprContext context) {
    var ctx = (VariantContext) context;
    return new Variant(ctx.label.getText(), ctx.rhs != null ? handleExpr(ctx.rhs) : new Unit());
  }

}
