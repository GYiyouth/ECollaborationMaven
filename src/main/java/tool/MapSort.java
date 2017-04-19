package tool;

import java.util.*;

/**
 * Created by GR on 2017/3/3.
 */
public class MapSort{

    /**
     * map<Integer,Integer>按照value降序
     *
     * @param map
     * @return
     */
    public static <K, V extends Comparable<? super V>> Map<K, V>
    sortMap(Map<K, V> map) {
        List<Map.Entry<K, V>> list =
                new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>() {
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }
        return result;
    }
}
