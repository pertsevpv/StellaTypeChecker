package stella.parser.walker;

import stella.type.*;
import stella.utils.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static stella.parser.gen.StellaParser.*;
import static stella.type.Types.*;

public class StellaTypeWalker {

  public static final Map<Class<? extends StellatypeContext>, Function<StellatypeContext, Type>> TYPE_MAP = Map.of(
      TypeBoolContext.class, (ctx) -> BOOL,
      TypeNatContext.class, (ctx) -> NAT,
      TypeUnitContext.class, (ctx) -> UNIT,
      TypeFunContext.class, StellaTypeWalker::handleFunc,
      TypeParensContext.class, (ctx) -> handleType(((TypeParensContext) ctx).type_),
      TypeSumContext.class, StellaTypeWalker::handleTypeSum,
      TypeTupleContext.class, StellaTypeWalker::handleTypeTuple,
      TypeRecordContext.class, StellaTypeWalker::handleTypeRecord,
      TypeListContext.class, (ctx) -> new ListType(handleType(((TypeListContext) ctx).type_)),
      TypeVariantContext.class, StellaTypeWalker::handleTypeVariant
  );

  public static Type handleType(StellatypeContext ctx) {
    if (!TYPE_MAP.containsKey(ctx.getClass()))
      throw new IllegalArgumentException("Unknown expr type: " + ctx.getClass());
    return TYPE_MAP.get(ctx.getClass()).apply(ctx);
  }

  public static FuncType handleFunc(StellatypeContext context) {
    var ctx = (TypeFunContext) context;
    LinkedList<Type> params = handleParams(ctx.paramTypes);
    var returnType = handleType(ctx.returnType);
    return new FuncType(params, returnType);
  }

  public static SumType handleTypeSum(StellatypeContext context) {
    var ctx = (TypeSumContext) context;
    return new SumType(handleType(ctx.left), handleType(ctx.right));
  }

  public static TupleType handleTypeTuple(StellatypeContext context) {
    var ctx = (TypeTupleContext) context;
    var types = ctx.types.stream()
        .map(StellaTypeWalker::handleType)
        .toList();
    return new TupleType(types);
  }

  public static RecordType handleTypeRecord(StellatypeContext context) {
    var ctx = (TypeRecordContext) context;
    var record = ctx.fieldTypes.stream()
        .map(field -> new Pair<>(field.label.getText(), handleType(field.type_)))
        .toList();
    return new RecordType(record);
  }

  public static VariantType handleTypeVariant(StellatypeContext context) {
    var ctx = (TypeVariantContext) context;
    return new VariantType(ctx.fieldTypes.stream().map(
        f -> new Pair<>(f.label.getText(), f.type_ != null ? handleType(f.type_) : UNIT)
    ).toList());
  }

  public static LinkedList<Type> handleParams(List<StellatypeContext> paramTypes) {
    if (paramTypes == null) return null;
    return new LinkedList<>(paramTypes
        .stream()
        .map(StellaTypeWalker::handleType)
        .toList()
    );
  }

}
