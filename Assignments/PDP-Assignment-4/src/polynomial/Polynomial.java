package polynomial;

/**
 * Created by gajjar.s,on 5:45 PM, 10/5/18
 */
public interface Polynomial {

  /**
   * It takes a coefficient and a power and adds the resulting term to the polynomial. Both the
   * coefficient and the power should be whole numbers. The power has to be a positive whole number.
   * It throws an IllegalArgumentException if the term is invalid.
   *
   * @param coefficient coefficient of the term
   * @param power       power of the term
   * @throws IllegalArgumentException if the term is invalid
   */
  void addTerm(int coefficient, int power) throws IllegalArgumentException;

  /**
   * Returns the degree of this polynomial.
   *
   * @return the degree of this polynomial
   */
  int getDegree();

  /**
   * Takes a power and returns the coefficient for the term with that power.
   *
   * @param coefficient the coefficient for the term
   * @return power of the given coefficient
   */
  int getCoefficient(int coefficient);

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
   * @return returns the polynomial obtained by adding the this polynomial and given polynomial
   */
  Polynomial add(Polynomial polynomial);

  /**
   * Returns the polynomial obtained by differentiating this polynomial.
   *
   * @return the polynomial obtained by differentiating this polynomial
   */
  Polynomial derivative();
}
