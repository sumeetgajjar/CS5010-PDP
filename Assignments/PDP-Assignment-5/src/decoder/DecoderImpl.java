package decoder;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * This class represents a {@link DecoderImpl}. It extends {@link Decoder} interface. It can be used
 * to decode message with "n" codingSymbols.
 */
public class DecoderImpl implements Decoder {

  private final Set<Character> validCodingSymbols;

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
    this.checkNullOrEmptyString(codingSymbols);
    this.checkDuplicateCodingSymbols(codingSymbols);

    validCodingSymbols = this.getCodingSymbolsSet(codingSymbols);
  }

  private Set<Character> getCodingSymbolsSet(String codingSymbols) {
    Set<Character> codingSymbolsSet = new HashSet<>();
    for (char codingSymbol : codingSymbols.toCharArray()) {
      codingSymbolsSet.add(codingSymbol);
    }
    return Collections.unmodifiableSet(codingSymbolsSet);
  }

  /**
   * Checks if the given codingSymbols contains duplicate symbols.
   *
   * @param codingSymbols string to check
   * @throws IllegalArgumentException if the given codingSymbols contains duplicate symbols
   */
  private void checkDuplicateCodingSymbols(String codingSymbols) throws IllegalArgumentException {

    long symbolsWithCountGreaterThanOne = codingSymbols.chars().boxed()
            .collect(Collectors.toMap(character -> character, character -> 1, Integer::sum))
            .entrySet().stream()
            .map(Map.Entry::getValue)
            .filter(count -> count > 1)
            .count();

    if (symbolsWithCountGreaterThanOne > 0) {
      throw new IllegalArgumentException("Invalid codingSymbols string");
    }
  }

  /**
   * Checks if the given string is null or empty.
   *
   * @param string string to check
   * @throws IllegalArgumentException if the given string is null or empty
   */
  private void checkNullOrEmptyString(String string) throws IllegalArgumentException {
    if (Objects.isNull(string) || string.length() == 0) {
      throw new IllegalArgumentException(String.format("Invalid string:'%s'", string));
    }
  }

  /**
   * Adds the given symbol and the corresponding code to the coding tree. It Throws {@link
   * IllegalStateException}
   * <ul>
   * <li>if the code contains symbols other than coding symbols</li>
   * <li>if the code already exists in the coding tree</li>
   * <li>if the symbol already exists in the coding tree</li>
   * <li>if the coding tree is complete</li>
   * </ul>
   *
   * <p>It throws an {@link IllegalArgumentException} if the given code is null or empty.
   *
   * @param symbol the symbol to be added
   * @param code   the code for the given symbol
   * @throws IllegalStateException    if the code contains symbols other than the coding symbols
   * @throws IllegalArgumentException if the given code is null or empty
   */
  @Override
  public void addCode(char symbol, String code) throws IllegalStateException, IllegalArgumentException {
    this.checkNullOrEmptyString(code);
    this.checkInvalidSymbolsInCode(code);


  }

  /**
   * Checks if the given code string contains invalid coding symbols.
   *
   * @param code the string to check
   * @throws IllegalStateException if the given code string contains invalid coding symbols
   */
  private void checkInvalidSymbolsInCode(String code) throws IllegalStateException {
    for (char codeSymbol : code.toCharArray()) {
      if (!validCodingSymbols.contains(codeSymbol)) {
        throw new IllegalStateException(String.format("Invalid coding symbol:'%s'", codeSymbol));
      }
    }
  }

  /**
   * Takes a encoded message and returns the decoded message using the coding tree created thus far.
   * It throws {@link IllegalStateException} if
   * <ul>
   * <li>if the given string contains symbols other than the coding symbols</li>
   * <li>if the coding tree is empty and decode() method is invoked</li>
   * <li>if the given string leads to an traversal to a leaf that does not exist</li>
   * </ul>
   *
   * <p>It throws {@link IllegalArgumentException} if the given encodedMessage is null or empty.
   *
   * @param encodedMessage the message to be decoded
   * @return the decoded string
   * @throws IllegalStateException    if the decoding fails due to any reason
   * @throws IllegalArgumentException if the given encodedMessage is null or empty
   */
  @Override
  public String decode(String encodedMessage) throws IllegalStateException, IllegalArgumentException {
    return null;
  }

  /**
   * Returns the codes entered thus far as a string. This string contains each symbol x and its code
   * yyy on a separate line, in the form x:yyy. Returns a empty string if the coding tree is empty.
   * The ordering of the symbols in the final string is according to the order of in which the
   * symbols were added in the coding tree using addCode() method. For e.g. if
   * <code>addCode('b',"110");addCode('a',"001");</code> then <code>allCodes()</code> will return
   * "b:110{line-separator}a:001"
   *
   * @return the codes entered thus far as a string
   */
  @Override
  public String allCodes() {
    return null;
  }

  /**
   * Returns true if the code entered so far is complete, false otherwise. A code is said to be
   * complete if every valid encoded message can be successfully decoded. This condition is
   * fulfilled if the coding tree is full (i.e. every non-leaf node has exactly the same number of
   * children, equal to the number of coding symbols). Returns false in case coding tree is empty.
   *
   * @return true if the code entered so far is complete, false otherwise
   */
  @Override
  public boolean isCodeComplete() {
    return false;
  }
}
