package decoder;

/**
 * This class represents a {@link DecoderImpl}. It extends {@link Decoder} interface. It can be used
 * to decode message with "n" codingSymbols.
 */
public class DecoderImpl implements Decoder {

  /**
   * Constructs a {@link DecoderImpl} with the given codingSymbols. The order of the symbols in the
   * given codingSymbols string is not important and does not affect the decoding process. It throws
   * {@link IllegalArgumentException} if the given codingSymbols satisfies any of the below
   * mentioned conditions.
   * <ul>
   * <li>if codingSymbols string is null</li>
   * <li>if codingSymbols string is empty</li>
   * <li>if codingSymbols string contains duplicate symbols</li>
   * </ul>
   *
   * @param codingSymbols the codingSymbols for this decoder
   * @throws IllegalArgumentException if the given codingSymbols are invalid
   */
  public DecoderImpl(String codingSymbols) throws IllegalArgumentException {

  }

  /**
   * Adds the given symbol and the corresponding code to the coding tree. Throws {@link
   * IllegalStateException}
   * <ul>
   * <li>if the code contains symbols other than coding symbols</li>
   * <li>if the code already exists in the coding tree</li>
   * <li>if the symbol already exists in the coding tree</li>
   * </ul>
   *
   * @param symbol the symbol to be added
   * @param code   the code for the given symbol
   * @throws IllegalStateException if the code contains symbols other than the coding symbols
   */
  @Override
  public void addCode(char symbol, String code) throws IllegalStateException {

  }

  @Override
  public String decode(String encodedMessage) throws IllegalStateException {
    return null;
  }

  @Override
  public String allCodes() {
    return null;
  }

  @Override
  public boolean isCodeComplete() {
    return false;
  }
}
