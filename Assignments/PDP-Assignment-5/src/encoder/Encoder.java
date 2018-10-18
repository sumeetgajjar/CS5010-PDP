package encoder;

import java.util.Map;

/**
 * Created by gajjar.s, on 9:28 PM, 10/17/18
 */
public interface Encoder {

  Map<Character, String> generateCodingTable(int noOfSymbols, String message);

  String encode(Map<Character, String> codingTable, String message) throws IllegalStateException;
}
