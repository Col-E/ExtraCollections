package software.coley.collections;

import java.util.HashMap;
import java.util.Map;

/**
 * Utility for handling {@link java.util.Map} types.
 * <br>
 * <b>Note:</b> All operations use {@link HashMap} as the implementation type.
 *
 * @author Matt Coley
 */
public class Maps {
	/**
	 * @param map
	 * 		Input map.
	 * @param key
	 * 		Key to insert with.
	 * @param value
	 * 		Value to insert.
	 * @param <K>
	 * 		Key type.
	 * @param <V>
	 * 		Value type.
	 *
	 * @return New map with value inserted.
	 */
	public static <K, V> Map<K, V> add(Map<K, V> map, K key, V value) {
		Map<K, V> result = new HashMap<>(map);
		result.put(key, value);
		return result;
	}

	/**
	 * @param src1
	 * 		Original map.
	 * @param src2
	 * 		Additional entries to add.
	 * @param <K>
	 * 		Key type.
	 * @param <V>
	 * 		Value type.
	 *
	 * @return New map with additional entries.
	 */
	public static <K, V> Map<K, V> combine(Map<K, V> src1, Map<K, V> src2) {
		Map<K, V> map = new HashMap<>(src1);
		map.putAll(src1);
		map.putAll(src2);
		return map;
	}

	/**
	 * @param src1
	 * 		Original map.
	 * @param src2
	 * 		Additional map.
	 * @param <K>
	 * 		Key type.
	 * @param <V>
	 * 		Value type.
	 *
	 * @return New map containing only the entries <i>(by keys)</i> not shared by the two maps.
	 */
	public static <K, V> Map<K, V> distinct(Map<K, V> src1, Map<K, V> src2) {
		Map<K, V> map1 = new HashMap<>(src1);
		for (K key : src2.keySet())
			map1.remove(key);
		Map<K, V> map2 = new HashMap<>(src2);
		for (K key : src1.keySet())
			map2.remove(key);
		return combine(map1, map2);
	}

	/**
	 * @param src1
	 * 		Original map.
	 * @param src2
	 * 		Additional map.
	 * @param <K>
	 * 		Key type.
	 * @param <V>
	 * 		Value type.
	 *
	 * @return New map containing only the entries <i>(by keys)</i> shared by the two maps.
	 */
	public static <K, V> Map<K, V> union(Map<K, V> src1, Map<K, V> src2) {
		Map<K, V> map = new HashMap<>(src1);
		for (K key : map.keySet())
			if (!src2.containsKey(key))
				map.remove(key);
		return map;
	}

	/**
	 * @param key
	 * 		Map entry key.
	 * @param value
	 * 		Map entry value.
	 * @param <K>
	 * 		Key type.
	 * @param <V>
	 * 		Value type.
	 *
	 * @return Map of one entry.
	 */
	public static <K, V> Map<K, V> of(K key, V value) {
		Map<K, V> map = new HashMap<>(1);
		map.put(key, value);
		return map;
	}
}
