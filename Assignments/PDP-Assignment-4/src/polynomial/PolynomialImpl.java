package polynomial;

/**
 * Created by gajjar.s,on 8:05 PM, 10/5/18
 */

/**
 * This class represents a Polynomial. It implements {@link Polynomial} interface.
 *
 * "" is a valid string
 * -ve powers are not allowed
 *  missing powers are not allowed
 *  missing coefficient are not allowed
 *  missing term are not allowed
 *  repetition of same power terms allowed
 *  invalid letters not allowed
 */
public class PolynomialImpl implements Polynomial {

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
  public double evaluate(double x) {
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
}
