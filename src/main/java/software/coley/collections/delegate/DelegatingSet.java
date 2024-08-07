package software.coley.collections.delegate;

import javax.annotation.Nonnull;
import java.util.Set;

/**
 * Set type that delegates to another implementation.
 *
 * @param <T>
 * 		Set item type.
 *
 * @author Matt Coley
 */
public class DelegatingSet<T> extends DelegatingCollection<T> implements Set<T> {
	/**
	 * @param delegate
	 * 		Delegate set to pass to.
	 */
	public DelegatingSet(@Nonnull Set<T> delegate) {
		super(delegate);
	}
}
