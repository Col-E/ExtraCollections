package software.coley.collections.delegate;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.Iterator;
import java.util.Set;

/**
 * Set type that delegates to another implementation.
 *
 * @param <T>
 * 		Set item type.
 *
 * @author Matt Coley
 */
public class DelegatingSet<T> implements Set<T> {
	private final Set<T> delegate;

	/**
	 * @param delegate
	 * 		Delegate set to pass to.
	 */
	public DelegatingSet(Set<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	public int size() {
		return delegate.size();
	}

	@Override
	public boolean isEmpty() {
		return delegate.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return delegate.contains(o);
	}

	@Override
	@Nonnull
	public Iterator<T> iterator() {
		return delegate.iterator();
	}

	@Override
	@Nonnull
	public Object[] toArray() {
		return delegate.toArray();
	}

	@Override
	@Nonnull
	@SuppressWarnings("all")
	public <T1> T1[] toArray(@Nonnull T1[] a) {
		// Suppression to remove warning about 'suspicious call to toArray(Object[])'
		return delegate.toArray(a);
	}

	@Override
	public boolean add(T t) {
		return delegate.add(t);
	}

	@Override
	public boolean remove(Object o) {
		return delegate.remove(o);
	}

	@Override
	public boolean containsAll(@Nonnull Collection<?> c) {
		return delegate.containsAll(c);
	}

	@Override
	public boolean addAll(@Nonnull Collection<? extends T> c) {
		return delegate.addAll(c);
	}

	@Override
	public boolean retainAll(@Nonnull Collection<?> c) {
		return delegate.retainAll(c);
	}

	@Override
	public boolean removeAll(@Nonnull Collection<?> c) {
		return delegate.removeAll(c);
	}

	@Override
	public void clear() {
		delegate.clear();
	}

	@Override
	public boolean equals(Object o) {
		return delegate.equals(o);
	}

	@Override
	public int hashCode() {
		return delegate.hashCode();
	}

	@Override
	public String toString() {
		return delegate.toString();
	}
}
