package polynomial.parser;

import java.util.Scanner;

import polynomial.bean.Term;

/**
 * This class represents a Single Variable Polynomial Term Parser. It implements {@link
 * PolynomialTermParser}. It can be used to parse polynomial String of polynomials with one
 * variable.
 */
public class SingleVariablePolynomialTermParser implements PolynomialTermParser {

  private final String variable;

  /**
   * Constructs a {@link SingleVariablePolynomialTermParser} object to parse polynomial strings of
   * given variable.
   *
   * @param variable the variable
   */
  public SingleVariablePolynomialTermParser(String variable) {
    this.variable = variable;
  }

  /**
   * Returns a new {@link Term} object parsed from the given String. The only valid format of input
   * strings are as follows: <ul>
   * <li>10</li>
   * <li>10x^0</li>
   * <li>1x^1</li>
   * <li>10x^1</li>
   * </ul>
   *
   * <p>Depending on value of isFirstTerm the following conditions holds true
   * <ul>
   * <li>if isFirstTerm = true: then the string may or may not start with '+' or '-' sign.</li>
   * <li>if isFirstTerm = false: then the string should start with '+' or '-' sign, else {@link
   * IllegalArgumentException} will be thrown.</li>
   * </ul>
   *
   * @param isFirstTerm does the given string corresponds to the first term
   * @param termString  the string to parse
   * @return the parsed string
   * @throws IllegalArgumentException if the given string is invalid
   */
  @Override
  public Term parsePolynomialTerm(boolean isFirstTerm, String termString)
          throws IllegalArgumentException {

    int coefficient;
    int power;

    try (Scanner scanner = new Scanner(termString)) {
      scanner.useDelimiter("");

      coefficient = getCoefficient(isFirstTerm, scanner);
      power = 0;

      if (scanner.hasNext()) {
        skipVariableAndRaiseToSign(scanner);
        power = getPower(scanner);
        checkIfTermHasEnded(scanner);
      }
    }

    return new Term(coefficient, power);
  }

  /**
   * Gets the integer value for power of term by reading the next string using {@link Scanner} obj.
   * If unable to parse power of term then it throws {@link IllegalArgumentException}.
   *
   * @param scanner scanner to read from
   * @return the integer value for power of term
   * @throws IllegalArgumentException if unable to parse power of term
   */
  private int getPower(Scanner scanner) throws IllegalArgumentException {
    StringBuilder powerString = new StringBuilder();

    if (scanner.hasNext("\\d")) {
      while (scanner.hasNext("\\d")) {
        powerString.append(scanner.next());
      }
    } else {
      throw new IllegalArgumentException("Invalid polynomial string");
    }

    try {
      return Integer.parseInt(powerString.toString());
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid polynomial string");
    }
  }

  /**
   * Checks if the more string is left to be read by Scanner object. Throws {@link
   * IllegalArgumentException} if scanner has more string to read.
   *
   * @param scanner scanner object to check
   * @throws IllegalArgumentException if the given scanner object has more string to read
   */
  private void checkIfTermHasEnded(Scanner scanner) throws IllegalArgumentException {
    if (scanner.hasNext()) {
      throw new IllegalArgumentException("Invalid polynomial string");
    }
  }

  /**
   * Check if next string is "{@link SingleVariablePolynomialTermParser#variable}" followed by "^".
   * It not then it throws {@link IllegalArgumentException}.
   *
   * @param scanner scanner object to read from
   * @throws IllegalArgumentException if the given string is invalid
   */
  private void skipVariableAndRaiseToSign(Scanner scanner) throws IllegalArgumentException {
    if (scanner.hasNext(this.variable)) {
      scanner.next();
    } else {
      throw new IllegalArgumentException("Invalid polynomial string");
    }

    if (scanner.hasNext("\\^")) {
      scanner.next();
    } else {
      throw new IllegalArgumentException("Invalid polynomial string");
    }
  }

  /**
   * Gets the integer value for coefficient of term by reading next string using {@link Scanner}.
   * Following conditions must hold based on value of isFirstTerm: <ul>
   * <li>if isFirstTerm = true: then the next string may or may not start with '+' or '-'
   * sign.</li>
   * <li>if isFirstTerm = false: then the next string should start with '+' or '-' sign else {@link
   * IllegalArgumentException} will be thrown.</li>
   * </ul>
   *
   * <p>If unable to parse coefficient of term then it throws {@link IllegalArgumentException}.
   *
   * @param isFirstTerm is the next string read from scanner is of first term
   * @param scanner     scanner to read from
   * @return the integer value for coefficient of term
   * @throws IllegalArgumentException if unable to parse coefficient of term
   */
  private int getCoefficient(boolean isFirstTerm, Scanner scanner) throws IllegalArgumentException {
    StringBuilder coefficientString = new StringBuilder();

    if (scanner.hasNext("-|\\+")) {
      String coefficientSign = scanner.next();
      coefficientString.append(coefficientSign);
    } else {
      if (!isFirstTerm) {
        throw new IllegalArgumentException("Invalid polynomial string");
      }
    }

    if (scanner.hasNext("\\d")) {
      while (scanner.hasNext("\\d")) {
        coefficientString.append(scanner.next());
      }
    } else {
      throw new IllegalArgumentException("Invalid polynomial string");
    }

    try {
      return Integer.parseInt(coefficientString.toString());
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("Invalid polynomial string");
    }
  }
}
