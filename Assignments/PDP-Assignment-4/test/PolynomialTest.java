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

  @Test
  public void testPolynomialStringWithMissingCoefficientAndPower() {
    Polynomial polynomial = null;
    try {
      polynomial = new PolynomialImpl("2x^2 +");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid polynomial string", e.getMessage());
    }
    Assert.assertNull(polynomial);
  }

  @Test
  public void testPolynomialStringWithNegativePower() {
    Polynomial polynomial = null;
    try {
      polynomial = new PolynomialImpl("2x^-2");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid polynomial string", e.getMessage());
    }
    Assert.assertNull(polynomial);
  }

  @Test
  public void testPolynomialStringWithMissingCoefficient() {
    //todo check the validity of this case on piazza
    Polynomial polynomial = null;
    try {
      polynomial = new PolynomialImpl("x^2");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid polynomial string", e.getMessage());
    }
    Assert.assertNull(polynomial);
  }

  @Test
  public void testPolynomialStringWithMissingPower() {
    Polynomial polynomial = null;
    try {
      polynomial = new PolynomialImpl("2x");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid polynomial string", e.getMessage());
    }
    Assert.assertNull(polynomial);
  }

  @Test
  public void testPolynomialStringWithInvalidLetters() {
    Polynomial polynomial = null;
    try {
      polynomial = new PolynomialImpl("2x^2 +random");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid polynomial string", e.getMessage());
    }
    Assert.assertNull(polynomial);

    try {
      polynomial = new PolynomialImpl("2x^2 +2y^2");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid polynomial string", e.getMessage());
    }
    Assert.assertNull(polynomial);

    try {
      polynomial = new PolynomialImpl("ABC");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid polynomial string", e.getMessage());
    }
    Assert.assertNull(polynomial);
  }

  @Test
  public void testNullPolynomialString() {
    Polynomial polynomial = null;
    try {
      polynomial = new PolynomialImpl(null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid polynomial string", e.getMessage());
    }
    Assert.assertNull(polynomial);
  }

  @Test
  public void testSamePowerTermsInPolynomialString() {
    Assert.assertEquals("6x^2-10", new PolynomialImpl("2x^2 -10 +4x^2").toString());
    Assert.assertEquals("-6x^2+10", new PolynomialImpl("-2x^2 +10 -4x^2").toString());
  }

  @Test
  public void testEmptyPolynomialString() {
    Assert.assertEquals("0", new PolynomialImpl("").toString());
  }

  @Test
  public void testDegreeZeroPolynomialInitialization() {
    Assert.assertEquals("10", new PolynomialImpl("10").toString());
    Assert.assertEquals("-10", new PolynomialImpl("-10").toString());
  }

  @Test
  public void testDegreeOnePolynomialInitialization() {
    Assert.assertEquals("2x^1+10", new PolynomialImpl("10 +2x^1").toString());
    Assert.assertEquals("2x^1-10", new PolynomialImpl("-10 -2x^1").toString());
  }

  @Test
  public void testDegreeTwoPolynomialInitialization() {
    Assert.assertEquals("2x^2+2x^1+10", new PolynomialImpl("+2x^1 10 +2x^2").toString());
    Assert.assertEquals("-2x^2-2x^1-10", new PolynomialImpl("-2x^1 -10 -2x^2").toString());
  }

  @Test
  public void testDegreeThreePolynomialInitialization() {
    Assert.assertEquals("3x^3+2x^2+2x^1+10",
            new PolynomialImpl("10 +2x^2 +2x^1 +3x^3").toString());

    Assert.assertEquals("-3x^3-2x^2-2x^1-10",
            new PolynomialImpl("-10 -2x^1 -2x^2 -3x^3").toString());
  }

  @Test
  public void testDegreeFourPolynomialInitialization() {
    Assert.assertEquals("4x^4+3x^3+2x^2+2x^1+10",
            new PolynomialImpl("4x^4 +10 +2x^2 +2x^1 +3x^3").toString());

    Assert.assertEquals("-4x^4-3x^3-2x^2-2x^1-10",
            new PolynomialImpl("-2x^1 -4x^4-10 -2x^2 -3x^3").toString());
  }

  @Test
  public void testDegreeFivePolynomialInitialization() {
    Assert.assertEquals("5x^5+4x^4+3x^3+2x^2+2x^1+10",
            new PolynomialImpl("4x^4 +10 +2x^2 +2x^1 +3x^3 +5x^5").toString());

    Assert.assertEquals("-5x^5-4x^4-3x^3-2x^2-2x^1-10",
            new PolynomialImpl("-2x^1 -4x^4-10 -2x^2 -3x^3 -5x^5").toString());
  }
}
