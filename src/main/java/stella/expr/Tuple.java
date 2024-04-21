package stella.expr;

import stella.checker.Context;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedTupleException;
import stella.exception.UnexpectedTupleLengthException;
import stella.pattern.Pattern;
import stella.type.TupleType;
import stella.type.Type;
import stella.type.Types;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Tuple extends Expr {

  public List<Expr> tuple;

  public Tuple(List<Expr> tuple) {
    this.tuple = tuple;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    if (!(expected instanceof TupleType expectedTuple))
      if (context.structuralSubtyping && expected == Types.TOP) return;
      else throw new UnexpectedTupleException(expected, this);
    if (tuple.size() != expectedTuple.tuple.size())
      throw new UnexpectedTupleLengthException(this, expectedTuple.size(), tuple.size());

    for (int i = 0; i < expectedTuple.size(); i++) {
      var expectI = expectedTuple.get(i + 1);
      var gotExpr = tuple.get(i);
      gotExpr.checkTypes(context, expectI);
    }
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    List<Type> tupleTypes = new ArrayList<>();
    for (var t: tuple) tupleTypes.add(t.infer(context));
    return new TupleType(tupleTypes);
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return new Tuple(tuple.stream().map(t -> t.withPattern(pattern, to)).toList());
  }

  @Override
  public String toString() {
    return tuple.stream()
        .map(Expr::toString)
        .collect(Collectors.joining(", ", "{", "}"));
  }
}
