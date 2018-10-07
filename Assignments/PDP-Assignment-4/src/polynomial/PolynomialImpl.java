package polynomial;

import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;

import polynomial.parser.SingleVariablePolynomialTermParser;
import util.Utils;
import util.list.GenericEmptyNode;
import util.list.GenericListADTNode;

/**
 * This class represents a Polynomial. It implements {@link Polynomial} interface.
 *
 * "" is a valid string
 *
 * -ve powers are not allowed
 *
 * missing powers are not allowed
 *
 * missing coefficient are not allowed
 *
 * missing term are not allowed
 *
 * repetition of same power terms allowed
 *
 * invalid letters not allowed
 *
 * no extra spaces are allowed in between
 *
 * no leading and trailing spaces are allowed
 *
 * evaluation can cause overflow
 */
public class PolynomialImpl implements Polynomial {

  private static final String TERMS_DELIMITER = " ";

  private final SingleVariablePolynomialTermParser polynomialTermParser;

  private GenericListADTNode<Term> head;

  //todo check for -0 only if tests fails
  public PolynomialImpl(String polynomialString) throws IllegalArgumentException {

    if (Objects.isNull(polynomialString)) {
      throw new IllegalArgumentException("Invalid polynomial string");
    }

    if (polynomialString.length() == 0) {
      polynomialString = "0";
    }

    this.head = new GenericEmptyNode<>();
    this.polynomialTermParser = new SingleVariablePolynomialTermParser();
    this.parsePolynomialString(polynomialString);
  }

  public PolynomialImpl() throws IllegalArgumentException {
    this("");
  }

  private PolynomialImpl(GenericListADTNode<Term> head) {
    this.head = head;
    polynomialTermParser = new SingleVariablePolynomialTermParser();
  }

  @Override
  public void addTerm(int coefficient, int power) throws IllegalArgumentException {
    this.head = this.head.insert(
            new Term(coefficient, power),
            Comparator.comparingInt(Term::getPower),
            Term::addTerm)
            .filter(Utils::isTermNonZero);
  }

  @Override
  public int getDegree() {
    return this.head.get(0).getPower();
  }

  @Override
  public int getCoefficient(int power) {
    return this.head.filter(term -> term.getPower() == power)
            .fold(0, (integer, term) -> integer + term.getCoefficient());
  }

  @Override
  public double evaluate(double x) {
    return this.head.map(term -> term.evaluate(x))
            .fold(0D, Double::sum);
  }

  @Override
  public Polynomial add(Polynomial polynomial) {
    return null;
  }

  @Override
  public Polynomial derivative() {
    return new PolynomialImpl(this.head.map(Term::differentiate).filter(Utils::isTermNonZero));
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public String toString() {
    if (this.head.count() == 0) {
      return "0";
    }

    StringBuilder builder = new StringBuilder();
    builder = this.head.map(Term::toString)
            .fold(builder, StringBuilder::append);

    if (builder.length() > 0 && builder.charAt(0) == '+') {
      return builder.substring(1);
    }

    return builder.toString();
  }

  //todo check if hashcode can be implemented


  private void parsePolynomialString(String polynomialString) {
    try (Scanner scanner = new Scanner(polynomialString)) {
      scanner.useDelimiter(TERMS_DELIMITER);

      boolean isFirstTerm = true;
      while (scanner.hasNext()) {
        String termString = scanner.next();
        Term term = this.polynomialTermParser.parsePolynomialTerm(isFirstTerm, termString);

        addTerm(term.getCoefficient(), term.getPower());

        isFirstTerm = false;
      }
    }
  }
}
