import org.junit.Assert;
import org.junit.Test;

import decoder.Decoder;
import decoder.DecoderImpl;

/**
 * A Junit class to Test {@link decoder.DecoderImpl}.
 */
public class DecoderTest {

  private Decoder getDecoder() {
    Decoder decoder = new DecoderImpl("01");

    decoder.addCode('a', "100");
    decoder.addCode('b', "00");
    decoder.addCode('c', "01");
    decoder.addCode('d', "11");
    decoder.addCode('e', "101");

    return decoder;
  }

  @Test
  public void testInitializationOfDecoder() {
    Decoder decoder = new DecoderImpl("01");

    decoder.addCode('a', "100");
    decoder.addCode('b', "00");
    decoder.addCode('c', "01");
    decoder.addCode('d', "11");
    decoder.addCode('e', "101");

    Assert.assertEquals("ace", decoder.decode("10001101"));

    String expectedAllCodes = "";
    expectedAllCodes += "a:100" + System.lineSeparator();
    expectedAllCodes += "b:00" + System.lineSeparator();
    expectedAllCodes += "c:01" + System.lineSeparator();
    expectedAllCodes += "d:11" + System.lineSeparator();
    expectedAllCodes += "e:101" + System.lineSeparator();

    Assert.assertEquals(expectedAllCodes, decoder.allCodes());

    Assert.assertTrue(decoder.isCodeComplete());
  }

  @Test
  public void testNullInputToConstructor() {
    Decoder decoder = null;
    try {
      decoder = new DecoderImpl(null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid codingSymbols string", e.getMessage());
    }
    Assert.assertNull(decoder);
  }

  @Test
  public void testEmptyInputToConstructor() {
    Decoder decoder = null;
    try {
      decoder = new DecoderImpl("");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid codingSymbols string", e.getMessage());
    }
    Assert.assertNull(decoder);
  }

  @Test
  public void testDuplicateCodingSymbolsInConstructorString() {
    Decoder decoder = null;
    try {
      decoder = new DecoderImpl("11");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid codingSymbols string", e.getMessage());
    }
    Assert.assertNull(decoder);

    try {
      decoder = new DecoderImpl("1ab cd1");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid codingSymbols string", e.getMessage());
    }
    Assert.assertNull(decoder);
  }

  @Test
  public void testAddCodeMethod() {
    String expectedAllCodes = "";
    Decoder decoder = new DecoderImpl("01");

    decoder.addCode('a', "100");
    expectedAllCodes += "a:100";
    Assert.assertEquals(expectedAllCodes, decoder.allCodes());

    decoder.addCode('b', "00");
    expectedAllCodes += System.lineSeparator() + "b:00";
    Assert.assertEquals(expectedAllCodes, decoder.allCodes());

    decoder.addCode('c', "01");
    expectedAllCodes += System.lineSeparator() + "c:01";
    Assert.assertEquals(expectedAllCodes, decoder.allCodes());

    decoder.addCode('d', "11");
    expectedAllCodes += System.lineSeparator() + "d:11";
    Assert.assertEquals(expectedAllCodes, decoder.allCodes());

    decoder.addCode('e', "101");
    expectedAllCodes += System.lineSeparator() + "e:101";
    Assert.assertEquals(expectedAllCodes, decoder.allCodes());

    Assert.assertEquals("ace", decoder.decode("10001101"));
  }

  @Test
  public void testAddingNullCodeForSymbol() {
    try {
      Decoder decoder = new DecoderImpl("01");
      decoder.addCode('a', "100");
      decoder.addCode('b', null);

    } catch (IllegalStateException e) {
      Assert.assertEquals("Invalid coding symbol:'null'", e.getMessage());
    }
  }

  @Test
  public void testAddingEmptyCodeForSymbol() {
    try {
      Decoder decoder = new DecoderImpl("01");
      decoder.addCode('a', "100");
      decoder.addCode('b', "");

    } catch (IllegalStateException e) {
      Assert.assertEquals("Invalid coding symbol:''", e.getMessage());
    }
  }

  @Test
  public void testAddingCodeAlreadyExistingSymbol() {
    try {
      Decoder decoder = new DecoderImpl("01");
      decoder.addCode('a', "100");
      decoder.addCode('a', "100");

    } catch (IllegalStateException e) {
      Assert.assertEquals("code for symbol:'a' already exists", e.getMessage());
    }
  }

  @Test
  public void testAddingSameCodeForTwoSymbols() {
    try {
      Decoder decoder = new DecoderImpl("01");
      decoder.addCode('a', "100");
      decoder.addCode('b', "100");

    } catch (IllegalStateException e) {
      Assert.assertEquals("code:'100' already exists for symbol:'a'", e.getMessage());
    }
  }

  @Test
  public void testAddingIllegalCodingSymbol() {
    try {
      Decoder decoder = new DecoderImpl("01");
      decoder.addCode('a', "100");
      decoder.addCode('b', "102");

    } catch (IllegalStateException e) {
      Assert.assertEquals("Invalid coding symbol:'2'", e.getMessage());
    }
  }

  @Test
  public void testSpecialCharactersAsCodingSymbols() {
    Decoder decoder = new DecoderImpl("!@");
  }

  @Test
  public void testSpaceInCodingSymbols() {
    Decoder decoder = new DecoderImpl("1 0");
  }

  @Test
  public void testOneSymbolDecoder() {
    Decoder decoder = new DecoderImpl("1");
  }

  @Test
  public void testTwoSymbolDecoder() {
    Decoder decoder = new DecoderImpl("01");
  }

  @Test
  public void testThreeSymbolDecoder() {
    Decoder decoder = new DecoderImpl("123");
  }

  @Test
  public void testSixteenSymbolDecoder() {
    Decoder decoder = new DecoderImpl("0123456789abcdef");
  }

  @Test
  public void testTwentySymbolDecoder() {
    Decoder decoder = new DecoderImpl("0123456789abcde !@#$");
  }

  @Test
  public void testChangingOrderOfSymbolDoesNotChangeDecoderOutput() {
    Decoder decoder1 = new DecoderImpl("01");

    decoder1.addCode('a', "100");
    decoder1.addCode('b', "00");
    decoder1.addCode('c', "01");
    decoder1.addCode('d', "11");
    decoder1.addCode('e', "101");

    Decoder decoder2 = new DecoderImpl("10");
    decoder2.addCode('a', "100");
    decoder2.addCode('b', "00");
    decoder2.addCode('c', "01");
    decoder2.addCode('d', "11");
    decoder2.addCode('e', "101");

    Assert.assertEquals(decoder1.allCodes(), decoder2.allCodes());
    Assert.assertEquals(decoder1.isCodeComplete(), decoder2.isCodeComplete());

    Assert.assertEquals("ace", decoder1.decode("10001101"));
    Assert.assertEquals("ace", decoder2.decode("10001101"));
  }
}
