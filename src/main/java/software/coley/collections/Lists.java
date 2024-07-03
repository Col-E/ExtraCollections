package software.coley.collections;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

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
	@Nonnull
	public static <T> List<T> add(@Nonnull List<T> src, @Nullable T additional) {
		if (src.isEmpty()) return Collections.singletonList(additional);

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
	@Nonnull
	public static <T> List<T> combine(@Nonnull List<T> src1, @Nonnull List<T> src2) {
		if (src2.isEmpty()) return src1;
		if (src1.isEmpty()) return Collections.emptyList();

		List<T> list = new ArrayList<>(src1.size() + src2.size());
		list.addAll(src1);
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
	@Nonnull
	public static <T> List<T> distinct(@Nonnull List<T> src) {
		if (src.isEmpty()) return Collections.emptyList();

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
	@Nonnull
	public static <T> List<T> disjoint(@Nonnull List<T> src1, @Nonnull List<T> src2) {
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
	@Nonnull
	public static <T> List<T> union(@Nonnull List<T> src1, @Nonnull List<T> src2) {
		List<T> results = new ArrayList<>(src1);
		results.retainAll(src2);
		return distinct(results);
	}

	/**
	 * @param src
	 * 		Original list.
	 * @param <T>
	 * 		Type of content.
	 *
	 * @return Reverse ordered list.
	 */
	@Nonnull
	public static <T> List<T> reversed(@Nonnull List<T> src) {
		if (src.isEmpty() || src.size() == 1) return src;

		List<T> copy = new ArrayList<>(src.size());
		for (T t : src)
			copy.add(0, t);
		return copy;
	}

	/**
	 * @param value
	 * 		List item.
	 * @param <T>
	 * 		Item type.
	 *
	 * @return List containing single item.
	 */
	@Nonnull
	public static <T> List<T> of(@Nullable T value) {
		return Collections.singletonList(value);
	}

	/**
	 * @param values
	 * 		List items.
	 * @param <T>
	 * 		Item type.
	 *
	 * @return List containing the items.
	 */
	@Nonnull
	public static <T> List<T> of(@Nonnull T[] values) {
		int length = values.length;
		if (length == 0) return Collections.emptyList();
		if (length == 1) return Collections.singletonList(values[0]);

		List<T> list = new ArrayList<>(length);
		list.addAll(Arrays.asList(values));
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
	@SafeVarargs
	public static <T> List<T> ofVar(T... values) {
		return of(values);
	}

	/**
	 * @param list
	 * 		List to insert into.
	 * @param item
	 * 		Item to insert.
	 * @param <T>
	 * 		Item type.
	 *
	 * @return Index to insert the item at to ensure sorted order.
	 * Results are only correct if the list itself is already in sorted order.
	 */
	public static <T extends Comparable<T>> int sortedInsertIndex(@Nonnull List<T> list, @Nonnull T item) {
		return sortedInsertIndex(null, list, item);
	}

	/**
	 * @param comparator
	 * 		Optional comparator for sorting, must be specified if {@code T} is not {@link Comparable}.
	 * @param list
	 * 		List to insert into.
	 * @param item
	 * 		Item to insert.
	 * @param <T>
	 * 		Item type.
	 *
	 * @return Index to insert the item at to ensure sorted order.
	 * Results are only correct if the list itself is already in sorted order.
	 */
	public static <T> int sortedInsertIndex(@Nullable Comparator<T> comparator, @Nonnull List<T> list, @Nonnull T item) {
		if (list.isEmpty()) return 0;
		int i = Collections.binarySearch(list, item, comparator);
		if (i < 0) i = -i - 1; // When not found, invert to get correct index.
		return i;
	}

	/**
	 * @param listA
	 * 		Some list of comparable items. Assumed to be in sorted order.
	 * @param listB
	 * 		Another list of comparable items. Assumed to be in sorted order.
	 * @param <T>
	 * 		Item type.
	 *
	 * @return Comparison of the lists.
	 */
	public static <T extends Comparable<T>> int compare(@Nonnull List<T> listA, @Nonnull List<T> listB) {
		int len = Math.min(listA.size(), listB.size());
		for (int i = 0; i < len; i++) {
			int cmp = listA.get(i).compareTo(listB.get(i));
			if (cmp != 0) return cmp;
		}
		return 0;
	}

	/**
	 * @param comparator
	 * 		Comparator to compare {@code T} items with.
	 * @param listA
	 * 		Some list of comparable items. Assumed to be in sorted order.
	 * @param listB
	 * 		Another list of comparable items. Assumed to be in sorted order.
	 * @param <T>
	 * 		Item type.
	 *
	 * @return Comparison of the lists.
	 */
	public static <T> int compare(@Nonnull Comparator<T> comparator, @Nonnull List<T> listA, @Nonnull List<T> listB) {
		int len = Math.min(listA.size(), listB.size());
		for (int i = 0; i < len; i++) {
			int cmp = comparator.compare(listA.get(i), listB.get(i));
			if (cmp != 0) return cmp;
		}
		return 0;
	}
}
