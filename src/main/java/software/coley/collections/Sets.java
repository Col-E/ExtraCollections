package software.coley.collections;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
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
	public static <T> Set<T> add(@Nonnull Set<T> src, @Nullable T additional) {
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
	public static <T> Set<T> intersection(Set<T> src1, Set<T> src2) {
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

	/**
	 * @param set1
	 * 		Some set.
	 * @param set2
	 * 		Some other set.
	 * @param <T>
	 * 		Item type.
	 *
	 * @return Iterator over all values among both sets.
	 */
	@Nonnull
	public static <T> Iterator<T> iterator(@Nullable Set<T> set1, @Nullable Set<T> set2) {
		if (set1 == null && set2 == null)
			return Collections.emptyIterator();
		else if (set1 == null)
			return set2.iterator();
		else if (set2 == null)
			return set1.iterator();
		return new MergedSetIterator<>(set1, set2);
	}

	/**
	 * Set iterator visiting the unique items of two distinct sets.
	 *
	 * @param <T>
	 * 		Item type.
	 */
	private static class MergedSetIterator<T> implements Iterator<T> {
		private final Iterator<T> leftIt, rightIt;
		private final int[] seen;
		private int i;

		public MergedSetIterator(@Nonnull Set<T> left, @Nonnull Set<T> right) {
			leftIt = left.iterator();
			rightIt = right.iterator();
			seen = new int[left.size() + right.size()];
		}

		@Override
		public boolean hasNext() {
			return leftIt.hasNext() || rightIt.hasNext();
		}

		@Override
		public T next() {
			T next = leftIt.hasNext() ? leftIt.next() : rightIt.next();
			int nextHash = next.hashCode();
			int max = i;
			i++;
			for (int j = 0; j < max; j++) {
				if (seen[j] == nextHash)
					return next();
			}
			seen[i] = nextHash;
			return next;
		}
	}
}
