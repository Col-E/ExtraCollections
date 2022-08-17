package software.coley.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility for handling {@link java.util.List} types.
 * <br>
 * <b>Note:</b> All operations use {@link ArrayList} as the implementation type.
 *
 * @author Matt Coley
 */
public class Lists {
	/**
	 * @param src
	 * 		Original list.
	 * @param additional
	 * 		Item to add.
	 * @param <T>
	 * 		Type of content.
	 *
	 * @return New list with additional item.
	 */
	public static <T> List<T> add(List<T> src, T additional) {
		List<T> list = new ArrayList<>(src);
		list.add(additional);
		return distinct(list);
	}

	/**
	 * @param src1
	 * 		Original list.
	 * @param src2
	 * 		Additional items to add.
	 * @param <T>
	 * 		Type of content.
	 *
	 * @return New list with additional items.
	 */
	public static <T> List<T> combine(List<T> src1, List<T> src2) {
		List<T> list = new ArrayList<>(src1);
		list.addAll(src2);
		return list;
	}

	/**
	 * @param src
	 * 		Original list.
	 * @param <T>
	 * 		Type of content.
	 *
	 * @return List with duplicates removed.
	 */
	public static <T> List<T> distinct(List<T> src) {
		List<T> copy = new ArrayList<>();
		for (T t : src) {
			if (copy.contains(t))
				continue;
			copy.add(t);
		}
		return copy;
	}

	/**
	 * @param src1
	 * 		Original list.
	 * @param src2
	 * 		Additional list.
	 * @param <T>
	 * 		Type of content.
	 *
	 * @return New list containing only the items not shared by the two lists.
	 */
	public static <T> List<T> disjoint(List<T> src1, List<T> src2) {
		List<T> results1 = new ArrayList<>(src1);
		List<T> results2 = new ArrayList<>(src2);
		results1.removeAll(src2);
		results2.removeAll(src1);
		return combine(results1, results2);
	}

	/**
	 * @param src1
	 * 		Original list.
	 * @param src2
	 * 		Additional list.
	 * @param <T>
	 * 		Type of content.
	 *
	 * @return List of containing only the items shared by the two lists.
	 */
	public static <T> List<T> union(List<T> src1, List<T> src2) {
		List<T> results = new ArrayList<>(src1);
		results.retainAll(src2);
		return distinct(results);
	}

	/**
	 * @param value
	 * 		List item.
	 * @param <T>
	 * 		Item type.
	 *
	 * @return List containing single item.
	 */
	public static <T> List<T> of(T value) {
		List<T> list = new ArrayList<>(1);
		list.add(value);
		return list;
	}

	/**
	 * @param values
	 * 		List items.
	 * @param <T>
	 * 		Item type.
	 *
	 * @return List containing the items.
	 */
	public static <T> List<T> of(T[] values) {
		List<T> list = new ArrayList<>(values.length);
		list.addAll(Arrays.asList(values));
		return list;
	}
}
