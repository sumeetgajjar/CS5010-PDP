package encoder;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.stream.Collectors;

import encoder.bean.Pair;
import util.Utils;

/**
 * This class represents a HuffmanEncoder. It implements {@link Encoder} interface. This class as
 * the name suggests generates a Coding table for a given message using the Huffman coding
 * Algorithm. This class is also useful for encoding the message given a coding table.
 */
public class HuffmanEncoder implements Encoder {

  /**
   * Given a message and coding Symbols, returns the coding table for each symbol in message. It
   * throws {@link IllegalArgumentException} if any of the following given sanity checks fails.
   * <ul>
   * <li>given message should not be null or empty</li>
   * <li>given coding symbols list should not be  null or empty</li>
   * <li>any coding symbol in the given list should not be null</li>
   * <li>The number of coding symbols should be greater equal to 2</li>
   * <li>Coding symbols in the given list should be unique</li>
   * </ul>
   *
   * <p>Throws {@link ArithmeticException} if the frequency of a character in the given message is
   * greater than {@link Integer#MAX_VALUE}.
   *
   * @param codingSymbols the valid list of coding symbols
   * @param message       the message to generate coding table for
   * @return an unmodifiable coding table containing code for each symbol in message
   * @throws IllegalArgumentException if the given params does not pass any of the sanity checks
   * @throws ArithmeticException      if the frequency of a character in the given message is
   *                                  greater than {@link Integer#MAX_VALUE}
   */
  @Override
  public Map<Character, String> generateCodingTable(List<Character> codingSymbols, String message)
          throws IllegalArgumentException, ArithmeticException {

    sanityCheckBeforeGeneratingCodingTable(codingSymbols, message);

    Map<Character, StringBuilder> codingTable = new HashMap<>();

    PriorityQueue<Pair<String, Integer>> priorityQueue = getPriorityQueueForMessage(message);
    while (!priorityQueue.isEmpty()) {
      StringBuilder builder = new StringBuilder();
      int frequencyCount = 0;

      for (int i = 0; i < codingSymbols.size() && !priorityQueue.isEmpty(); i++) {
        Pair<String, Integer> pair = priorityQueue.poll();
        String symbols = pair.getFirst();

        Character codingSymbol = codingSymbols.get(i);
        codingTable = updateCodingTable(codingTable, codingSymbol, symbols);

        frequencyCount = Math.addExact(frequencyCount, pair.getSecond());
        builder.append(symbols);
      }

      Pair<String, Integer> newPair = new Pair<>(builder.toString(), frequencyCount);
      priorityQueue.add(newPair);

      if (priorityQueue.size() < 2) {
        break;
      }
    }
    return getCharacterStringMap(codingTable);
  }

  /**
   * Given a coding table and a message, returns the encoded form of the message. The encoding is
   * done using the coding table provided. It throws an {@link IllegalStateException} if the given
   * message contains a symbol whose code is not present or if the code is empty in the given
   * codingTable. It throws an {@link IllegalArgumentException} is the given coding is null or empty
   * or if the given message is null or empty.
   *
   * @param codingTable the coding table for the message
   * @param message     the message to encode
   * @return the encoded message
   * @throws IllegalStateException    if the encoding fails for any reason
   * @throws IllegalArgumentException if the given params are invalid
   */
  @Override
  public String encode(Map<Character, String> codingTable, String message)
          throws IllegalStateException, IllegalArgumentException {

    Utils.checkNullOrEmptyString(message);
    Utils.checkNullOrEmptyMap(codingTable);

    StringBuilder builder = new StringBuilder();
    for (char symbol : message.toCharArray()) {
      String code = codingTable.get(symbol);
      if (Objects.isNull(code) || code.isEmpty()) {
        throw new IllegalStateException(
                String.format("invalid coding symbol for symbol:'%s'", symbol));
      }
      builder.append(code);
    }
    return builder.toString();
  }

