package encoder;

import java.util.List;
import java.util.Map;

/**
 * This interface represents a Encoder and all operations that can be performed on an Encoder.
 */
public interface Encoder {

  /**
   * Given a message and coding Symbols, returns the coding table for each symbol in message.
   *
   * @param codingSymbols the valid list of coding symbols
   * @param message       the message to generate coding table for
   * @return the coding table containing code for each symbol in message
   */
  Map<Character, String> generateCodingTable(List<Character> codingSymbols, String message);

  /**
   * Given a coding table and a message, returns the encoded form of the message. The encoding is
   * done using the coding table provided. If encoding fails due any given reason then the encoder
   * throws an {@link IllegalStateException}.
   *
   * @param codingTable the coding table for the message
   * @param message     the message to encode
   * @return the encoded form of the message
   * @throws IllegalStateException if the encoding fails for any reason
   */
  String encode(Map<Character, String> codingTable, String message) throws IllegalStateException;
}
