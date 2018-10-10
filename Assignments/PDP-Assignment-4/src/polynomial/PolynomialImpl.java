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
 * This class represents a Polynomial. It implements {@link Polynomial} interface. This polynomial
 * does not support terms with negative powers. It supports terms with only single variable "x".
 */
public class PolynomialImpl implements Polynomial {

  /**
   * Delimiter for terms in polynomial string.
   */
  private static final String TERMS_DELIMITER = " ";

  /**
   * parser to parse th polynomial string.
   */
  private final SingleVariablePolynomialTermParser polynomialTermParser;

  /**
   * head of the list of terms.
   */
  private GenericListADTNode<Term> head;

  /**
   * Constructs a {@link PolynomialImpl } object with given list of terms.
   *
   * @param head the head of list of terms
   */
  private PolynomialImpl(GenericListADTNode<Term> head) {
    this.head = head;
    this.polynomialTermParser = new SingleVariablePolynomialTermParser("x");
  }

  /**
   * Constructs a {@link PolynomialImpl} object by parsing the given polynomialString. It throws a
   * {@link IllegalArgumentException} if the specified string is invalid. A input of empty "" String
   * will constructs a empty {@link Polynomial}. The polynomialString should comply to following
   * rules.<ul>
   * <li>-ve powers for term are not allowed. E.g. "1x^-2" is invalid</li>
   * <li>Missing powers for the term are not allowed, missing powers will not be inferred E.g. "1x"
   * is invalid</li>
   * <li>Missing coefficient for the term is not allowed, missing coefficient will not be inferred
   * E.g. "x^2" is invalid</li>
   * <li>The first term in the string may or may not start with a sign, E.g. "+4x^2" and "4x^2"
   * both are valid</li>
   * <li>Missing operators in between the terms is not allowed, missing operator will not be
   * inferred, E.g. "10x^2 1x^1" is invalid</li>
   * <li>variables apart from x are not allowed, E.g. "y^2" is invalid</li>
   * <li>Extra spaces between two consecutive terms is not allowed, E.g. "4x^1   +2x^4" is
   * invalid</li>
   * <li>leading and trailing spaces are not allowed for the string E.g. "  1x^3  "</li>
   * <li>Only coefficient is allowed, E.g. "1" is valid</li>
   * <li>Repetition of terms with same power is allowed, E.g. "4x^2 +10 +3x^2" is valid</li>
   * </ul>
   *
   * <p>The following examples are valid examples of polynomialString
   * <ul>
   * <li>"4x^3 +3x^1 -5"</li>
   * <li>"-3x^4 -2x^5 -5 +11x^1"</li>
   * <li>"102"</li>
   * <li>"+3x^4 -2x^5 -5 -2x^4 +11x^1"</li>
   * </ul>
   *
   * @param polynomialString the polynomialString to parse
   * @throws IllegalArgumentException if the given polynomialString is invalid
   */
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

  /**
   * Constructs a empty {@link PolynomialImpl} object.
   */
  public PolynomialImpl() throws IllegalArgumentException {
    this("");
  }

  /**
   * It takes a coefficient and a power and adds the resulting term to this polynomial. Both the
   * coefficient and the power should be whole numbers. The power has to be a positive whole number.
   * It throws an IllegalArgumentException if the term is invalid.
   *
   * @param coefficient coefficient of the term
   * @param power       power of the term
   * @throws IllegalArgumentException if the term is invalid
   */
  @Override
  public void addTerm(int coefficient, int power) throws IllegalArgumentException {
    this.head = this.head
            .insert(
                    new Term(coefficient, power),
                    Comparator.comparingInt(Term::getPower),
                    Term::addTwoTerms)
            .filter(Utils::isTermNonZero);
  }

  /**
   * Returns the degree of this polynomial. In case of empty polynomial it return 0 as its degree.
   *
   * @return the degree of this polynomial
   */
  @Override
  public int getDegree() {
    return this.head
            .map(Term::getPower)
            .foldLeft(0, Integer::max);
  }

  /**
   * Takes a power and returns the coefficient for the term with that power. If the term does not
   * exists with the given power then it returns 0 as its coefficient.
   *
   * @param power the power for the term
   * @return coefficient of the given power
   */
  @Override
  public int getCoefficient(int power) {
    return this.head
            .filter(term -> term.getPower() == power)
            .foldLeft(0, (runningCoefficientSum, term) ->
                    runningCoefficientSum + term.getCoefficient());
  }

  /**
   * Returns the the evaluation of this polynomial using given x value. It throws {@link
   * ArithmeticException} if the result of evaluation is not finite.
   *
   * @param x value of x for this polynomial
   * @return evaluation of this polynomial using given value x.
   * @throws ArithmeticException if the result of evaluation is not finite
   */
  @Override
  public double evaluate(double x) throws ArithmeticException {
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

  /**
   * Returns a new {@link Polynomial} object by adding this and specified {@link Polynomial}. This
   * method does not mutate either polynomial.
   *
   * @param that another {@link Polynomial}
   * @return returns the polynomial obtained by adding this polynomial and given polynomial
   */
  @Override
  public Polynomial add(Polynomial that) {

    int degree = that.getDegree();
    if (degree == 0 && that.getCoefficient(0) == 0) {
      return getThisPolynomialClone();
    }

    Polynomial dummy = that.add(new PolynomialImpl());

    Polynomial sum = this.head.foldLeft(dummy, (poly, term) -> {
      poly.addTerm(term.getCoefficient(), term.getPower());
      return poly;
    });

    return sum;
  }

  /**
   * Returns a new polynomial obtained by differentiating this polynomial. It does not mutate the
   * current polynomial.
   *
   * @return a new polynomial obtained by differentiating this polynomial
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

  /**
   * Returns the clone of this Polynomial.
   *
   * @return the clone of this Polynomial
   */
  private Polynomial getThisPolynomialClone() {
    GenericListADTNode<Term> headClone = this.head.map(Term::new);
    return new PolynomialImpl(headClone);
  }
}
