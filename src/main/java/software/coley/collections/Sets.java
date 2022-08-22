package software.coley.collections;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Utility for handling {@link java.util.Set} types.
 * <br>
 * <b>Note:</b> All operations use {@link HashSet} as the implementation type.
 *
 * @author Matt Coley
 */
public class Sets {
	/**
	 * @param src
	 * 		Original set.
	 * @param additional
	 * 		Item to add.
	 * @param <T>
	 * 		Type of content.
	 *
	 * @return New set with additional item.
	 */
	public static <T> Set<T> add(Set<T> src, T additional) {
		Set<T> set = new HashSet<>(src);
		set.add(additional);
		return set;
	}

	/**
	 * @param src1
	 * 		Original set.
	 * @param src2
	 * 		Additional items to add.
	 * @param <T>
	 * 		Type of content.
	 *
	 * @return New set with additional items.
	 */
	public static <T> Set<T> combine(Set<T> src1, Set<T> src2) {
		Set<T> set = new HashSet<>(src1);
		set.addAll(src2);
		return set;
	}

	/**
	 * @param src1
	 * 		Original set.
	 * @param src2
	 * 		Additional set.
	 * @param <T>
	 * 		Type of content.
	 *
	 * @return Set of containing only the items not shared by the two sets.
	 */
	public static <T> Set<T> disjoint(Set<T> src1, Set<T> src2) {
		Set<T> results = new HashSet<>(src1);
		results.removeAll(src2);
		return results;
	}

	/**
	 * @param src1
	 * 		Original set.
	 * @param src2
	 * 		Additional set.
	 * @param <T>
	 * 		Type of content.
	 *
	 * @return Set containing only the items shared by the two sets.
	 */
	public static <T> Set<T> union(Set<T> src1, Set<T> src2) {
		Set<T> results = new HashSet<>(src1);
		results.retainAll(src2);
		return results;
	}

	/**
	 * @param value
	 * 		Set item.
	 * @param <T>
	 * 		Item type.
	 *
	 * @return Set containing single item.
	 */
	public static <T> Set<T> of(T value) {
		Set<T> set = new HashSet<>(1);
		set.add(value);
		return set;
	}

	/**
	 * @param values
	 * 		Set items.
	 * @param <T>
	 * 		Item type.
	 *
	 * @return Set containing the items.
	 */
	public static <T> Set<T> of(T[] values) {
		Set<T> set = new HashSet<>(values.length);
		set.addAll(Arrays.asList(values));
		return set;
	}

	/**
	 * @param values
	 * 		Set items.
	 * @param <T>
	 * 		Item type.
	 *
	 * @return Set containing the items.
	 */
	@SafeVarargs
	public static <T> Set<T> ofVar(T... values) {
		return of(values);
	}
}
