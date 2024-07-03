package software.coley.collections.delegate;

import javax.annotation.Nonnull;
import java.util.Iterator;
import java.util.Objects;

/**
 * Iterable type that delegates to another implementation.
 *
 * @param <T>
 * 		Iterable item type.
 *
 * @author Matt Coley
 */
public class DelegatingIterable<T> implements Iterable<T> {
	private final Iterable<T> delegate;

	/**
	 * @param delegate
	 * 		Delegate iterable to pass to.
	 */
	public DelegatingIterable(@Nonnull Iterable<T> delegate) {
		this.delegate = Objects.requireNonNull(delegate, "Cannot delegate to a null iterable");
	}

	@Override
	@Nonnull
	public Iterator<T> iterator() {
		return delegate.iterator();
	}
}
