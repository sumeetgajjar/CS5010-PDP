package polynomial;

import java.util.Objects;

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

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }

    if (!(obj instanceof Term)) {
      return false;
    }

    Term that = (Term) obj;
    return this.coefficient == that.coefficient
            && this.power == that.power;
  }

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

  public static Term addTerm(Term term1, Term term2) throws IllegalArgumentException, ArithmeticException {
    if (term1.power != term2.power) {
      throw new IllegalArgumentException("cannot add terms");
    }

    int coefficientSum = Math.addExact(term1.coefficient, term2.coefficient);
    return new Term(coefficientSum, term1.power);
  }
}
