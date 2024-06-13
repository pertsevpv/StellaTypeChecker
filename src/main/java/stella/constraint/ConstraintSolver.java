package stella.constraint;

import stella.exception.OccursCheckInfiniteTypeException;
import stella.exception.TypeCheckingException;
import stella.type.*;
import stella.utils.Pair;
import stella.utils.Utils;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class ConstraintSolver {

  public LinkedList<Pair<VarType, Type>> solve(LinkedList<Constraint> constraints) throws TypeCheckingException {
    if (constraints.isEmpty()) return new LinkedList<>();

    var c = constraints.removeFirst();
    if (c.left instanceof VarType && c.right instanceof VarType && c.left.equals(c.right))
      return solve(constraints);
    if (c.left instanceof VarType cx) {
      if (occursIn(cx, c.right)) throw new OccursCheckInfiniteTypeException(c);
      return plus(
          solve(sub(constraints, cx, c.right)),
          new Pair<>(cx, c.right)
      );
    }
    if (c.right instanceof VarType cx) {
      if (occursIn(cx, c.left)) throw new OccursCheckInfiniteTypeException(c);
      return plus(
          solve(sub(constraints, cx, c.left)),
          new Pair<>(cx, c.left)
      );
    }
    if (c.left instanceof FuncType lf && c.right instanceof FuncType rf) {
      return solve(plus(
          constraints,
          new Constraint(lf.params.get(0), rf.params.get(0)),
          new Constraint(lf.ret, rf.ret)
      ));
    }
    if (c.left instanceof ListType ll && c.right instanceof ListType rl) {
      return solve(plus(
          constraints,
          new Constraint(ll.listType, rl.listType)
      ));
    }
    if (c.left instanceof SumType ls && c.right instanceof SumType rs) {
      return solve(plus(
          constraints,
          new Constraint(ls.left, rs.left),
          new Constraint(ls.right, rs.right)
      ));
    }
    if (c.left instanceof TupleType lt && c.right instanceof TupleType rt) {
      return solve(plus(
          constraints,
          new Constraint(lt.get(1), rt.get(1)),
          new Constraint(lt.get(2), rt.get(2))
      ));
    }
    Utils.checkTypeInExpr(c.left, c.right, null, false);
    return solve(constraints);
  }

  private static boolean occursIn(VarType varType, Type toCheck) {
    if (toCheck == Types.NAT ||
        toCheck == Types.BOOL ||
        toCheck == Types.UNIT ||
        toCheck == Types.TOP ||
        toCheck == Types.BOTTOM
    ) return false;
    if (toCheck instanceof FuncType funcType)
      return funcType.params.stream().anyMatch(t -> ConstraintSolver.occursIn(varType, t)) || occursIn(varType, funcType.ret);
    if (toCheck instanceof TupleType tupleType)
      return occursIn(varType, tupleType.get(1)) || occursIn(varType, tupleType.get(2));
    if (toCheck instanceof SumType sumType) return occursIn(varType, sumType.left) || occursIn(varType, sumType.right);
    if (toCheck instanceof ListType listType) return occursIn(varType, listType.listType);
    if (toCheck instanceof VarType x) {
      return varType.equals(x);
    }
    return false;
  }

  private static LinkedList<Constraint> sub(LinkedList<Constraint> constraints, VarType varType, Type sub) {
    return constraints.stream().map(c -> sub(c, varType, sub)).collect(Collectors.toCollection(LinkedList::new));
  }

  private static Constraint sub(Constraint c, VarType varType, Type sub) {
    return new Constraint(c.left.sub(varType, sub), c.right.sub(varType, sub));
  }

  private <T> LinkedList<T> plus(LinkedList<T> a, List<T> b) {
    LinkedList<T> r = new LinkedList<>(a);
    r.addAll(b);
    return r;
  }

  private <T> LinkedList<T> plus(LinkedList<T> a, List<T> b, T c) {
    LinkedList<T> r = new LinkedList<>(a);
    r.addAll(b);
    r.add(c);
    return r;
  }

  private <T> LinkedList<T> plus(LinkedList<T> a, T b) {
    return plus(a, Collections.singletonList(b));
  }

  private <T> LinkedList<T> plus(LinkedList<T> a, T b, T c) {
    return plus(a, Arrays.asList(b, c));
  }
}
