package stella;

import stella.checker.TypeChecker;
import stella.exception.TypeCheckingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Main {

  public static void main(String[] args) {
    if (args.length == 0) throw new IllegalArgumentException("Filepath is empty");
    String path = args[0];
    String source;
    try {
      source = Files.readString(Path.of(path));
    } catch (IOException e) {
      System.err.println(e.getMessage());
      throw new RuntimeException(e);
    }
    TypeChecker checker = new TypeChecker(source);
    try {
      checker.typeCheck();
    } catch (TypeCheckingException e) {
      System.err.println(e.getMessage());
      System.exit(-1);
    } catch (Exception e) {
      System.err.println(e.getMessage());
      System.exit(-2);
    }
  }
}
