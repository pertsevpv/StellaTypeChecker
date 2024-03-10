package stella.pattern;

import stella.exception.TypeCheckingException;
import stella.expr.Expr;
import stella.type.Type;
import stella.utils.Pair;

import java.util.List;

public abstract class Pattern {

  abstract boolean match(Expr expr);

  public abstract void checkType(Type expected, List<Pair<String, Type>> collected) throws TypeCheckingException;

}
