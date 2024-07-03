package software.coley.collections.delegate;

import javax.annotation.Nonnull;
import java.util.Enumeration;

/**
 * Enumeration type that delegates to another implementation.
 *
 * @param <T>
 * 		Enumeration item type.
 *
 * @author Matt Coley
 */
public class DelegatingEnumeration<T> implements Enumeration<T> {
	private final Enumeration<T> delegate;

	/**
	 * @param delegate
	 * 		Delegate enumeration to pass to.
	 */
	public DelegatingEnumeration(@Nonnull Enumeration<T> delegate) {
		this.delegate = delegate;
	}

	@Override
	public boolean hasMoreElements() {
		return delegate.hasMoreElements();
	}

	@Override
	public T nextElement() {
		return delegate.nextElement();
	}
}
