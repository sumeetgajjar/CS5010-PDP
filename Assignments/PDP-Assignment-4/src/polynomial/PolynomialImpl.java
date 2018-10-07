package polynomial;

import java.util.Comparator;
import java.util.Objects;
import java.util.Scanner;

import polynomial.bean.Term;
import polynomial.listutil.GenericEmptyNode;
import polynomial.listutil.GenericListADTNode;
import polynomial.parser.SingleVariablePolynomialTermParser;
import util.Utils;

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

  private PolynomialImpl(GenericListADTNode<Term> head) {
    this.head = head;
    this.polynomialTermParser = new SingleVariablePolynomialTermParser();
  }

  public PolynomialImpl(String polynomialString) throws IllegalArgumentException {
    this(new GenericEmptyNode<>());

    if (Objects.isNull(polynomialString)) {
      throw new IllegalArgumentException("Invalid polynomial string");
    }

    if (polynomialString.length() == 0) {
      polynomialString = "0";
    }
    this.parsePolynomialString(polynomialString);
  }

  public PolynomialImpl() throws IllegalArgumentException {
    this("");
  }

  @Override
  public void addTerm(int coefficient, int power) throws IllegalArgumentException {
    this.head = this.head
            .insert(
                    new Term(coefficient, power),
                    Comparator.comparingInt(Term::getPower),
                    Term::addTwoTerms)
            .filter(Utils::isTermNonZero);
  }

  @Override
  public int getDegree() {
    if (this.head.count() == 0) {
      return 0;
    }
    return this.head.get(0).getPower();
  }

  @Override
  public int getCoefficient(int power) {
    return this.head
            .filter(term -> term.getPower() == power)
            .fold(0, (integer, term) -> integer + term.getCoefficient());
  }

  @Override
  public double evaluate(double x) {
    return this.head
            .map(term -> term.evaluate(x))
            .fold(0D, Double::sum);
  }

  @Override
  public Polynomial add(Polynomial polynomial) {
    if (polynomial instanceof PolynomialImpl) {
      PolynomialImpl that = (PolynomialImpl) polynomial;
      GenericListADTNode<Term> sum = this.head
              .combine(
                      that.head,
                      Comparator.comparingInt(Term::getPower),
                      Term::addTwoTerms)
              .filter(Utils::isTermNonZero);

      return new PolynomialImpl(sum);
    }
    throw new IllegalArgumentException("cannot add polynomials of different implementations");
  }

  @Override
  public Polynomial derivative() {
    return new PolynomialImpl(
            this.head
                    .map(Term::differentiate)
                    .filter(Utils::isTermNonZero));
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof PolynomialImpl)) {
      return false;
    }

    PolynomialImpl that = (PolynomialImpl) obj;
    return this.head.equals(that.head);
  }

  @Override
  public int hashCode() {
    return this.head
            .map(Objects::hashCode)
            .fold(1, (hash1, hash2) -> (31 * hash1) + hash2);
  }

  @Override
  public String toString() {
    if (this.head.count() == 0) {
      return "0";
    }

    StringBuilder builder = new StringBuilder();
    builder = this.head
            .map(Term::toString)
            .fold(builder, StringBuilder::append);

    return removeLeadingPositiveSign(builder);
  }

  private String removeLeadingPositiveSign(StringBuilder builder) {
    if (builder.length() > 0 && builder.charAt(0) == '+') {
      return builder.substring(1);
    }

    return builder.toString();
  }

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
