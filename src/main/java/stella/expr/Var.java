package stella.expr;

import stella.checker.Gamma;
import stella.exception.TypeCheckingException;
import stella.exception.UndefinedVariableException;
import stella.pattern.Pattern;
import stella.pattern.VarPattern;
import stella.type.Type;
import stella.utils.Utils;

public class Var extends Expr {

  public String var;

  public Var(String var) {
    this.var = var;
  }

  @Override
  public void checkTypes(Gamma gamma, Type expected) throws TypeCheckingException {
    var type = gamma.get(var);
    if (type == null) throw new UndefinedVariableException(var);
    Utils.checkTypeInExpr(expected, type, this);
  }

  @Override
  public Type infer(Gamma gamma) throws TypeCheckingException {
    var type = gamma.get(var);
    if (type == null) throw new UndefinedVariableException(var);
    return type;
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    if (pattern instanceof VarPattern varPattern &&
        varPattern.var.equals(var)
    ) return to;
    return this;
  }

  @Override
  public String toString() {
    return var;
  }
}
