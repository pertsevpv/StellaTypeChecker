package stella.expr;

import stella.checker.Context;
import stella.constraint.Constraint;
import stella.exception.NotARecordException;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedFieldAccessException;
import stella.type.RecordType;
import stella.type.Type;
import stella.utils.Utils;

import java.util.List;

public class DotRecord extends Expr {

  public Expr record;
  public String label;

  public DotRecord(Expr record, String label) {
    this.record = record;
    this.label = label;
  }

  @Override
  public void checkTypes(Context context, Type expected) throws TypeCheckingException {
    var exprType = record.infer(context);
    if (!(exprType instanceof RecordType recordType)) throw new NotARecordException(record, exprType);
    if (!context.structuralSubtyping && !recordType.containLabel(label))
      throw new UnexpectedFieldAccessException(label, recordType);
    Utils.checkTypeInExpr(expected, recordType.get(label), this, context.structuralSubtyping);
  }

  @Override
  public Type infer(Context context) throws TypeCheckingException {
    var exprType = record.infer(context);
    if (!(exprType instanceof RecordType recordType)) throw new NotARecordException(record, exprType);
    return recordType.get(label);
  }

  @Override
  public Type collectConstraints(Context context, List<Constraint> constraints) throws TypeCheckingException {
    throw new UnsupportedOperationException();
  }

  @Override
  public String toString() {
    return "%s.%s".formatted(record, label);
  }
}
