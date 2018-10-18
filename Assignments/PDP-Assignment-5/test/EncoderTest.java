import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

import encoder.Encoder;
import encoder.HuffmanEncoder;

/**
 * A junit class to test {@link encoder.HuffmanEncoder}.
 */
public class EncoderTest {

  @Test
  public void testHuffmanEncoderInitialization() {
    String message = "abcde";

    Encoder<Character> encoder = new HuffmanEncoder();
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
  public void testSymbolInMessageDoesNotExistInCodingTable() {
    Encoder<Character> encoder = new HuffmanEncoder();
    String message = "abcde";
    Map<Character, String> codingTable =
            encoder.generateCodingTable(Arrays.asList('0', '1'), message);

    try {
      encoder.encode(codingTable, String.format("%s%s", message, "f"));
      Assert.fail("should have failed");
    } catch (IllegalStateException e) {
      Assert.assertEquals("coding symbol not found for symbol:'f'", e.getMessage());
    }
  }

  @Test
  public void testCodingSymbolCannotBeNull() {
    Encoder<Character> encoder = new HuffmanEncoder();
    try {
      encoder.generateCodingTable(Arrays.asList(null, '1'), "abcde");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("coding symbol cannot be null", e.getMessage());
    }
  }

  @Test
  public void testNullOrEmptyMessageForGeneratingCodingTable() {
    Encoder<Character> encoder = new HuffmanEncoder();
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
    Encoder<Character> encoder = new HuffmanEncoder();
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
    Encoder<Character> encoder = new HuffmanEncoder();
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
    Encoder<Character> encoder = new HuffmanEncoder();

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
}
