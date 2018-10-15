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

    Assert.assertEquals("ace", decoder.decode("10001101 "));

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
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertNull(decoder);
  }

  @Test
  public void testEmptyInputToConstructor() {
    Decoder decoder = null;
    try {
      decoder = new DecoderImpl("");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertNull(decoder);
  }

  @Test
  public void testDuplicateCodingSymbolsInConstructorString() {
    Decoder decoder = null;
    try {
      decoder = new DecoderImpl("11");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertNull(decoder);

    try {
      decoder = new DecoderImpl("1ab cd1");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException ignored) {
    }
    Assert.assertNull(decoder);
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

    Assert.assertEquals("ace", decoder1.decode("10001101 "));
    Assert.assertEquals("ace", decoder2.decode("10001101 "));
  }
}
