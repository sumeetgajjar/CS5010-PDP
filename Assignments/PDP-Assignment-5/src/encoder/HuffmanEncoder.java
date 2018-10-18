package encoder;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

import encoder.bean.Pair;
import util.Utils;

/**
 * Created by gajjar.s, on 9:30 PM, 10/17/18
 */
public class HuffmanEncoder implements Encoder {

  @Override
  public Map<Character, String> generateCodingTable(int noOfSymbols, String message) {
    Map<Character, StringBuilder> codingTable = new HashMap<>();
    PriorityQueue<Pair<String, Integer>> priorityQueue = getPriorityQueueForMessage(message);
    while (!priorityQueue.isEmpty()) {
      StringBuilder builder = new StringBuilder();
      int frequencyCount = 0;

      for (int i = 0; i < noOfSymbols && !priorityQueue.isEmpty(); i++) {
        Pair<String, Integer> pair = priorityQueue.poll();
        String symbols = pair.getFirst();
        for (char symbol : symbols.toCharArray()) {
          StringBuilder gx = codingTable.getOrDefault(symbol, new StringBuilder());
          gx.append(i);
          codingTable.put(symbol, gx);
        }

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

  @Override
  public String encode(Map<Character, String> codingTable, String message) throws IllegalStateException {
    StringBuilder builder = new StringBuilder();
    for (char symbol : message.toCharArray()) {
      String code = codingTable.get(symbol);
      if (Objects.isNull(code)) {
        throw new IllegalStateException(String.format("coding symbol not found for symbol:%s", symbol));
      }
      builder.append(code);
    }
    return builder.toString();
  }

  private Map<Character, String> getCharacterStringMap(Map<Character, StringBuilder> codingTable) {
    Map<Character, String> finalCodingTable = new HashMap<>(codingTable.size());
    for (Map.Entry<Character, StringBuilder> entry : codingTable.entrySet()) {
      finalCodingTable.put(entry.getKey(), entry.getValue().reverse().toString());
    }
    return finalCodingTable;
  }

  private PriorityQueue<Pair<String, Integer>> getPriorityQueueForMessage(String message) {
    List<Pair<String, Integer>> frequencyPairs = getFrequencyPair(message);

    PriorityQueue<Pair<String, Integer>> priorityQueue = new PriorityQueue<>(getFrequencyPairComparator());
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

  public static void main(String[] args) {
    Encoder encoder = new HuffmanEncoder();
    String message = "(Taken from wikipedia)\n" +
            "\n" +
            "Grace Brewster Murray Hopper (née Murray; December 9, 1906 – January 1, 1992) was an American computer scientist and United States Navy rear admiral.[1] One of the first programmers of the Harvard Mark I computer, she was a pioneer of computer programming who invented one of the first compiler related tools. She popularized the idea of machine-independent programming languages, which led to the development of COBOL, an early high-level programming language still in use today.\n" +
            "\n" +
            "Hopper attempted to enlist in the Navy during World War II but was rejected because she was 34 years old. She instead joined the Navy Reserves. Hopper began her computing career in 1944 when she worked on the Harvard Mark I team led by Howard H. Aiken. In 1949, she joined the Eckert–Mauchly Computer Corporation and was part of the team that developed the UNIVAC I computer. At Eckert–Mauchly she began developing the compiler. She believed that a programming language based on English was possible. Her compiler converted English terms into machine code understood by computers. By 1952, Hopper had finished her program linker (originally called a compiler), which was written for the A-0 System.";
    Map<Character, String> codingTable = encoder.generateCodingTable(2, message);
    System.out.println(codingTable);
    System.out.println(encoder.encode(codingTable, message));
  }
}
