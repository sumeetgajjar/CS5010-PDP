package polynomial.parser;

import java.util.Scanner;

import polynomial.Term;

/**
 * Created by gajjar.s,on 6:13 PM, 10/6/18
 */
public class PolynomialTermParser {

  public Term parsePolynomialTerm(boolean isFirstTerm, String termString) {

    Scanner scanner = new Scanner(termString);
    scanner.useDelimiter("\\.*");

    int coefficient = getCoefficient(isFirstTerm, scanner);
    int power = 0;

    if (scanner.hasNext()) {
      skipVariableAndRaiseToSign(scanner);
      power = getPower(scanner);
      checkIfTermHasEnded(scanner);
    }

    return new Term(coefficient, power);
  }

  private int getPower(Scanner scanner) {
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

  private void checkIfTermHasEnded(Scanner scanner) {
    if (scanner.hasNext()) {
      throw new IllegalArgumentException("Invalid polynomial string");
    }
  }

  private void skipVariableAndRaiseToSign(Scanner scanner) {
    if (scanner.hasNext("x")) {
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
