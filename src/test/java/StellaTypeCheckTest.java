import org.junit.jupiter.api.Test;
import stella.checker.TypeChecker;
import stella.exception.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CancellationException;

public class StellaTypeCheckTest {

  @Test
  void testOk() {
    List<String> failed = new ArrayList<>();
    int okCnt = 0;
    for (var path: getOkFiles()) {
      var source = readFile(path);
      try {
        TypeChecker checker = new TypeChecker(source);
        checker.typeCheck();
        okCnt++;
      } catch (CancellationException e) {
        System.out.println("Wrong grammar for file" + path.getFileName().toString());
      } catch (Exception e) {
        System.out.print(path.getFileName() + ": ");
        System.out.println(e.getMessage());
        failed.add(path.getFileName().toString());
      }
    }
    System.out.println();
    System.out.println("Totally ok: " + okCnt);
    System.out.println("Totally failed: " + failed.size());
    failed.forEach(System.out::println);
  }

  @Test
  void testBad() {
    testBad("ERROR_AMBIGUOUS_LIST", AmbiguousListException.class);
    testBad("ERROR_ILLEGAL_EMPTY_MATCHING", IllegalEmptyMatchingException.class);
    testBad("ERROR_INCORRECT_ARITY_OF_MAIN", IncorrectArityOfMainException.class);
    testBad("ERROR_INCORRECT_NUMBER_OF_ARGUMENTS", IncorrectNumberOfArguments.class);
//    testBad("ERROR_MISSING_DATA_FOR_LABEL", );
    testBad("ERROR_MISSING_MAIN", MissingMainException.class);
    testBad("ERROR_MISSING_RECORD_FIELDS", MissingRecordFieldsException.class);
    testBad("ERROR_NONEXHAUSTIVE_MATCH_PATTERNS", NonExhaustiveMatchPatterns.class);
    testBad("ERROR_NOT_A_FUNCTION", NotAFunctionException.class);
    testBad("ERROR_NOT_A_LIST", NotAListException.class);
    testBad("ERROR_NOT_A_RECORD", NotARecordException.class);
    testBad("ERROR_NOT_A_TUPLE", NotATupleException.class);
    testBad("ERROR_TUPLE_INDEX_OUT_OF_BOUNDS", TupleIndexOutOfBoundsException.class);
    testBad("ERROR_UNDEFINED_VARIABLE", UndefinedVariableException.class);
//    testBad("ERROR_UNEXPECTED_DATA_FOR_NULLARY_LABEL", );
    testBad("ERROR_UNEXPECTED_FIELD_ACCESS", UnexpectedFieldAccessException.class);
    testBad("ERROR_UNEXPECTED_INJECTION", UnexpectedInjectionException.class);
    testBad("ERROR_UNEXPECTED_LAMBDA", UnexpectedLambdaException.class);
    testBad("ERROR_UNEXPECTED_LIST", UnexpectedListException.class);
//    testBad("ERROR_UNEXPECTED_NON_NULLARY_VARIANT_PATTERN", );
//    testBad("ERROR_UNEXPECTED_NULLARY_VARIANT_PATTERN", );
    testBad("ERROR_UNEXPECTED_NUMBER_OF_PARAMETERS_IN_LAMBDA", UnexpectedNumberOfParametersInLambda.class);
    testBad("ERROR_UNEXPECTED_PATTERN_FOR_TYPE", UnexpectedPatternForTypeException.class);
    testBad("ERROR_UNEXPECTED_RECORD", UnexpectedRecordException.class);
    testBad("ERROR_UNEXPECTED_RECORD_FIELDS", UnexpectedRecordFieldException.class);
    testBad("ERROR_UNEXPECTED_TUPLE", UnexpectedTupleException.class);
    testBad("ERROR_UNEXPECTED_TUPLE_LENGTH", UnexpectedTupleLengthException.class);
    testBad("ERROR_UNEXPECTED_TYPE_FOR_EXPRESSION", UnexpectedTypeForExpressionException.class);
    testBad("ERROR_UNEXPECTED_TYPE_FOR_PARAMETER", UnexpectedTypeForParameterException.class);
    testBad("ERROR_UNEXPECTED_VARIANT", UnexpectedVariantException.class);
    testBad("ERROR_UNEXPECTED_VARIANT_LABEL", UnexpectedVariantLabelException.class);
  }

  <T extends TypeCheckingException> void testBad(String dir, Class<T> clazz) {
    System.out.println("TESTING " + dir);
    List<String> failed = new ArrayList<>();
    List<String> wrongError = new ArrayList<>();
    int okCnt = 0;
    for (var path: getBadFiles(dir)) {
      var source = readFile(path);
      try {
        TypeChecker checker = new TypeChecker(source);
        checker.typeCheck();
        failed.add(path.getFileName().toString());
      } catch (CancellationException e) {
        System.out.println("Wrong grammar for file" + path.getFileName().toString());
      } catch (TypeCheckingException e) {
        if (clazz.isInstance(e)) okCnt++;
        else wrongError.add(path.getFileName().toString());
      } catch (Exception e) {
        System.out.println(e.getMessage());
        failed.add(path.getFileName().toString());
      }
    }
    System.out.println("Totally ok: " + okCnt);
    if (!failed.isEmpty()) System.out.println("Totally failed: " + failed.size());
    failed.forEach(System.out::println);
    if (!wrongError.isEmpty()) System.out.println("Wrong error for: " + wrongError.size());
    wrongError.forEach(System.out::println);
    System.out.println();
  }


  private List<Path> getOkFiles() {
    try (var pathStream = Files.list(Path.of("src/test/resources/ok"))) {
      return pathStream.toList();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private List<Path> getBadFiles(String dir) {
    try (var pathStream = Files.list(Path.of("src/test/resources/bad", dir))) {
      return pathStream.toList();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  private String readFile(Path path) {
    try {
      return Files.readString(path);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}
