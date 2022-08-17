package software.coley.collections.delegate;

import javax.annotation.Nonnull;
import java.util.*;

/**
 * List type that delegates to another implementation.
 *
 * @param <T>
 * 		List item type.
 *
 * @author Matt Coley
 */
public class DelegatingList<T> implements List<T> {
	private final List<T> delegate;

	/**
	 * @param delegate
	 * 		Delegate list to pass to.
	 */
	public DelegatingList(List<T> delegate) {
		this.delegate = Objects.requireNonNull(delegate, "Cannot delegate to a null list");
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
	public <E> E[] toArray(@Nonnull E[] a) {
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
	public boolean addAll(int index, @Nonnull Collection<? extends T> c) {
		return delegate.addAll(index, c);
	}

	@Override
	public boolean removeAll(@Nonnull Collection<?> c) {
		return delegate.removeAll(c);
	}

	@Override
	public boolean retainAll(@Nonnull Collection<?> c) {
		return delegate.retainAll(c);
	}

	@Override
	public void clear() {
		delegate.clear();
	}

	@Override
	public T get(int index) {
		return delegate.get(index);
	}

	@Override
	public T set(int index, T element) {
		return delegate.set(index, element);
	}

	@Override
	public void add(int index, T element) {
		delegate.add(index, element);
	}

	@Override
	public T remove(int index) {
		return delegate.remove(index);
	}

	@Override
	public int indexOf(Object o) {
		return delegate.indexOf(o);
	}

	@Override
	public int lastIndexOf(Object o) {
		return delegate.lastIndexOf(o);
	}

	@Override
	@Nonnull
	public ListIterator<T> listIterator() {
		return delegate.listIterator();
	}

	@Override
	@Nonnull
	public ListIterator<T> listIterator(int index) {
		return delegate.listIterator(index);
	}

	@Override
	@Nonnull
	public List<T> subList(int fromIndex, int toIndex) {
		return delegate.subList(fromIndex, toIndex);
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