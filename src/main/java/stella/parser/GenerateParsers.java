package stella.parser;

import org.antlr.v4.Tool;


public class GenerateParsers {

  public static final String OUTPUT = "src/main/java/stella/parser/gen";
  public static final String PACKAGE = "stella.parser.gen";
  public static final String GRAMMAR_PATH = "src/main/resources/grammar/stella/";

  public static void main(String[] args) {
    args = new String[] {
        "-o", OUTPUT,
        "-package", PACKAGE,
        "-Xexact-output-dir",
        GRAMMAR_PATH + "StellaLexer.g4",
        GRAMMAR_PATH + "StellaParser.g4"
    };
    new Tool(args).processGrammarsOnCommandLine();
  }
}
