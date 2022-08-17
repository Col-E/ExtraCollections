package software.coley.collections.delegate;

import javax.annotation.Nonnull;
import java.util.Collection;
import java.util.List;
import java.util.ListIterator;
import java.util.Objects;

/**
 * List type that delegates to another implementation.
 *
 * @param <T>
 * 		List item type.
 *
 * @author Matt Coley
 */
public class DelegatingList<T> extends DelegatingCollection<T> implements List<T> {
	private final List<T> delegate;

	/**
	 * @param delegate
	 * 		Delegate list to pass to.
	 */
	public DelegatingList(List<T> delegate) {
		super(delegate);
		this.delegate = Objects.requireNonNull(delegate, "Cannot delegate to a null list");
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		return delegate.addAll(index, c);
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
}