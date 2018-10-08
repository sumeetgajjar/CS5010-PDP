package polynomial.bean;

import java.util.Objects;

/**
 * This class represents a Term of the Polynomial. A {@link Term} has a coefficient and a power. The
 * power of the term cannot be less than zero. If an attempt is made to create a term with power
 * less than 0 than it will throw an {@link IllegalArgumentException}.
 */
public class Term {

  private final int coefficient;
  private final int power;

  /**
   * Constructs a Term with given coefficient and power.
   *
   * @param coefficient the coefficient of the term
   * @param power       the power of the term
   * @throws IllegalArgumentException if the given power is less than zero
   */
  public Term(int coefficient, int power) throws IllegalArgumentException {

    if (power < 0) {
      throw new IllegalArgumentException("power of term cannot be negative");
    }

    this.coefficient = coefficient;
    this.power = power;
  }

  /**
   * Constructs a new {@link Term} object with coefficient and power of the given {@link Term}.
   *
   * @param term the term to be copied
   */
  public Term(Term term) {
    this(term.getCoefficient(), term.getPower());
  }

  /**
   * Checks if given obj is equal to this Term.
   *
   * @param obj the object to be compared
   * @return true if given obj is equal to this Term, false otherwise
   */
  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Term)) {
      return false;
    }

    Term that = (Term) obj;
    return (this.coefficient == that.coefficient) && (this.power == that.power);
  }

  /**
   * Generates the hashCode of this Term using coefficient and power.
   *
   * @return the hashCode of this object
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.coefficient, this.power);
  }

  @Override
  public String toString() {
    String sign = this.coefficient >= 0 ? "+" : "";
    String variableString = this.power != 0 ? String.format("x^%d", this.power) : "";

    return String.format("%s%d%s", sign, this.coefficient, variableString);
  }

  /**
   * Returns the evaluation of this Term for given value of x.
   *
   * @param x the given value
   * @return the evaluation of the term for given value of x
   */
  public double evaluate(double x) {
    return coefficient * (Math.pow(x, power));
  }

  /**
   * Returns a new {@link Term} which is the Derivative of this {@link Term}. It throws a {@link
   * ArithmeticException} if the coefficient overflows while differentiating the term.
   *
   * @return a new {@link Term} which is the Derivative of this {@link Term}
   * @throws ArithmeticException if the coefficient overflows while calculating the derivative
   */
  public Term differentiate() throws ArithmeticException {
    int powerOfDerivative = Math.max(0, this.power - 1);
    int coefficientOfDerivative = Math.multiplyExact(this.power, this.coefficient);
    return new Term(coefficientOfDerivative, powerOfDerivative);
  }

  /**
   * Returns the coefficient of this term.
   *
   * @return the coefficient of this term
   */
  public int getCoefficient() {
    return coefficient;
  }

  /**
   * Returns the power of this term.
   *
   * @return the power of this term
   */
  public int getPower() {
    return power;
  }

  /**
   * Returns a new {@link Term} with zero coefficient and zero power.
   *
   * @return a new {@link Term} with zero coefficient and zero power
   */
  public static Term getZeroTerm() {
    return new Term(0, 0);
  }

  /**
   * Returns the result of addition of given terms. Throws an {@link IllegalArgumentException} if
   * the power of the given terms is not same. Throws {@link ArithmeticException} if an overflow
   * occurs while adding the given terms.
   *
   * @param term1 first term to be added
   * @param term2 second term to be added
   * @return the addition of the given terms
   * @throws IllegalArgumentException if the power of the given terms is not same
   * @throws ArithmeticException      if an overflow occurs while adding the given terms
   */
  public static Term addTwoTerms(Term term1, Term term2)
          throws IllegalArgumentException, ArithmeticException {

    if (term1.power != term2.power) {
      throw new IllegalArgumentException("cannot add terms");
    }

    int coefficientSum = Math.addExact(term1.coefficient, term2.coefficient);
    return new Term(coefficientSum, term1.power);
  }
}
