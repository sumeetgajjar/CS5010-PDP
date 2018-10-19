import org.junit.Assert;
import org.junit.Test;

import decoder.Decoder;
import decoder.DecoderImpl;

/**
 * A Junit class to Test {@link decoder.DecoderImpl}.
 */
public class DecoderTest {

  //todo test prefix codes with same prefix

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
    Assert.assertEquals("bad", decoder.decode("0010011"));
    Assert.assertEquals("dad", decoder.decode("1110011"));

    String expectedAllCodes = "";
    expectedAllCodes += "a:100" + System.lineSeparator();
    expectedAllCodes += "b:00" + System.lineSeparator();
    expectedAllCodes += "c:01" + System.lineSeparator();
    expectedAllCodes += "d:11" + System.lineSeparator();
    expectedAllCodes += "e:101";

    Assert.assertEquals(expectedAllCodes, decoder.allCodes());

    Assert.assertTrue(decoder.isCodeComplete());
  }

  @Test
  public void testDecoderWithEmptyCodingTree() {
    Decoder decoder = new DecoderImpl("01");
    Assert.assertFalse(decoder.isCodeComplete());
    Assert.assertEquals("", decoder.allCodes());

    try {
      decoder.decode("10001101");
      Assert.fail("should have failed");
    } catch (IllegalStateException e) {
      Assert.assertEquals("cannot decode given sequence", e.getMessage());
    }

    decoder.addCode('a', "100");
    Assert.assertEquals("aaa", decoder.decode("100100100"));
  }

  @Test
  public void testNullInputToConstructor() {
    Decoder decoder = null;
    try {
      decoder = new DecoderImpl(null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid string:'null'", e.getMessage());
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
      Assert.assertEquals("Invalid string:''", e.getMessage());
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
  public void testAddCodeMethodWithAllCodesMethod() {
    Decoder decoder = new DecoderImpl("01");

    String expectedAllCodes = "";
    Assert.assertEquals(expectedAllCodes, decoder.allCodes());

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
  public void testAddCodeMethodWithDecodeMethod() {
    Decoder decoder = new DecoderImpl("01");

    decoder.addCode('a', "100");
    Assert.assertEquals("aaa", decoder.decode("100100100"));

    decoder.addCode('b', "00");
    Assert.assertEquals("aba", decoder.decode("10000100"));

    decoder.addCode('c', "01");
    Assert.assertEquals("abc", decoder.decode("1000001"));

    decoder.addCode('d', "11");
    Assert.assertEquals("abcd", decoder.decode("100000111"));

    decoder.addCode('e', "101");
    Assert.assertEquals("eabcd", decoder.decode("101100000111"));

    Assert.assertEquals("ace", decoder.decode("10001101"));
  }

  @Test
  public void testAddingNullCodeForSymbol() {
    try {
      Decoder decoder = new DecoderImpl("01");
      decoder.addCode('a', "100");
      decoder.addCode('b', null);

      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid string:'null'", e.getMessage());
    }
  }

  @Test
  public void testAddingEmptyCodeForSymbol() {
    try {
      Decoder decoder = new DecoderImpl("01");
      decoder.addCode('a', "100");
      decoder.addCode('b', "");

      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid string:''", e.getMessage());
    }
  }

  @Test
  public void testAddingCodeAlreadyExistingSymbol() {
    try {
      Decoder decoder = new DecoderImpl("01");
      decoder.addCode('a', "100");
      decoder.addCode('a', "101");

      Assert.fail("should have failed");
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

      Assert.fail("should have failed");
    } catch (IllegalStateException e) {
      Assert.assertEquals("data already exists at given path", e.getMessage());
    }
  }

  @Test
  public void testAddingSymbolToCompleteCodingTree() {
    try {
      Decoder decoder = new DecoderImpl("01");
      decoder.addCode('a', "0");
      decoder.addCode('b', "1");

      Assert.assertTrue(decoder.isCodeComplete());

      decoder.addCode('c', "01");

      Assert.fail("should have failed");
    } catch (IllegalStateException e) {
      Assert.assertEquals("children cannot be added to leafNode", e.getMessage());
    }
  }

  @Test
  public void testAddingCodeWhichDoesNotSatisfyPrefixCodingCriteria() {
    try {
      Decoder decoder = new DecoderImpl("01");
      decoder.addCode('a', "10");
      decoder.addCode('b', "1");

      Assert.fail("should have failed");
    } catch (IllegalStateException e) {
      Assert.assertEquals("data already exists at given path", e.getMessage());
    }

    try {
      Decoder decoder = new DecoderImpl("01");
      decoder.addCode('b', "1");
      decoder.addCode('a', "10");

      Assert.fail("should have failed");
    } catch (IllegalStateException e) {
      Assert.assertEquals("children cannot be added to leafNode", e.getMessage());
    }
  }

  @Test
  public void testAddingIllegalCodingSymbol() {
    try {
      Decoder decoder = new DecoderImpl("01");
      decoder.addCode('a', "100");
      decoder.addCode('b', "102");

      Assert.fail("should have failed");
    } catch (IllegalStateException e) {
      Assert.assertEquals("Invalid coding symbol:'2'", e.getMessage());
    }
  }

  @Test
  public void testDecodingInvalidString() {
    Decoder decoder = getDecoder();
    try {
      decoder.decode("10001102");
      Assert.fail("should have failed");
    } catch (IllegalStateException e) {
      Assert.assertEquals("cannot decode given sequence", e.getMessage());
    }

    try {
      decoder.decode("2345");
      Assert.fail("should have failed");
    } catch (IllegalStateException e) {
      Assert.assertEquals("cannot decode given sequence", e.getMessage());
    }

    Assert.assertEquals("a", decoder.decode("100"));
  }

  @Test
  public void testDecodingOnEmptyCodingTree() {
    Decoder decoder = new DecoderImpl("01");
    try {
      decoder.decode("100");
      Assert.fail("should have failed");

    } catch (IllegalStateException e) {
      Assert.assertEquals("cannot decode given sequence", e.getMessage());
    }

    decoder.addCode('a', "100");
    Assert.assertEquals("a", decoder.decode("100"));
  }

  @Test
  public void testDecodingInvalidEncodedString() {
    Decoder decoder = new DecoderImpl("01");
    decoder.addCode('a', "100");

    try {
      decoder.decode("1001");
    } catch (IllegalStateException e) {
      Assert.assertEquals("cannot decode given sequence", e.getMessage());
    }

    try {
      decoder.decode("10010");
    } catch (IllegalStateException e) {
      Assert.assertEquals("cannot decode given sequence", e.getMessage());
    }

    try {
      decoder.decode("100101");
    } catch (IllegalStateException e) {
      Assert.assertEquals("cannot decode given sequence", e.getMessage());
    }

    try {
      decoder.decode("1101");
    } catch (IllegalStateException e) {
      Assert.assertEquals("cannot decode given sequence", e.getMessage());
    }

    try {
      decoder.decode("1001101");
    } catch (IllegalStateException e) {
      Assert.assertEquals("cannot decode given sequence", e.getMessage());
    }

    decoder.addCode('b', "1101");
    Assert.assertEquals("ab", decoder.decode("1001101"));
  }

  @Test
  public void testDecodingEmptyString() {
    try {
      Decoder decoder = getDecoder();
      decoder.decode("");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid string:''", e.getMessage());
    }
  }

  @Test
  public void testDecodingNull() {
    try {
      Decoder decoder = getDecoder();
      decoder.decode(null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid string:'null'", e.getMessage());
    }
  }

  @Test
  public void testSpecialCharactersAsCodingSymbols() {
    Decoder decoder = new DecoderImpl("@#");

    decoder.addCode('a', "#@@");
    decoder.addCode('b', "@@");
    decoder.addCode('c', "@#");
    decoder.addCode('d', "##");
    decoder.addCode('e', "#@#");

    Assert.assertEquals("ace", decoder.decode("#@@@##@#"));
  }

  @Test
  public void testSpaceInCodingSymbols() {
    Decoder decoder = new DecoderImpl("1 ");

    decoder.addCode('a', " 11");
    decoder.addCode('b', "11");
    decoder.addCode('c', "1 ");
    decoder.addCode('d', "  ");
    decoder.addCode('e', " 1 ");

    Assert.assertEquals("ace", decoder.decode(" 111  1 "));
  }

  @Test
  public void testOneSymbolDecoder() {
    Decoder decoder = new DecoderImpl("1");

    decoder.addCode('a', "1");
    Assert.assertTrue(decoder.isCodeComplete());

    Assert.assertEquals("a:1", decoder.allCodes());

    Assert.assertEquals("aaaaaaaaaa", decoder.decode("1111111111"));

    try {
      decoder.decode("11110000");
      Assert.fail("should have failed");
    } catch (IllegalStateException e) {
      Assert.assertEquals("cannot decode given sequence", e.getMessage());
    }
  }

  @Test
  public void testThreeSymbolDecoder() {
    Decoder decoder = new DecoderImpl("123");

    decoder.addCode('a', "12");
    decoder.addCode('b', "22");
    decoder.addCode('c', "21");
    decoder.addCode('d', "231");
    decoder.addCode('e', "31");

    Assert.assertEquals("aced", decoder.decode("122131231"));
  }

  @Test
  public void testSixteenSymbolDecoder() {
    Decoder decoder = new DecoderImpl("0123456789abcdef");

    decoder.addCode('a', "b");
    decoder.addCode('c', "e");
    decoder.addCode('e', "d");
    decoder.addCode('d', "fedcba9876543210");

    Assert.assertEquals("aced", decoder.decode("bedfedcba9876543210"));
  }

  @Test
  public void testTwentySymbolDecoder() {
    Decoder decoder = new DecoderImpl("0123456789abcdef !@#$");

    decoder.addCode('a', "b");
    decoder.addCode('c', "e");
    decoder.addCode('e', "d");
    decoder.addCode('d', "$#@! fedcba9876543210");

    Assert.assertEquals("aced", decoder.decode("bed$#@! fedcba9876543210"));
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

  @Test
  public void testChangingOrderOfAddCodeDoesNotChangeDecoderOutput() {
    Decoder decoder1 = new DecoderImpl("01");

    decoder1.addCode('e', "101");
    decoder1.addCode('d', "11");
    decoder1.addCode('c', "01");
    decoder1.addCode('b', "00");
    decoder1.addCode('a', "100");

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

  @Test
  public void testOrderingOfAllCodesMethod() {
    Decoder decoder = new DecoderImpl("01");
    decoder.addCode('b', "110");
    decoder.addCode('a', "001");

    String expectedAllCodesString = String.format("a:001%sb:110", System.lineSeparator());
    Assert.assertEquals(expectedAllCodesString, decoder.allCodes());

    decoder = new DecoderImpl("01");
    decoder.addCode('a', "001");
    decoder.addCode('b', "110");

    Assert.assertEquals(expectedAllCodesString, decoder.allCodes());
  }

  @Test
  public void testIsCodeComplete() {
    Decoder decoder = new DecoderImpl("01");
    Assert.assertFalse(decoder.isCodeComplete());

    decoder.addCode('b', "00");
    Assert.assertFalse(decoder.isCodeComplete());

    decoder.addCode('c', "01");
    Assert.assertFalse(decoder.isCodeComplete());

    decoder.addCode('d', "11");
    Assert.assertFalse(decoder.isCodeComplete());

    decoder.addCode('a', "100");
    Assert.assertFalse(decoder.isCodeComplete());

    decoder.addCode('e', "101");
    Assert.assertTrue(decoder.isCodeComplete());
  }

  @Test
  public void testDecodingCaseSensitiveSymbols() {
    Decoder decoder = new DecoderImpl("01");

    decoder.addCode('a', "100");
    decoder.addCode('B', "00");
    decoder.addCode('c', "01");
    decoder.addCode('D', "11");
    decoder.addCode('e', "101");

    Assert.assertEquals("ace", decoder.decode("10001101"));
    Assert.assertEquals("BaD", decoder.decode("0010011"));
    Assert.assertEquals("DaD", decoder.decode("1110011"));

    String expectedAllCodes = "";
    expectedAllCodes += "B:00" + System.lineSeparator();
    expectedAllCodes += "D:11" + System.lineSeparator();
    expectedAllCodes += "a:100" + System.lineSeparator();
    expectedAllCodes += "c:01" + System.lineSeparator();
    expectedAllCodes += "e:101";

    Assert.assertEquals(expectedAllCodes, decoder.allCodes());

    Assert.assertTrue(decoder.isCodeComplete());
  }
}
