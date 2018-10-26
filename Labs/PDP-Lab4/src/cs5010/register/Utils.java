package cs5010.register;

import java.util.Map;

/**
 * Created by gajjar.s, on 2:37 PM, 10/26/18
 */
public class Utils {

  /**
   * Returns if the given map has equal or not. The maps are said to be equal if both maps contain
   * exactly the same set of keys and same values associated with keys.
   *
   * @param map1 map1
   * @param map2 map2
   * @param <K>  the type of the keys in the map
   * @param <V>  the type of the values in the map
   * @return true if the given maps are equal, false otherwise
   */
  public static <K, V> boolean areMapEqual(Map<K, V> map1, Map<K, V> map2) {

    if (map1.size() != map2.size()) {
      return false;
    }

    for (Map.Entry<K, V> entry : map1.entrySet()) {
      K key = entry.getKey();
      if (map2.containsKey(key)) {
        if (!entry.getValue().equals(map2.get(key))) {
          return false;
        }
      } else {
        return false;
      }
    }
    return true;
  }

}
