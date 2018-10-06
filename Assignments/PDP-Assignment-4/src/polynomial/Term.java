package polynomial;

/**
 * Created by gajjar.s,on 4:26 PM, 10/6/18
 */
public class Term {

  private final int coefficient;
  private final int power;

  public Term(int coefficient, int power) throws IllegalArgumentException {

    if (power < 0) {
      throw new IllegalArgumentException("power of term cannot be less than 0");
    }

    this.coefficient = coefficient;
    this.power = power;
  }

  public Term addTerm(Term that) throws IllegalArgumentException, ArithmeticException {
    if (this.power != that.power) {
      throw new IllegalArgumentException("cannot add terms");
    }

    int coefficientSum = this.coefficient + that.coefficient;
    return new Term(coefficientSum, this.power);
  }

  public double evaluate(double x) {
    return coefficient * (Math.pow(x, power));
  }

  public Term differentiate() throws ArithmeticException {
    int powerOfDerivative = Math.max(0, this.power - 1);
    int coefficientOfDerivative = Math.multiplyExact(this.power, this.coefficient);
    return new Term(coefficientOfDerivative, powerOfDerivative);
  }

  public int getCoefficient() {
    return coefficient;
  }

  public int getPower() {
    return power;
  }
}