  /**
   * Performs various sanity checks on the given params. Throws {@link IllegalArgumentException} if
   * any of the sanity checks fails.
   *
   * @param codingSymbols the list of coding symbols to check
   * @param message       the message to check
   * @throws IllegalArgumentException if any of the sanity check fails
   */
  private void sanityCheckBeforeGeneratingCodingTable(List<Character> codingSymbols, String message)
          throws IllegalArgumentException {

    Utils.checkNullOrEmptyString(message);
    Utils.checkNullOrEmptyCollection(codingSymbols);

    if (codingSymbols.size() < 2) {
      throw new IllegalArgumentException("coding symbols cannot be less than 2");
    }

    Set<Character> codingSymbolSet = new HashSet<>(codingSymbols);
    if (codingSymbols.size() != codingSymbolSet.size()) {
      throw new IllegalArgumentException("duplicate coding symbols are not allowed");
    }
  }

  /**
   * Updates the code for the given symbols in the coding table.
   *
   * @param codingTable  the coding table
   * @param codingSymbol the coding symbol to be append
   * @param symbols      the symbols whose code in the coding table needs to be updated
   * @return the updated coding table
   * @throws IllegalArgumentException if the codingSymbol to append is null
   */
  private Map<Character, StringBuilder> updateCodingTable(
          Map<Character, StringBuilder> codingTable,
          Character codingSymbol,
          String symbols) throws IllegalArgumentException {

    if (Objects.isNull(codingSymbol)) {
      throw new IllegalArgumentException("coding symbol cannot be null");
    }

    for (char symbol : symbols.toCharArray()) {
      StringBuilder gx = codingTable.getOrDefault(symbol, new StringBuilder());
      gx.append(codingSymbol);
      codingTable.put(symbol, gx);
    }
    return codingTable;
  }

  /**
   * Converts given coding table of (Character,StringBuilder) to coding table of
   * (Character,String).
   *
   * @param codingTable the coding table to convert
   * @return the coding table of (Character,String)
   */
  private Map<Character, String> getCharacterStringMap(Map<Character, StringBuilder> codingTable) {
    Map<Character, String> finalCodingTable = new HashMap<>(codingTable.size());
    for (Map.Entry<Character, StringBuilder> entry : codingTable.entrySet()) {
      finalCodingTable.put(entry.getKey(), entry.getValue().reverse().toString());
    }
    return Collections.unmodifiableMap(finalCodingTable);
  }

  /**
   * Returns a priority Queue of the individual character of given message. The order of data in the
   * queue depends upon the frequency of the character in the given message. The character will
   * lower frequency will be placed before the character with higher frequency. If the frequency of
   * two characters is same then the character which is lexicographically earlier will be placed
   * first.
   *
   * @param message the message
   * @return a priority Queue of the individual character of given message
   */
  private PriorityQueue<Pair<String, Integer>> getPriorityQueueForMessage(String message) {
    List<Pair<String, Integer>> frequencyPairs = getFrequencyPairs(message);

    PriorityQueue<Pair<String, Integer>> priorityQueue =
            new PriorityQueue<>(getFrequencyPairComparator());

    priorityQueue.addAll(frequencyPairs);

    return priorityQueue;
  }

  /**
   * Returns a list of Pair of character and its corresponding frequency in the given message.
   *
   * @param message the message to generate frequency map
   * @return a list of Pair of character and its corresponding frequency in the given message
   */
  private List<Pair<String, Integer>> getFrequencyPairs(String message) {
    return Utils.convertStringToCharacterArray(message).stream()
            .map(String::valueOf)
            .collect(Collectors.toMap(character -> character, character -> 1, Integer::sum))
            .entrySet().stream()
            .map(e -> new Pair<>(e.getKey(), e.getValue()))
            .collect(Collectors.toList());
  }

  /**
   * Returns a comparator for comparing Pair of String and Integer. Comparison is made on basis of
   * the Integer value in the given pairs. If both the pairs have same integer value then the string
   * in the given pairs are compared lexicographically.
   *
   * @return a comparator for comparing Pair of String and Integer
   */
  private Comparator<Pair<String, Integer>> getFrequencyPairComparator() {
    return (pair1, pair2) -> {
      int frequencyDiff = pair1.getSecond() - pair2.getSecond();
      if (frequencyDiff == 0) {
        return pair1.getFirst().compareTo(pair2.getFirst());
      } else {
        return frequencyDiff;
      }
    };
  }
}
