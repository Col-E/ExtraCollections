package software.coley.collections.delegate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.SortedSet;

/**
 * Sorted Set type that delegates to another implementation.
 *
 * @param <T>
 * 		Set item type.
 *
 * @author Matt Coley
 */
public class DelegatingSortedSet<T> extends DelegatingSet<T> implements SortedSet<T> {
	private final SortedSet<T> delegate;

	/**
	 * @param delegate
	 * 		Delegate set to pass to.
	 */
	public DelegatingSortedSet(@Nonnull SortedSet<T> delegate) {
		super(delegate);
		this.delegate = delegate;
	}

	@Override
	@Nullable
	public Comparator<? super T> comparator() {
		return delegate.comparator();
	}

	@Override
	@Nonnull
	public SortedSet<T> subSet(T fromElement, T toElement) {
		return delegate.subSet(fromElement, toElement);
	}

	@Override
	@Nonnull
	public SortedSet<T> headSet(T toElement) {
		return delegate.headSet(toElement);
	}

	@Override
	@Nonnull
	public SortedSet<T> tailSet(T fromElement) {
		return delegate.tailSet(fromElement);
	}

	@Override
	@Nullable
	public T first() {
		return delegate.first();
	}

	@Override
	@Nullable
	public T last() {
		return delegate.last();
	}
}
