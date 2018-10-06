package polynomial;

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

  //todo check for -0 only if tests fails
  public PolynomialImpl(String polynomialString) throws IllegalArgumentException {
  }

  public PolynomialImpl() throws IllegalArgumentException {
  }


  @Override
  public void addTerm(int coefficient, int power) throws IllegalArgumentException {

  }

  @Override
  public int getDegree() {
    return 0;
  }

  @Override
  public int getCoefficient(int power) {
    return 0;
  }

  @Override
  public double evaluate(double x) throws ArithmeticException {
    return 0;
  }

  @Override
  public Polynomial add(Polynomial polynomial) {
    return null;
  }

  @Override
  public Polynomial derivative() {
    return null;
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public String toString() {
    return super.toString();
  }

  //todo check if hashcode can be implemented
}
