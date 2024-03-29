package stella.expr;

import stella.checker.Gamma;
import stella.exception.TypeCheckingException;
import stella.pattern.Pattern;
import stella.type.FuncType;
import stella.type.Type;
import stella.type.Types;

import java.util.List;

public class NatRec extends Expr {

  Expr n, z, s;

  public NatRec(Expr n, Expr z, Expr s) {
    this.n = n;
    this.z = z;
    this.s = s;
  }

  @Override
  public void checkTypes(Gamma gamma, Type expected) throws TypeCheckingException {
    n.checkTypes(gamma, Types.NAT);
    var zType = z.infer(gamma);
    s.checkTypes(gamma, new FuncType(List.of(Types.NAT), new FuncType(List.of(zType), zType)));
  }

  @Override
  public Type infer(Gamma gamma) throws TypeCheckingException {
    n.checkTypes(gamma, Types.NAT);
    var zType = z.infer(gamma);
    s.checkTypes(gamma, new FuncType(List.of(Types.NAT), new FuncType(List.of(zType), zType)));
    return zType;
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return new NatRec(
        n.withPattern(pattern, to),
        z.withPattern(pattern, to),
        s.withPattern(pattern, to)
    );
  }

  @Override
  public String toString() {
    return "Nat::rec(%s, %s, %s)".formatted(n, z, s);
  }
}
