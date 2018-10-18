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
 * Created by gajjar.s, on 9:30 PM, 10/17/18
 */
public class HuffmanEncoder implements Encoder<Character> {

  @Override
  public Map<Character, String> generateCodingTable(List<Character> codingSymbols, String message)
          throws IllegalArgumentException {

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

  private void sanityCheckBeforeGeneratingCodingTable(List<Character> codingSymbols, String message)
          throws IllegalArgumentException {

    Utils.checkNullOrEmptyString(message);
    Utils.checkNullOrEmptyList(codingSymbols);

    if (codingSymbols.size() < 2) {
      throw new IllegalArgumentException("coding symbols cannot be less than 2");
    }

    Set<Character> codingSymbolSet = new HashSet<>(codingSymbols);
    if (codingSymbols.size() != codingSymbolSet.size()) {
      throw new IllegalArgumentException("duplicate coding symbols are not allowed");
    }
  }

  @Override
  public String encode(Map<Character, String> codingTable, String message)
          throws IllegalStateException, IllegalArgumentException {

    Utils.checkNullOrEmptyString(message);
    Utils.checkNullOrEmptyMap(codingTable);

    StringBuilder builder = new StringBuilder();
    for (char symbol : message.toCharArray()) {
      String code = codingTable.get(symbol);
      if (Objects.isNull(code)) {
        throw new IllegalStateException(
                String.format("coding symbol not found for symbol:'%s'", symbol));
      }
      builder.append(code);
    }
    return builder.toString();
  }

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

  private Map<Character, String> getCharacterStringMap(Map<Character, StringBuilder> codingTable) {
    Map<Character, String> finalCodingTable = new HashMap<>(codingTable.size());
    for (Map.Entry<Character, StringBuilder> entry : codingTable.entrySet()) {
      finalCodingTable.put(entry.getKey(), entry.getValue().reverse().toString());
    }
    return Collections.unmodifiableMap(finalCodingTable);
  }

  private PriorityQueue<Pair<String, Integer>> getPriorityQueueForMessage(String message) {
    List<Pair<String, Integer>> frequencyPairs = getFrequencyPair(message);

    PriorityQueue<Pair<String, Integer>> priorityQueue =
            new PriorityQueue<>(getFrequencyPairComparator());

    priorityQueue.addAll(frequencyPairs);

    return priorityQueue;
  }

  private List<Pair<String, Integer>> getFrequencyPair(String message) {
    return Utils.convertStringToCharacterArray(message).stream()
            .map(String::valueOf)
            .collect(Collectors.toMap(character -> character, character -> 1, Integer::sum))
            .entrySet().stream()
            .map(e -> new Pair<>(e.getKey(), e.getValue()))
            .collect(Collectors.toList());
  }

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
