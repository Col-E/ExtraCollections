package software.coley.collections.delegate;

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
	public DelegatingNavigableSet(NavigableSet<T> delegate) {
		super(delegate);
		this.delegate = delegate;
	}

	@Override
	public T lower(T t) {
		return delegate.lower(t);
	}

	@Override
	public T floor(T t) {
		return delegate.floor(t);
	}

	@Override
	public T ceiling(T t) {
		return delegate.ceiling(t);
	}

	@Override
	public T higher(T t) {
		return delegate.higher(t);
	}

	@Override
	public T pollFirst() {
		return delegate.pollFirst();
	}

	@Override
	public T pollLast() {
		return delegate.pollLast();
	}

	@Override
	public NavigableSet<T> descendingSet() {
		return delegate.descendingSet();
	}

	@Override
	public Iterator<T> descendingIterator() {
		return delegate.descendingIterator();
	}

	@Override
	public NavigableSet<T> subSet(T fromElement, boolean fromInclusive, T toElement, boolean toInclusive) {
		return delegate.subSet(fromElement, fromInclusive, toElement, toInclusive);
	}

	@Override
	public NavigableSet<T> headSet(T toElement, boolean inclusive) {
		return delegate.headSet(toElement, inclusive);
	}

	@Override
	public NavigableSet<T> tailSet(T fromElement, boolean inclusive) {
		return delegate.tailSet(fromElement, inclusive);
	}
}
