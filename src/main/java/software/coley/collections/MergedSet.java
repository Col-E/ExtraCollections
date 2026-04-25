package software.coley.collections;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Set implementation that merges two sets together.
 *
 * @param <T>
 * 		Element type.
 *
 * @author Matt Coley
 */
public class MergedSet<T> implements Set<T> {
	private final Set<T> left;
	private final Set<T> right;

	/**
	 * @param left
	 * 		One set to merge.
	 * @param right
	 * 		Another set to merge.
	 */
	public MergedSet(@Nonnull Set<T> left, @Nonnull Set<T> right) {
		this.left = left;
		this.right = right;
	}

	@Override
	public int size() {
		return left.size() + right.size();
	}

	@Override
	public boolean isEmpty() {
		return left.isEmpty() && right.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return left.contains(o) || right.contains(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		for (Object o : c)
			if (!contains(o))
				return false;
		return true;
	}

	@Override
	@Nonnull
	public Iterator<T> iterator() {
		return Sets.iterator(left, right);
	}

	@Override
	@Nonnull
	public Object[] toArray() {
		Object[] leftArr = left.toArray();
		Object[] rightArr = right.toArray();
		Object[] mergedArr = new Object[leftArr.length + rightArr.length];
		System.arraycopy(leftArr, 0, mergedArr, 0, leftArr.length);
		System.arraycopy(rightArr, 0, mergedArr, leftArr.length, rightArr.length);
		return mergedArr;
	}

	@Override
	@Nonnull
	@SuppressWarnings("unchecked")
	public <T1> T1[] toArray(@Nonnull T1[] unused) {
		return (T1[]) toArray();
	}

	@Override
	public boolean add(T t) {
		if (contains(t))
			return false;

		// Add to the smaller set to keep them balanced.
		if (left.size() > right.size())
			return right.add(t);
		else
			return left.add(t);
	}

	@Override
	public boolean remove(Object o) {
		return left.remove(o) || right.remove(o);
	}

	@Override
	public boolean addAll(@Nonnull Collection<? extends T> c) {
		boolean modified = false;
		for (T value : c)
			modified |= add(value);
		return modified;
	}

	@Override
	public boolean retainAll(@Nonnull Collection<?> c) {
		return left.retainAll(c) | right.retainAll(c);
	}

	@Override
	public boolean removeAll(@Nonnull Collection<?> c) {
		return left.removeAll(c) | right.removeAll(c);
	}

	@Override
	public void clear() {
		left.clear();
		right.clear();
	}
}
