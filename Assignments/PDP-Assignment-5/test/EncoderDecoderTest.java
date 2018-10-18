import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.Map;

import decoder.Decoder;
import decoder.DecoderImpl;
import encoder.Encoder;
import encoder.HuffmanEncoder;
import util.Utils;

/**
 * A Junit class to Interactively Encoder and Decoder.
 */
public class EncoderDecoderTest {

  @Test
  public void testEncoderDecoderInteractivelyWithTwoCodingSymbols() {
    for (String codingSymbols : Arrays.asList("01", "012", "0123", "@ #\n$", "0123456789abcdef")) {
      Encoder encoder = new HuffmanEncoder();
      String originalMessage = "Robots are gonna take over the world, I am just playing my part";
      Map<Character, String> codingTable = encoder.generateCodingTable(
              Utils.convertStringToCharacterArray(codingSymbols), originalMessage);

      String encodedMessage = encoder.encode(codingTable, originalMessage);

      Decoder decoder = new DecoderImpl(codingSymbols);
      for (Map.Entry<Character, String> entry : codingTable.entrySet()) {
        decoder.addCode(entry.getKey(), entry.getValue());
      }

      String decodedMessage = decoder.decode(encodedMessage);
      Assert.assertEquals(originalMessage, decodedMessage);
    }
  }
}
