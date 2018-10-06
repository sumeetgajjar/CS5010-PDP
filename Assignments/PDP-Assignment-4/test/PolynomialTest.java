import org.junit.Assert;
import org.junit.Test;

import polynomial.Polynomial;
import polynomial.PolynomialImpl;

/**
 * Created by gajjar.s,on 5:46 PM, 10/5/18
 */
public class PolynomialTest {

  @Test
  public void testPolynomialInitialization() {
    Polynomial polynomial = new PolynomialImpl("+4x^1 +2x^5 -3x^2 -10");
    Assert.assertEquals(5, polynomial.getDegree());
    Assert.assertEquals(3, polynomial.getCoefficient(4));
    Assert.assertEquals(-7D, polynomial.evaluate(1D), 0D);
    Assert.assertEquals("2x^5-3x^2+4x^1-10", polynomial.toString());

    String expectedPolynomialStringAfterAddition = "2x^5+3x^4-3x^2+4x^1-10";
    Polynomial polynomialAfterAddition = polynomial.add(new PolynomialImpl("3x^4"));
    Assert.assertEquals(expectedPolynomialStringAfterAddition, polynomialAfterAddition.toString());

    Polynomial expectedDerivative = new PolynomialImpl("10x^4-6x+4");
    Assert.assertEquals(expectedDerivative, polynomial.derivative());

    polynomial.addTerm(3, 4);
    Assert.assertEquals(expectedPolynomialStringAfterAddition, polynomial.toString());
  }

  @Test
  public void testEmptyPolynomial() {
    Polynomial polynomial = new PolynomialImpl();

    Assert.assertEquals(new PolynomialImpl(), polynomial);

    Assert.assertEquals(0, polynomial.getDegree());
    Assert.assertEquals(0, polynomial.getCoefficient(2));
    Assert.assertEquals(0D, polynomial.evaluate(4D), 0D);

    Polynomial actualDerivative = polynomial.derivative();
    Assert.assertEquals("0", actualDerivative.toString());

    Polynomial sumOfPolynomials = polynomial.add(new PolynomialImpl("2x^2"));
    Assert.assertEquals("2x^2", sumOfPolynomials.toString());

    polynomial.addTerm(2, 4);
    Assert.assertEquals("2x^4", polynomial.toString());
  }
}
