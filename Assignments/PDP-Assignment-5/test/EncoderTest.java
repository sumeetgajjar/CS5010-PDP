import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import encoder.Encoder;
import encoder.HuffmanEncoder;
import util.Utils;

/**
 * A junit class to test {@link encoder.HuffmanEncoder}.
 */
public class EncoderTest {

  @Test
  public void testHuffmanEncoderInitialization() {
    String message = "abcde";

    Encoder encoder = new HuffmanEncoder();
    Map<Character, String> codingTable =
            encoder.generateCodingTable(Arrays.asList('0', '1'), message);

    Assert.assertEquals("110", codingTable.get('a'));
    Assert.assertEquals("111", codingTable.get('b'));
    Assert.assertEquals("00", codingTable.get('c'));
    Assert.assertEquals("01", codingTable.get('d'));
    Assert.assertEquals("10", codingTable.get('e'));

    Assert.assertEquals("110111000110", encoder.encode(codingTable, message));
    Assert.assertEquals("110110110110", encoder.encode(codingTable, "aaaa"));
  }

  @Test
  public void testEncodingUsingSpecialSymbols() {
    String message = "abcde";

    Encoder encoder = new HuffmanEncoder();
    Map<Character, String> codingTable =
            encoder.generateCodingTable(Arrays.asList('@', ' '), message);

    Assert.assertEquals("  @", codingTable.get('a'));
    Assert.assertEquals("   ", codingTable.get('b'));
    Assert.assertEquals("@@", codingTable.get('c'));
    Assert.assertEquals("@ ", codingTable.get('d'));
    Assert.assertEquals(" @", codingTable.get('e'));

    Assert.assertEquals("  @   @@@  @", encoder.encode(codingTable, message));
    Assert.assertEquals("  @  @  @  @", encoder.encode(codingTable, "aaaa"));
  }

  @Test
  public void testEncodingMessageSameAsCodingSymbols() {
    String message = "abcde";

    Encoder encoder = new HuffmanEncoder();
    Map<Character, String> codingTable =
            encoder.generateCodingTable(Arrays.asList('a', 'b', 'c', 'd', 'e'), message);

    Assert.assertEquals("a", codingTable.get('a'));
    Assert.assertEquals("b", codingTable.get('b'));
    Assert.assertEquals("c", codingTable.get('c'));
    Assert.assertEquals("d", codingTable.get('d'));
    Assert.assertEquals("e", codingTable.get('e'));

    Assert.assertEquals("abcde", encoder.encode(codingTable, message));
    Assert.assertEquals("aaaa", encoder.encode(codingTable, "aaaa"));
  }

  @Test
  public void testSymbolInMessageDoesNotExistInCodingTable() {
    Encoder encoder = new HuffmanEncoder();
    String message = "abcde";
    Map<Character, String> codingTable =
            encoder.generateCodingTable(Arrays.asList('0', '1'), message);

    try {
      encoder.encode(codingTable, String.format("%s%s", message, "f"));
      Assert.fail("should have failed");
    } catch (IllegalStateException e) {
      Assert.assertEquals("invalid coding symbol for symbol:'f'", e.getMessage());
    }
  }

  @Test
  public void testEmptyCodingSymbolForASymbolInMessage() {
    Encoder encoder = new HuffmanEncoder();
    String message = "abcde";
    Map<Character, String> codingTable = new HashMap<>();
    codingTable.put('a', "");
    codingTable.put('b', "111");

    try {
      encoder.encode(codingTable, message);
      Assert.fail("should have failed");
    } catch (IllegalStateException e) {
      Assert.assertEquals("invalid coding symbol for symbol:'a'", e.getMessage());
    }
  }

  @Test
  public void testCodingSymbolCannotBeNull() {
    Encoder encoder = new HuffmanEncoder();
    try {
      encoder.generateCodingTable(Arrays.asList(null, '1'), "abcde");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("coding symbol cannot be null", e.getMessage());
    }
  }

  @Test
  public void testCodingSymbolSizeLessThanTwo() {
    Encoder encoder = new HuffmanEncoder();
    try {
      encoder.generateCodingTable(Collections.singletonList('1'), "abcde");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("coding symbols cannot be less than 2", e.getMessage());
    }
  }

  @Test
  public void testDuplicateCodingSymbols() {
    Encoder encoder = new HuffmanEncoder();
    try {
      encoder.generateCodingTable(Arrays.asList('1', '1'), "abcde");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("duplicate coding symbols are not allowed", e.getMessage());
    }
  }

  @Test
  public void testNullOrEmptyMessageForGeneratingCodingTable() {
    Encoder encoder = new HuffmanEncoder();
    try {
      encoder.generateCodingTable(Arrays.asList('0', '1'), null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid string:'null'", e.getMessage());
    }

    try {
      encoder.generateCodingTable(Arrays.asList('0', '1'), "");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid string:''", e.getMessage());
    }
  }

  @Test
  public void testNullOrEmptyCodingSymbolsForGeneratingCodingTable() {
    Encoder encoder = new HuffmanEncoder();
    try {
      encoder.generateCodingTable(null, "abcde");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("List cannot be null or empty", e.getMessage());
    }

    try {
      encoder.generateCodingTable(Collections.emptyList(), "abcde");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("List cannot be null or empty", e.getMessage());
    }
  }

  @Test
  public void testNullOrEmptyMessageForEncoding() {
    Encoder encoder = new HuffmanEncoder();
    Map<Character, String> codingTable =
            encoder.generateCodingTable(Arrays.asList('0', '1'), "abcde");
    try {
      encoder.encode(codingTable, null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid string:'null'", e.getMessage());
    }

    try {
      encoder.encode(codingTable, "");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid string:''", e.getMessage());
    }
  }

  @Test
  public void testNullOrEmptyCodingTableForEncoding() {
    Encoder encoder = new HuffmanEncoder();

    try {
      encoder.encode(null, "abcde");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Map cannot be null or empty", e.getMessage());
    }

    try {
      encoder.encode(Collections.emptyMap(), "abcde");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Map cannot be null or empty", e.getMessage());
    }
  }

  @Test
  public void testHuffmanEncoderWithThreeSymbols() {
    String message = "abcd";

    Encoder encoder = new HuffmanEncoder();
    Map<Character, String> codingTable =
            encoder.generateCodingTable(Arrays.asList('0', '1', '2'), message);

    Assert.assertEquals("10", codingTable.get('a'));
    Assert.assertEquals("11", codingTable.get('b'));
    Assert.assertEquals("12", codingTable.get('c'));
    Assert.assertEquals("0", codingTable.get('d'));

    Assert.assertEquals("1011120", encoder.encode(codingTable, message));
    Assert.assertEquals("10101010", encoder.encode(codingTable, "aaaa"));
  }

  @Test
  public void testHuffmanEncoderWithHexSymbols() {
    String message = "Hello world";

    Encoder encoder = new HuffmanEncoder();
    Map<Character, String> codingTable =
            encoder.generateCodingTable(
                    Utils.convertStringToCharacterArray("0123456789abcdef"),
                    message);

    Assert.assertEquals("0", codingTable.get(' '));
    Assert.assertEquals("1", codingTable.get('H'));
    Assert.assertEquals("2", codingTable.get('d'));
    Assert.assertEquals("3", codingTable.get('e'));
    Assert.assertEquals("4", codingTable.get('r'));
    Assert.assertEquals("5", codingTable.get('w'));
    Assert.assertEquals("6", codingTable.get('o'));
    Assert.assertEquals("7", codingTable.get('l'));

    Assert.assertEquals("13776056472", encoder.encode(codingTable, message));
  }
}
