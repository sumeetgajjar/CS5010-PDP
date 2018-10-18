package encoder;

import java.util.List;
import java.util.Map;

/**
 * Created by gajjar.s, on 9:28 PM, 10/17/18
 */
public interface Encoder<P> {

  Map<P, String> generateCodingTable(List<P> codingSymbols, String message);

  String encode(Map<P, String> codingTable, String message) throws IllegalStateException;
}
