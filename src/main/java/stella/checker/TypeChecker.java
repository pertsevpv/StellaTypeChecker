package stella.checker;

import org.antlr.v4.runtime.*;
import stella.exception.IncorrectArityOfMainException;
import stella.exception.MissingMainException;
import stella.exception.TypeCheckingException;
import stella.exception.UnexpectedTypeForExpressionException;
import stella.expr.Expr;
import stella.parser.gen.StellaLexer;
import stella.parser.gen.StellaParser;
import stella.type.FuncType;
import stella.type.Type;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.stream.Collectors;

import static stella.parser.gen.StellaParser.*;
import static stella.parser.walker.StellaExprWalker.handleExpr;
import static stella.parser.walker.StellaTypeWalker.handleType;

public class TypeChecker {

  ProgramContext program;
  Context context = new Context();

  public TypeChecker(String source) throws CancellationException {
    StellaLexer lexer = new StellaLexer(CharStreams.fromString(source));
    TokenStream tokenStream = new CommonTokenStream(lexer);
    var parser = new StellaParser(tokenStream);
    parser.setErrorHandler(new BailErrorStrategy());
    this.program = parser.program();
  }

  public void typeCheck() throws TypeCheckingException {
    var decls = program.decls;
    checkForMain(program.decls);
    Set<String> extensions = program.extensions
        .stream()
        .map(it -> (AnExtensionContext) it)
        .flatMap(it -> it.extensionNames.stream())
        .map(Token::getText).collect(Collectors.toSet());

    context.structuralSubtyping = extensions.contains("#structural-subtyping");
    context.ambiguousTypeAsBottom = extensions.contains("#ambiguous-type-as-bottom");

  for (var decl: decls) {
      if (decl instanceof DeclFunContext declFun) checkFun(declFun);
      if (decl instanceof DeclExceptionTypeContext exceptionType) {
        context.exceptionType = handleType(exceptionType.exceptionType);
      }
    }
  }

  public void checkFun(DeclFunContext declFun) throws TypeCheckingException {
    context.enterGamma();
    var funcName = declFun.name.getText();
    LinkedList<Type> params = null;
    if (declFun.paramDecls != null) {
      params = new LinkedList<>();
      for (var param: declFun.paramDecls) {
        var name = param.name.getText();
        var type = handleType(param.stellatype());
        context.put(name, type);
        params.addLast(type);
      }
    }
    for (var decl: declFun.localDecls) {
      if (decl instanceof DeclFunContext nested) checkFun(nested);
    }

    Type returnType;
    FuncType funcType;
    Expr returnExpr = handleExpr(declFun.returnExpr);
    Type returnExprType;

    if (declFun.returnType == null) {
      returnExprType = returnExpr.infer(context);
      returnType = returnExprType;
      funcType = new FuncType(params, returnType);
      context.gamma.parent.put(funcName, funcType);
    } else {
      returnType = handleType(declFun.returnType);
      funcType = new FuncType(params, returnType);
      context.gamma.parent.put(funcName, funcType);
      returnExpr.checkTypes(context, returnType);
    }
    context.exitGamma();
  }

  private void checkForMain(List<DeclContext> decls) throws TypeCheckingException {
    DeclFunContext main = decls.stream()
        .filter(decl -> decl instanceof StellaParser.DeclFunContext)
        .map(decl -> (StellaParser.DeclFunContext) decl)
        .filter(decl -> decl.name.getText().equals("main"))
        .findFirst()
        .orElseThrow(MissingMainException::new);
    if (main.paramDecls.size() != 1) throw new IncorrectArityOfMainException(main.paramDecls.size());
  }

  private static void check(Type expected, Type got, Expr expr) throws UnexpectedTypeForExpressionException {
    if (!expected.equals(got)) throw new UnexpectedTypeForExpressionException(expected, got, expr);
  }

}
