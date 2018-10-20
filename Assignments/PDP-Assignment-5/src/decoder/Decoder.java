package decoder;

/**
 * This class represents a Decoder and all operations that can be performed on a Decoder.
 */
public interface Decoder {

  /**
   * Adds the given symbol and the corresponding code to the coding tree.
   *
   * @param symbol the symbol to be added
   * @param code   the code for the given symbol
   * @throws IllegalStateException if the code contains symbols other than the coding symbols
   */
  void addCode(char symbol, String code) throws IllegalStateException;

  /**
   * Takes a encoded message and returns the decoded message using the coding tree created thus
   * far.
   *
   * @param encodedMessage the message to be decoded
   * @return the decoded message
   * @throws IllegalStateException if the decoding fails due to any reason
   */
  String decode(String encodedMessage) throws IllegalStateException;

  /**
   * Returns the codes entered thus far as a string. This string contains each symbol x and its code
   * yyy on a separate line, in the form x:yyy.
   *
   * @return the codes entered thus far as a string
   */
  String allCodes();

  /**
   * Returns true if the code entered so far is complete, false otherwise. A code is said to be
   * complete if every valid encoded message can be successfully decoded. This condition is
   * fulfilled if the coding tree is full (i.e. every non-leaf node has exactly the same number of
   * children, equal to the number of coding symbols).
   *
   * @return true if the code entered so far is complete, false otherwise
   */
  boolean isCodeComplete();
}
