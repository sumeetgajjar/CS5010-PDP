package polynomial;

/**
 * This interface represents a Polynomial and all operations that can be performed on a polynomial.
 */
public interface Polynomial {

  /**
   * It takes a coefficient and a power and adds the resulting term to this polynomial. Both the
   * coefficient and the power should be whole numbers. The power has to be a positive whole number.
   * It throws an IllegalArgumentException if the term is invalid.
   *
   * @param coefficient coefficient of the term
   * @param power       power of the term
   * @throws IllegalArgumentException if the term is invalid
   */
  void addTerm(int coefficient, int power) throws IllegalArgumentException;

  /**
   * Returns the degree of this polynomial. In case of empty polynomial it return 0 as its degree.
   *
   * @return the degree of this polynomial
   */
  int getDegree();

  /**
   * Takes a power and returns the coefficient for the term with that power. If the term does not
   * exists with the given power then it returns 0 as its coefficient.
   *
   * @param power the power for the term
   * @return coefficient of the given power
   */
  int getCoefficient(int power);

  /**
   * Returns the the evaluation of this polynomial using given x value.
   *
   * @param x value of x for this polynomial
   * @return evaluation of this polynomial using given value x.
   */
  double evaluate(double x);

  /**
   * Takes an Polynomial object and returns the polynomial obtained by adding the two polynomials.
   * Any implementation should ensure that this method does not mutate either polynomial.
   *
   * @param polynomial another {@link Polynomial}
   * @return returns the polynomial obtained by adding this polynomial and given polynomial
   */
  Polynomial add(Polynomial polynomial);

  /**
   * Returns the polynomial obtained by differentiating this polynomial.
   *
   * @return the polynomial obtained by differentiating this polynomial
   */
  Polynomial derivative();
}
