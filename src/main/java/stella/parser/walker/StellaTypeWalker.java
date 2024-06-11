package stella.parser.walker;

import stella.type.*;
import stella.utils.Pair;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static stella.parser.gen.StellaParser.*;
import static stella.type.Types.*;

public class StellaTypeWalker {

  public static final Map<Class<? extends StellatypeContext>, Function<StellatypeContext, Type>> TYPE_MAP = initMap();

  public static Map<Class<? extends StellatypeContext>, Function<StellatypeContext, Type>> initMap() {
    Map<Class<? extends StellatypeContext>, Function<StellatypeContext, Type>> map = new HashMap<>();
    map.put(TypeBoolContext.class, (ctx) -> BOOL);
    map.put(TypeNatContext.class, (ctx) -> NAT);
    map.put(TypeUnitContext.class, (ctx) -> UNIT);
    map.put(TypeFunContext.class, StellaTypeWalker::handleFunc);
    map.put(TypeParensContext.class, (ctx) -> handleType(((TypeParensContext) ctx).type_));
    map.put(TypeSumContext.class, StellaTypeWalker::handleTypeSum);
    map.put(TypeTupleContext.class, StellaTypeWalker::handleTypeTuple);
    map.put(TypeRecordContext.class, StellaTypeWalker::handleTypeRecord);
    map.put(TypeListContext.class, (ctx) -> new ListType(handleType(((TypeListContext) ctx).type_)));
    map.put(TypeVariantContext.class, StellaTypeWalker::handleTypeVariant);
    map.put(TypeRefContext.class, (ctx) -> new RefType(handleType(((TypeRefContext) ctx).type_)));
    map.put(TypeTopContext.class, (ctx) -> TOP);
    map.put(TypeBottomContext.class, (ctx) -> BOTTOM);
    map.put(TypeVarContext.class, StellaTypeWalker::handleVarType);
    return map;
  }

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

  public static VarType handleVarType(StellatypeContext context) {
    var ctx = (TypeVarContext) context;
    if (ctx.StellaIdent().getText().equals("auto")) return new VarType();
    else throw new RuntimeException();
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
