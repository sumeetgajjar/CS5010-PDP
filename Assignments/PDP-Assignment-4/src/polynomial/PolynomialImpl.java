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
    return this.head
            .map(Term::getPower)
            .foldLeft(0, Integer::max);
  }

  @Override
  public int getCoefficient(int power) {
    return this.head
            .filter(term -> term.getPower() == power)
            .foldLeft(0,
                    (runningCoefficientSum, term) -> runningCoefficientSum + term.getCoefficient());
  }

  @Override
  public double evaluate(double x) {
    return this.head
            .map(term -> term.evaluate(x))
            .foldLeft(0D, (a, b) -> {
              double sum = Double.sum(a, b);
              if (!Double.isFinite(sum)) {
                throw new ArithmeticException("overflow occurred while evaluating polynomial");
              }
              return sum;
            });
  }

  @Override
  public Polynomial add(Polynomial that) {

    int degree = that.getDegree();
    if (degree == 0 && that.getCoefficient(0) == 0) {
      GenericListADTNode<Term> headClone = this.head.map(Term::new);
      return new PolynomialImpl(headClone);
    }

    Polynomial dummy = that.add(new PolynomialImpl());

    Polynomial sum = this.head.foldLeft(dummy, (poly, term) -> {
      poly.addTerm(term.getCoefficient(), term.getPower());
      return poly;
    });

    return sum;
  }

  /**
   * Returns the polynomial obtained by differentiating this polynomial.
   *
   * @return the polynomial obtained by differentiating this polynomial
   */
  @Override
  public Polynomial derivative() {
    return new PolynomialImpl(
            this.head
                    .map(Term::differentiate)
                    .filter(Utils::isTermNonZero));
  }

  /**
   * Returns true if all terms in this polynomial are equal to all terms in specified polynomial.
   *
   * @param obj the object to compare with this polynomial
   * @return true if this polynomial is equal to specified obj, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof PolynomialImpl)) {
      return false;
    }

    PolynomialImpl that = (PolynomialImpl) obj;

    return this.head
            .zipAll(that.head, Term::getZeroTerm, Term::getZeroTerm)
            .map(pair -> pair.getFirst().equals(pair.getSecond()))
            .foldLeft(true, Boolean::equals);
  }

  /**
   * Returns the hashCode of this polynomial.
   *
   * @return the hashCode of this polynomial
   */
  @Override
  public int hashCode() {
    return this.head
            .map(Objects::hashCode)
            .foldLeft(1, (hash1, hash2) -> (31 * hash1) + hash2);
  }

  /**
   * Returns the String representation of this polynomial. E.g. <ul>
   * <li>For polynomial "5x^2 +4x^1 −2", toString will return "5x^2+4x^1-2"</li>
   * <li>For polynomial "3 −50x^3 +x^2", toString will return "-50x^3+1x^2+3"</li>
   * <li>For polynomial "4x^1 +2x^5 −3x^2 −10", toString will return "2x^5-3x^2+4x^1-10"</li>
   * </ul>
   *
   * @return the String representation of this polynomial
   */
  @Override
  public String toString() {
    if (this.getNumberOfTerms() == 0) {
      return "0";
    }

    StringBuilder builder = new StringBuilder();
    builder = this.head
            .map(Term::toString)
            .foldLeft(builder, StringBuilder::append);

    return removeLeadingPositiveSign(builder);
  }

  /**
   * Returns the number of terms in this polynomial.
   *
   * @return the number of terms in this polynomial
   */
  private int getNumberOfTerms() {
    return this.head.map(term -> 1)
            .foldLeft(0, Integer::sum);
  }

  /**
   * Strip the leading positive sign from the given StringBuilder and return the string.
   *
   * @param builder builder
   * @return the string with positive sign stripped
   */
  private String removeLeadingPositiveSign(StringBuilder builder) {
    if (builder.length() > 0 && builder.charAt(0) == '+') {
      return builder.substring(1);
    }

    return builder.toString();
  }

  /**
   * Parses the given string into polynomial Terms and adds the same term to current polynomial.
   *
   * @param polynomialString polynomialString to parse
   */
  private void parsePolynomialString(String polynomialString) {
    try (Scanner scanner = new Scanner(polynomialString)) {
      scanner.useDelimiter(TERMS_DELIMITER);

      boolean isFirstTerm = true;
      while (scanner.hasNext()) {
        String termString = scanner.next();
        Term term = this.polynomialTermParser.parsePolynomialTerm(isFirstTerm, termString);

        this.addTerm(term.getCoefficient(), term.getPower());

        isFirstTerm = false;
      }
    }
  }
}
