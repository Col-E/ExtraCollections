package software.coley.collections.delegate;

import java.util.Objects;
import java.util.Queue;

/**
 * Queue type that delegates to another implementation.
 *
 * @param <T>
 * 		Queue item type.
 *
 * @author Matt Coley
 */
public class DelegatingQueue<T> extends DelegatingCollection<T> implements Queue<T> {
	private final Queue<T> delegate;

	/**
	 * @param delegate
	 * 		Delegate queue to pass to.
	 */
	public DelegatingQueue(Queue<T> delegate) {
		super(delegate);
		this.delegate = Objects.requireNonNull(delegate, "Cannot delegate to a null queue");
	}

	@Override
	public boolean offer(T t) {
		return delegate.offer(t);
	}

	@Override
	public T remove() {
		return delegate.remove();
	}

	@Override
	public T poll() {
		return delegate.poll();
	}

	@Override
	public T element() {
		return delegate.element();
	}

	@Override
	public T peek() {
		return delegate.peek();
	}
}
