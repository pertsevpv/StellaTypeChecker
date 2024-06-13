package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.NotAFunctionException;
import stella.exception.TypeCheckingException;
import stella.type.FuncType;
import stella.type.Type;
import stella.type.VarType;
import stella.utils.Utils;

import java.util.List;

public class Fix extends Expr {

  Expr expr;

  public Fix(Expr expr) {
    this.expr = expr;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    var gotType = expr.infer(context);
    var expArgType = new FuncType(List.of(expected), expected);
    if (!(gotType instanceof FuncType gotFunc) || gotFunc.params.size() != 1)
      throw new NotAFunctionException(this, expr, expArgType);
    Utils.checkTypeInExpr(expArgType, gotFunc, this, context.structuralSubtyping);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    var exprType = expr.infer(context);
    if (!(exprType instanceof FuncType gotFunc) || gotFunc.params.size() != 1)
      throw new NotAFunctionException(this, expr, exprType);
    Utils.checkTypeInExpr(gotFunc.params.get(0), gotFunc.ret, this, context.structuralSubtyping);
    return gotFunc.ret;
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
   var t = expr.collectConstraints(context, constraints);
   if (!(t instanceof FuncType tf)) {
     if (t instanceof VarType tx) {
       var x = new VarType();
       constraints.add(new Constraint(tx, new FuncType(List.of(x), x)));
       return x;
     } else throw new NotAFunctionException(this, expr);
   } else {
     if (tf.params.size() != 1) throw new NotAFunctionException(this, expr, tf);
     constraints.add(new Constraint(t, new FuncType(tf.params, tf.params.get(0))));
     return tf.params.get(0);
   }
  }

  @Override
  public String toString() {
    return "fix(%s)".formatted(expr);
  }
}
