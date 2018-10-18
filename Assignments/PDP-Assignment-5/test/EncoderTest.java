import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

import encoder.Encoder;
import encoder.HuffmanEncoder;

/**
 * A junit class to test {@link encoder.HuffmanEncoder}
 */
public class EncoderTest {

  @Test
  public void testHuffmanEncoderInitialization() {
    String message = "abcde";

    Encoder<Character> encoder = new HuffmanEncoder();
    Map<Character, String> codingTable = encoder.generateCodingTable(Arrays.asList('0', '1'), message);

    Assert.assertEquals("110", codingTable.get('a'));
    Assert.assertEquals("111", codingTable.get('b'));
    Assert.assertEquals("00", codingTable.get('c'));
    Assert.assertEquals("01", codingTable.get('d'));
    Assert.assertEquals("10", codingTable.get('e'));

    Assert.assertEquals("110111000110", encoder.encode(codingTable, message));
  }

  @Test
  public void testNullOrEmptyMessageForGeneratingCodingTable() {
    Encoder<Character> encoder = new HuffmanEncoder();
    try {
      Map<Character, String> codingTable = encoder.generateCodingTable(Arrays.asList('0', '1'), null);
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid string:'null'", e.getMessage());
    }

    try {
      Map<Character, String> codingTable = encoder.generateCodingTable(Arrays.asList('0', '1'), "");
      Assert.fail("should have failed");
    } catch (IllegalArgumentException e) {
      Assert.assertEquals("Invalid string:''", e.getMessage());
    }
  }

  @Test
  public void testNullOrEmptyMessageForEncoding() {
    Encoder<Character> encoder = new HuffmanEncoder();
    Map<Character, String> codingTable = encoder.generateCodingTable(Arrays.asList('0', '1'), "abcde");
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
}
