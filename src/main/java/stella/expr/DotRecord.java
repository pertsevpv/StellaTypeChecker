package stella.expr;

import stella.checker.Gamma;
import stella.exception.*;
import stella.pattern.Pattern;
import stella.type.RecordType;
import stella.type.Type;
import stella.utils.Utils;

public class DotRecord extends Expr {

  public Expr record;
  public String label;

  public DotRecord(Expr record, String label) {
    this.record = record;
    this.label = label;
  }

  @Override
  public void checkTypes(Gamma gamma, Type expected) throws TypeCheckingException {
    var exprType = record.infer(gamma);
    if (!(exprType instanceof RecordType recordType)) throw new NotARecordException(record, exprType);
    if (!recordType.containLabel(label)) throw new UnexpectedFieldAccessException(label, recordType);
    Utils.checkTypeInExpr(expected, recordType.get(label), this);
  }

  @Override
  public Type infer(Gamma gamma) throws TypeCheckingException {
    var exprType = record.infer(gamma);
    if (!(exprType instanceof RecordType recordType)) throw new NotARecordException(record, exprType);
    return recordType.get(label);
  }

  @Override
  public Expr withPattern(Pattern pattern, Expr to) {
    return null;
  }

  @Override
  public String toString() {
    return "%s.%s".formatted(record, label);
  }
}
