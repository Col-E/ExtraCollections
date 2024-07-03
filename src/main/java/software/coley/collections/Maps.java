package software.coley.collections;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Utility for handling {@link java.util.Map} types.
 * <br>
 * <b>Note:</b> All operations use {@link HashMap} as the implementation type if otherwise not configurable.
 *
 * @author Matt Coley
 */
public class Maps {
	/**
	 * @param resultMapSupplier
	 * 		Result map supplier.
	 * 		Should provide an empty map to store results in.
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
	 * @param <M>
	 * 		Returned map type.
	 *
	 * @return New map with value inserted.
	 */
	@Nonnull
	public static <K, V, M extends Map<K, V>> M add(@Nonnull Supplier<M> resultMapSupplier,
	                                                @Nullable Map<K, V> map, K key, V value) {
		M result = resultMapSupplier.get();
		if (map != null) result.putAll(map);
		result.put(key, value);
		return result;
	}


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
	@Nonnull
	public static <K, V> Map<K, V> add(@Nullable Map<K, V> map, K key, V value) {
		if (map == null) return Collections.singletonMap(key, value);

		Map<K, V> result = new HashMap<>(map);
		result.put(key, value);
		return result;
	}

	/**
	 * @param resultMapSupplier
	 * 		Result map supplier.
	 * 		Should provide an empty map to store results in.
	 * @param src1
	 * 		Original map.
	 * @param src2
	 * 		Additional entries to add.
	 * @param <K>
	 * 		Key type.
	 * @param <V>
	 * 		Value type.
	 * @param <M>
	 * 		Returned map type.
	 *
	 * @return New map with additional entries.
	 */
	@Nonnull
	public static <K, V, M extends Map<K, V>> M combine(@Nonnull Supplier<M> resultMapSupplier,
	                                                    @Nullable Map<K, V> src1, @Nullable Map<K, V> src2) {
		M result = resultMapSupplier.get();
		if (src1 != null) result.putAll(src1);
		if (src2 != null) result.putAll(src2);
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
	@Nonnull
	public static <K, V> Map<K, V> combine(@Nullable Map<K, V> src1, @Nullable Map<K, V> src2) {
		if (src1 == null) {
			if (src2 == null) return Collections.emptyMap();
			return src2;
		}
		Map<K, V> map = new HashMap<>(src1);
		if (src2 != null) map.putAll(src2);
		return map;
	}

	/**
	 * @param resultMapSupplier
	 * 		Result map supplier.
	 * 		Should provide an empty map to store results in.
	 * @param src1
	 * 		Original map.
	 * @param src2
	 * 		Additional map.
	 * @param <K>
	 * 		Key type.
	 * @param <V>
	 * 		Value type.
	 * @param <M>
	 * 		Returned map type.
	 *
	 * @return New map containing only the entries <i>(by keys)</i> not shared by the two maps.
	 */
	@Nonnull
	public static <K, V, M extends Map<K, V>> M distinct(@Nonnull Supplier<M> resultMapSupplier,
	                                                     @Nullable Map<K, V> src1, @Nullable Map<K, V> src2) {
		M map1 = resultMapSupplier.get();
		if (src1 != null) map1.putAll(src1);
		if (src2 != null)
			for (K key : src2.keySet())
				map1.remove(key);
		M map2 = resultMapSupplier.get();
		if (src2 != null) map2.putAll(src2);
		if (src1 != null)
			for (K key : src1.keySet())
				map2.remove(key);
		return combine(resultMapSupplier, map1, map2);
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
	@Nonnull
	public static <K, V> Map<K, V> distinct(@Nullable Map<K, V> src1, @Nullable Map<K, V> src2) {
		Map<K, V> map1;
		Map<K, V> map2;

		if (src1 != null) {
			map1 = new HashMap<>(src1);
			if (src2 != null)
				for (K key : src2.keySet())
					map1.remove(key);
		} else {
			map1 = null;
		}

		if (src2 != null) {
			map2 = new HashMap<>(src2);
			if (src1 != null)
				for (K key : src1.keySet())
					map2.remove(key);
		} else {
			map2 = null;
		}

		return combine(map1, map2);
	}

	/**
	 * @param resultMapSupplier
	 * 		Result map supplier.
	 * 		Should provide an empty map to store results in.
	 * @param src1
	 * 		Original map.
	 * @param src2
	 * 		Additional map.
	 * @param <K>
	 * 		Key type.
	 * @param <V>
	 * 		Value type.
	 * @param <M>
	 * 		Returned map type.
	 *
	 * @return New map containing only the entries <i>(by keys)</i> shared by the two maps.
	 */
	@Nonnull
	public static <K, V, M extends Map<K, V>> M union(@Nonnull Supplier<M> resultMapSupplier,
	                                                  @Nullable Map<K, V> src1, @Nullable Map<K, V> src2) {
		M result = resultMapSupplier.get();
		if (src1 != null)
			result.putAll(src1);
		if (src2 != null)
			for (K key : result.keySet())
				if (!src2.containsKey(key))
					result.remove(key);
		return result;
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
	@Nonnull
	public static <K, V> Map<K, V> union(@Nullable Map<K, V> src1, @Nullable Map<K, V> src2) {
		if (src1 == null || src1.isEmpty() || src2 == null || src2.isEmpty())
			return Collections.emptyMap();

		Map<K, V> result = new HashMap<>(src1);
		for (K key : result.keySet())
			if (!src2.containsKey(key))
				result.remove(key);
		return result;
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
	@Nonnull
	public static <K, V> Map<K, V> of(K key, V value) {
		return Collections.singletonMap(key, value);
	}

	/**
	 * @param map
	 * 		Map input.
	 * @param <K>
	 * 		Key type.
	 * @param <V>
	 * 		Value type.
	 *
	 * @return Naive map inversion.
	 */
	@Nonnull
	public static <K, V> Map<V, K> reverse(@Nullable Map<K, V> map) {
		if (map == null) return Collections.emptyMap();

		Map<V, K> result = new HashMap<>();
		map.forEach((k, v) -> result.put(v, k));
		return result;
	}
}
