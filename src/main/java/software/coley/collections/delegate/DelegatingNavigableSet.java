package software.coley.collections.delegate;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.NavigableSet;

/**
 * Navigable Set type that delegates to another implementation.
 *
 * @param <T>
 * 		Set item type.
 *
 * @author Matt Coley
 */
public class DelegatingNavigableSet<T> extends DelegatingSortedSet<T> implements NavigableSet<T> {
	private final NavigableSet<T> delegate;

	/**
	 * @param delegate
	 * 		Delegate set to pass to.
	 */
	public DelegatingNavigableSet(@Nonnull NavigableSet<T> delegate) {
		super(delegate);
		this.delegate = delegate;
	}

	@Override
	@Nullable
	public T lower(T t) {
		return delegate.lower(t);
	}

	@Override
	@Nullable
	public T floor(T t) {
		return delegate.floor(t);
	}

	@Override
	@Nullable
	public T ceiling(T t) {
		return delegate.ceiling(t);
	}

	@Override
	@Nullable
	public T higher(T t) {
		return delegate.higher(t);
	}

	@Override
	@Nullable
	public T pollFirst() {
		return delegate.pollFirst();
	}

	@Override
	@Nullable
	public T pollLast() {
		return delegate.pollLast();
	}

	@Override
	@Nonnull
	public NavigableSet<T> descendingSet() {
		return delegate.descendingSet();
	}

	@Override
	@Nonnull
	public Iterator<T> descendingIterator() {
		return delegate.descendingIterator();
	}

	@Override
	@Nonnull
	public NavigableSet<T> subSet(T fromElement, boolean fromInclusive, T toElement, boolean toInclusive) {
		return delegate.subSet(fromElement, fromInclusive, toElement, toInclusive);
	}

	@Override
	@Nonnull
	public NavigableSet<T> headSet(T toElement, boolean inclusive) {
		return delegate.headSet(toElement, inclusive);
	}

	@Override
	@Nonnull
	public NavigableSet<T> tailSet(T fromElement, boolean inclusive) {
		return delegate.tailSet(fromElement, inclusive);
	}
}
