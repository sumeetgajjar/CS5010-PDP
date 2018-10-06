import org.junit.Assert;
import org.junit.Test;

import polynomial.Polynomial;
import polynomial.PolynomialImpl;
import util.Utils;

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
  public void testPolynomialStringWithExtraSpaces() {
    Polynomial polynomial = null;
    try {
      polynomial = new PolynomialImpl("     ");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid polynomial string", e.getMessage());
    }
    Assert.assertNull(polynomial);

    try {
      polynomial = new PolynomialImpl("2x^2    3x^3");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid polynomial string", e.getMessage());
    }
    Assert.assertNull(polynomial);

    try {
      polynomial = new PolynomialImpl("2x^2 3x^3    ");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid polynomial string", e.getMessage());
    }
    Assert.assertNull(polynomial);

    try {
      polynomial = new PolynomialImpl("   2x^2 3x^3");
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
      polynomial = new PolynomialImpl("2xy^2 +10");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid polynomial string", e.getMessage());
    }
    Assert.assertNull(polynomial);

    try {
      polynomial = new PolynomialImpl("2x^a +10");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid polynomial string", e.getMessage());
    }
    Assert.assertNull(polynomial);

    try {
      polynomial = new PolynomialImpl("ax^2 +10");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid polynomial string", e.getMessage());
    }
    Assert.assertNull(polynomial);

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
  public void testPolynomialStringWithZeroPowerAndCoefficient() {
    Assert.assertEquals("12", new PolynomialImpl("2x^0 +10").toString());
    Assert.assertEquals("-10", new PolynomialImpl("2x^0 -10").toString());
    Assert.assertEquals("10x^2+2", new PolynomialImpl("2x^0 +10x^2").toString());

    Assert.assertEquals("10", new PolynomialImpl("0x^1 +10").toString());
    Assert.assertEquals("10x^2-10", new PolynomialImpl("0x^0 -10x^0 -0x^6 +10x^2").toString());

    Assert.assertEquals("0", new PolynomialImpl("0x^4 -0x^7 +0 -0x^0 +0x^9").toString());
  }

  @Test
  public void testSamePowerTermsInPolynomialString() {
    Assert.assertEquals("6x^2-10", new PolynomialImpl("2x^2 -10 +4x^2").toString());
    Assert.assertEquals("-6x^2+10", new PolynomialImpl("-2x^2 +10 -4x^2").toString());
  }

  @Test
  public void testPolynomialStringStartingWithSign() {
    Assert.assertEquals("4x^2-10", new PolynomialImpl("+2x^2 -10").toString());
    Assert.assertEquals("-4x^2+10", new PolynomialImpl("-2x^2 +10").toString());
  }

  @Test
  public void testEmptyPolynomialString() {
    Assert.assertEquals("0", new PolynomialImpl("").toString());
    Assert.assertEquals("0", new PolynomialImpl("0").toString());
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
            new PolynomialImpl("-2x^1 -4x^4 -10 -2x^2 -3x^3").toString());
  }

  @Test
  public void testDegreeFivePolynomialInitialization() {
    Assert.assertEquals("5x^5+4x^4+3x^3+2x^2+2x^1+10",
            new PolynomialImpl("4x^4 +10 +2x^2 +2x^1 +3x^3 +5x^5").toString());

    Assert.assertEquals("-5x^5-4x^4-3x^3-2x^2-2x^1-10",
            new PolynomialImpl("-2x^1 -4x^4 -10 -2x^2 -3x^3 -5x^5").toString());
  }

  @Test
  public void testGetDegreeOfPolynomial() {
    String[] polynomialStrings = Utils.getArray(
            "0",
            "2x^1",
            "2x^2 +2x^1",
            "2x^2 -4x^3 +2x^1",
            "-6x^4 +2x^2 -4x^3 +2x^1");

    for (int i = 0; i < polynomialStrings.length; i++) {
      Polynomial polynomial = new PolynomialImpl(polynomialStrings[i]);
      Assert.assertEquals(i, polynomial.getDegree());
    }

    Assert.assertEquals(0, new PolynomialImpl().getDegree());
    Assert.assertEquals(0, new PolynomialImpl("").getDegree());
  }

  @Test
  public void testGetCoefficientOfPolynomial() {
    Polynomial polynomial = new PolynomialImpl("-2x^1 -10 +4x^4 -10 -6x^2 -3x^3 -5x^5");
    Assert.assertEquals("-5x^5+4x^4-4x^3-6x^2-2x^1-10", polynomial.toString());

    Assert.assertEquals(-10, polynomial.getCoefficient(0));
    Assert.assertEquals(-2, polynomial.getCoefficient(1));
    Assert.assertEquals(-6, polynomial.getCoefficient(2));
    Assert.assertEquals(-3, polynomial.getCoefficient(3));
    Assert.assertEquals(4, polynomial.getCoefficient(4));
    Assert.assertEquals(-5, polynomial.getCoefficient(5));

    Assert.assertEquals(5, polynomial.getDegree());

    Assert.assertEquals(0, polynomial.getCoefficient(6));
    Assert.assertEquals(0, polynomial.getCoefficient(100000000));
    Assert.assertEquals(0, polynomial.getCoefficient(-1));
    Assert.assertEquals(0, polynomial.getCoefficient(-100000000));
  }

  @Test
  public void testPolynomialEquality() {
    Polynomial polynomial1 = new PolynomialImpl("1 +2x^2 -1x^1 -3x^3 +4x^4");
    Assert.assertEquals("4x^4-3x^3+2x^2+1", polynomial1.toString());

    Polynomial polynomial2 = new PolynomialImpl("-1x^1 +1 +2x^2 +4x^4 -3x^3 ");
    Assert.assertEquals("4x^4-3x^3+2x^2+1", polynomial2.toString());

    Polynomial polynomial3 = new PolynomialImpl("+2x^2 -1x^1 +1 +4x^4 -3x^3 ");
    Assert.assertEquals("4x^4-3x^3+2x^2+1", polynomial3.toString());

    //Reflexivity
    Assert.assertEquals(polynomial1, polynomial1);

    //Associativity
    Assert.assertEquals(polynomial1, polynomial2);
    Assert.assertEquals(polynomial2, polynomial1);

    //Transitivity
    Assert.assertEquals(polynomial1, polynomial2);
    Assert.assertEquals(polynomial2, polynomial3);
    Assert.assertTrue(polynomial1.equals(polynomial3));
  }

  @Test
  public void testAddingZeroPolynomials() {
    Polynomial polynomial1 = new PolynomialImpl();
    Polynomial polynomial2 = new PolynomialImpl();
    Assert.assertEquals("0", polynomial1.add(polynomial2).toString());
    Assert.assertEquals("0", polynomial2.add(polynomial1).toString());

    Assert.assertEquals("0", polynomial1.toString());
    Assert.assertEquals("0", polynomial2.toString());
  }

  @Test
  public void testAddingPolynomialWithZeroPolynomial() {
    Polynomial polynomial1 = new PolynomialImpl();
    Assert.assertEquals("0", polynomial1.toString());

    Polynomial polynomial2 = new PolynomialImpl("-1 -2x^2 +3x^3 -4x^4");
    String polynomialString2 = "-4x^4+3x^3-2x^2-1";
    Assert.assertEquals(polynomialString2, polynomial2.toString());

    Assert.assertEquals("-4x^4+3x^3-2x^2-1", polynomial1.add(polynomial2).toString());

    Assert.assertEquals("0", polynomial1.toString());
    Assert.assertEquals(polynomialString2, polynomial2.toString());
  }

  @Test
  public void testPolynomialAddition1() {
    Polynomial polynomial1 = new PolynomialImpl("1 +2x^2 -3x^3 +4x^4");
    String polynomial1String = "4x^4-3x^3+2x^2+1";
    Assert.assertEquals(polynomial1String, polynomial1.toString());

    Polynomial polynomial2 = new PolynomialImpl("-1 -2x^2 +3x^3 -4x^4");
    String polynomialString2 = "-4x^4+3x^3-2x^2-1";
    Assert.assertEquals(polynomialString2, polynomial2.toString());

    Assert.assertEquals("0", polynomial1.add(polynomial2).toString());

    Assert.assertEquals(polynomial1String, polynomial1.toString());
    Assert.assertEquals(4, polynomial1.getDegree());
    Assert.assertEquals(4, polynomial2.getDegree());
  }

  @Test
  public void testPolynomialAddition2() {
    Polynomial polynomial1 = new PolynomialImpl("11 +2x^2 -3x^3 +14x^4");
    String polynomial1String = "14x^4-3x^3+2x^2+11";
    Assert.assertEquals(polynomial1String, polynomial1.toString());

    Polynomial polynomial2 = new PolynomialImpl("-1 -2x^2 +3x^3 -4x^4");
    String polynomialString2 = "-4x^4+3x^3-2x^2-1";
    Assert.assertEquals(polynomialString2, polynomial2.toString());

    Assert.assertEquals("10x^4+10", polynomial1.add(polynomial2).toString());

    Assert.assertEquals(polynomial1String, polynomial1.toString());
    Assert.assertEquals(polynomialString2, polynomial2.toString());
  }

  @Test
  public void testPolynomialAddition3() {
    Polynomial polynomial1 = new PolynomialImpl("5x^5 +11 +12x^2 -13x^3 +14x^4");
    String polynomial1String = "14x^4-13x^3+12x^2+11";
    Assert.assertEquals(polynomial1String, polynomial1.toString());

    Polynomial polynomial2 = new PolynomialImpl("-1 -2x^2 +3x^3 -4x^4");
    String polynomialString2 = "-4x^4+3x^3-2x^2-1";
    Assert.assertEquals(polynomialString2, polynomial2.toString());

    Polynomial polynomialAfterAddition = polynomial1.add(polynomial2);
    Assert.assertEquals("5x^5+10x^4-13x^3+10x^2+10", polynomialAfterAddition.toString());
    Assert.assertEquals(5, polynomialAfterAddition.getDegree());

    Assert.assertEquals(polynomial1String, polynomial1.toString());
    Assert.assertEquals(polynomialString2, polynomial2.toString());
  }

  @Test
  public void testMultipleAdditionOnSamePolynomialObject() {
    Polynomial polynomial = new PolynomialImpl("10 +3x^4");
    String polynomialString = "3x^4+10";
    Assert.assertEquals(polynomialString, polynomial.toString());

    Assert.assertEquals("3x^4+2x^2+10",
            polynomial.add(new PolynomialImpl("2x^2")).toString());
    Assert.assertEquals(polynomialString, polynomial.toString());

    Assert.assertEquals("3x^4+2x^2+10",
            polynomial.add(new PolynomialImpl("2x^2")).toString());
    Assert.assertEquals(polynomialString, polynomial.toString());

    Assert.assertEquals("3x^4+2x^2+10",
            polynomial.add(new PolynomialImpl("2x^2")).toString());
    Assert.assertEquals(polynomialString, polynomial.toString());

    Assert.assertEquals("3x^4+2x^2+10",
            polynomial.add(new PolynomialImpl("2x^2")).toString());
    Assert.assertEquals(polynomialString, polynomial.toString());
  }

  @Test
  public void testPolynomialAdditionIsAssociative() {
    Polynomial polynomial1 = new PolynomialImpl("1 +2x^2 -3x^3 +4x^4");
    Assert.assertEquals("4x^4-3x^3+2x^2+1", polynomial1.toString());

    Polynomial polynomial2 = new PolynomialImpl("13x^3 -11 -12x^2 -14x^4 ");
    Assert.assertEquals("-14x^4+13x^3-12x^2-11", polynomial2.toString());

    Assert.assertEquals("-10x^4+10x^3-10x^2-10", polynomial1.add(polynomial2).toString());
    Assert.assertEquals(polynomial1.add(polynomial2), polynomial2.add(polynomial1));
  }

  @Test
  public void testDerivativeOfZeroPolynomial() {
    Polynomial polynomial = new PolynomialImpl();
    String polynomialString = "0";
    Assert.assertEquals(polynomialString, polynomial.toString());

    Polynomial derivative1 = polynomial.derivative();
    Assert.assertEquals("0", derivative1.toString());
    Assert.assertEquals(polynomialString, polynomial.toString());

    Polynomial derivative2 = polynomial.derivative();
    Assert.assertEquals("0", derivative2.toString());
    Assert.assertEquals(polynomialString, polynomial.toString());

    Polynomial derivative3 = polynomial.derivative();
    Assert.assertEquals("0", derivative3.toString());
    Assert.assertEquals(polynomialString, polynomial.toString());
  }

  @Test
  public void testDerivativeOfConstant() {
    Polynomial polynomial = new PolynomialImpl("10");
    String polynomialString = "10";
    Assert.assertEquals(polynomialString, polynomial.toString());

    Polynomial derivative1 = polynomial.derivative();
    Assert.assertEquals("0", derivative1.toString());
    Assert.assertEquals(polynomialString, polynomial.toString());

    Polynomial derivative2 = polynomial.derivative();
    Assert.assertEquals("0", derivative2.toString());
    Assert.assertEquals(polynomialString, polynomial.toString());
  }

  @Test
  public void testDerivativeOfPolynomialWithDegreeOne() {
    Polynomial polynomial = new PolynomialImpl("10 +4x^1");
    String polynomialString = "4x^1+10";
    Assert.assertEquals(polynomialString, polynomial.toString());

    Polynomial derivative = polynomial.derivative();
    Assert.assertEquals("4", derivative.toString());
    Assert.assertEquals(0, derivative.getDegree());

    Assert.assertEquals(polynomialString, polynomial.toString());
  }

  @Test
  public void testDerivativeOfPolynomialWithDegreeTwo() {
    Polynomial polynomial = new PolynomialImpl("10 +4x^1 -5x^2");
    String polynomialString = "-5x^2+4x^1+10";
    Assert.assertEquals(polynomialString, polynomial.toString());

    Polynomial derivative = polynomial.derivative();
    Assert.assertEquals("-10x^1+4", derivative.toString());
    Assert.assertEquals(1, derivative.getDegree());

    Assert.assertEquals(polynomialString, polynomial.toString());
  }

  @Test
  public void testDerivativeOfPolynomialWithDegreeThree() {
    Polynomial polynomial = new PolynomialImpl("10 +4x^1 -5x^2 +10x^3");
    String polynomialString = "10x^3-5x^2+4x^1+10";
    Assert.assertEquals(polynomialString, polynomial.toString());

    Polynomial derivative = polynomial.derivative();
    Assert.assertEquals("30x^2-10x^1+4", derivative.toString());
    Assert.assertEquals(2, derivative.getDegree());

    Assert.assertEquals(polynomialString, polynomial.toString());
  }

  @Test
  public void testDerivativeOfPolynomialWithDegreeFour() {
    Polynomial polynomial = new PolynomialImpl("10 +4x^1 +12x^4 -5x^2 +10x^3");
    String polynomialString = "12x^4+10x^3-5x^2+4x^1+10";
    Assert.assertEquals(polynomialString, polynomial.toString());

    Polynomial derivative = polynomial.derivative();
    Assert.assertEquals("48x^3+30x^2-10x^1+4", derivative.toString());
    Assert.assertEquals(3, derivative.getDegree());

    Assert.assertEquals(polynomialString, polynomial.toString());
  }

  @Test
  public void testDerivativeOfPolynomialWithDegreeFive() {
    Polynomial polynomial = new PolynomialImpl("10 +10x^5");
    String polynomialString = "10x^5+10";
    Assert.assertEquals(polynomialString, polynomial.toString());

    Polynomial derivative = polynomial.derivative();
    Assert.assertEquals("50x^4", derivative.toString());
    Assert.assertEquals(4, derivative.getDegree());

    Assert.assertEquals(polynomialString, polynomial.toString());
  }

  @Test
  public void testDifferentiatingSamePolynomialObject() {
    Polynomial polynomial = new PolynomialImpl("10 +10x^5");
    String polynomialString = "10x^5+10";
    Assert.assertEquals(polynomialString, polynomial.toString());

    Assert.assertEquals("50x^4", polynomial.derivative().toString());
    Assert.assertEquals(polynomialString, polynomial.toString());

    Assert.assertEquals("50x^4", polynomial.derivative().toString());
    Assert.assertEquals(polynomialString, polynomial.toString());

    Assert.assertEquals("50x^4", polynomial.derivative().toString());
    Assert.assertEquals(polynomialString, polynomial.toString());

    Assert.assertEquals("50x^4", polynomial.derivative().toString());
    Assert.assertEquals(polynomialString, polynomial.toString());
  }

  @Test
  public void testEvaluatingZeroPolynomial() {
    Polynomial polynomial = new PolynomialImpl("");
    Assert.assertEquals(0D, polynomial.evaluate(0D), 0D);
    Assert.assertEquals(0D, polynomial.evaluate(100D), 0D);
    Assert.assertEquals(0D, polynomial.evaluate(-100D), 0D);
  }

  @Test
  public void testEvaluatingPolynomialWithPositiveX() {
    Polynomial polynomial = new PolynomialImpl("+1 +2x^2 -3x^3");
    Assert.assertEquals("-3x^3+2x^2+1", polynomial.toString());

    Assert.assertEquals(1D, polynomial.evaluate(0D), 0D);
    Assert.assertEquals(0D, polynomial.evaluate(1D), 0D);
    Assert.assertEquals(6D, polynomial.evaluate(-1D), 0D);

    Assert.assertEquals(-2799D, polynomial.evaluate(10D), 0D);
    Assert.assertEquals(3201D, polynomial.evaluate(-10D), 0D);

    try {
      polynomial.evaluate(Double.MAX_VALUE);
      Assert.fail("should have failed");
    } catch (ArithmeticException e) {
      Assert.assertEquals("overflow occurred while evaluating polynomial", e.getMessage());
    }

    try {
      polynomial.evaluate(Double.MIN_VALUE);
      Assert.fail("should have failed");
    } catch (ArithmeticException e) {
      Assert.assertEquals("overflow occurred while evaluating polynomial", e.getMessage());
    }
  }

  @Test
  public void testAddingTermToPolynomial() {
    Polynomial polynomial = new PolynomialImpl();
    Assert.assertEquals("0", polynomial.toString());

    polynomial.addTerm(10, 0);
    Assert.assertEquals("10", polynomial.toString());

    polynomial.addTerm(1, 1);
    Assert.assertEquals("1x^1+10", polynomial.toString());

    polynomial.addTerm(2, 2);
    Assert.assertEquals("2x^2+1x^1+10", polynomial.toString());

    polynomial.addTerm(3, 3);
    Assert.assertEquals("3x^3+2x^2+1x^1+10", polynomial.toString());

    polynomial.addTerm(-4, 4);
    Assert.assertEquals("-4x^4+3x^3+2x^2+1x^1+10", polynomial.toString());

    polynomial.addTerm(-5, 5);
    Assert.assertEquals("-5x^5-4x^4+3x^3+2x^2+1x^1+10", polynomial.toString());

    polynomial.addTerm(6, 6);
    Assert.assertEquals("6x^6-5x^5-4x^4+3x^3+2x^2+1x^1+10", polynomial.toString());

    polynomial.addTerm(0, 0);
    Assert.assertEquals("6x^6-5x^5-4x^4+3x^3+2x^2+1x^1+10", polynomial.toString());
  }

  @Test
  public void testAddingInvalidTermToPolynomial() {
    Polynomial polynomial = new PolynomialImpl();
    try {
      polynomial.addTerm(10, -1);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("cannot add term with negative power", e.getMessage());
    }
    Assert.assertEquals("0", polynomial.toString());

    try {
      polynomial.addTerm(-10, -1);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("cannot add term with negative power", e.getMessage());
    }
    Assert.assertEquals("0", polynomial.toString());

    try {
      polynomial.addTerm(0, -1);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("cannot add term with negative power", e.getMessage());
    }
    Assert.assertEquals("0", polynomial.toString());
  }

  @Test
  public void testAddingTermsOfSamePowerToPolynomial() {
    Polynomial polynomial = new PolynomialImpl("-2x^1 -10 +4x^4 -10 -6x^2 -3x^3 -5x^5");
    Assert.assertEquals("-5x^5+4x^4-4x^3-6x^2-2x^1-10", polynomial.toString());

    polynomial.addTerm(8, 0);
    Assert.assertEquals("-5x^5+4x^4-4x^3-6x^2-2x^1-2", polynomial.toString());

    polynomial.addTerm(-8, 0);
    Assert.assertEquals("-5x^5+4x^4-4x^3-6x^2-2x^1-10", polynomial.toString());

    polynomial.addTerm(8, 1);
    Assert.assertEquals("-5x^5+4x^4-4x^3-6x^2+6x^1-2", polynomial.toString());

    polynomial.addTerm(-8, 1);
    Assert.assertEquals("-5x^5+4x^4-4x^3-6x^2-2x^1-2", polynomial.toString());

    polynomial.addTerm(8, 2);
    Assert.assertEquals("-5x^5+4x^4-4x^3+2x^2-2x^1-2", polynomial.toString());

    polynomial.addTerm(-8, 2);
    Assert.assertEquals("-5x^5+4x^4-4x^3-6x^2-2x^1-2", polynomial.toString());

    polynomial.addTerm(8, 3);
    Assert.assertEquals("-5x^5+4x^4+12x^3-6x^2-2x^1-2", polynomial.toString());

    polynomial.addTerm(-8, 3);
    Assert.assertEquals("-5x^5+4x^4-4x^3-6x^2-2x^1-2", polynomial.toString());

    polynomial.addTerm(8, 4);
    Assert.assertEquals("-5x^5+12x^4-4x^3-6x^2-2x^1-2", polynomial.toString());

    polynomial.addTerm(-8, 4);
    Assert.assertEquals("-5x^5+4x^4-4x^3-6x^2-2x^1-2", polynomial.toString());

    polynomial.addTerm(8, 5);
    Assert.assertEquals("3x^5+4x^4-4x^3-6x^2-2x^1-2", polynomial.toString());

    polynomial.addTerm(-8, 5);
    Assert.assertEquals("-5x^5+4x^4-4x^3-6x^2-2x^1-2", polynomial.toString());
  }
}
