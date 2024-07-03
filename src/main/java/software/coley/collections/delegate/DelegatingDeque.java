package software.coley.collections.delegate;

import javax.annotation.Nonnull;
import java.util.Deque;
import java.util.Iterator;
import java.util.Objects;

/**
 * Deque type that delegates to another implementation.
 *
 * @param <T>
 * 		Deque item type.
 *
 * @author Matt Coley
 */
public class DelegatingDeque<T> extends DelegatingQueue<T> implements Deque<T> {
	private final Deque<T> delegate;

	/**
	 * @param delegate
	 * 		Delegate deque to pass to.
	 */
	public DelegatingDeque(@Nonnull Deque<T> delegate) {
		super(delegate);
		this.delegate = Objects.requireNonNull(delegate, "Cannot delegate to a null deque");
	}

	@Override
	public void addFirst(T t) {
		delegate.addFirst(t);
	}

	@Override
	public void addLast(T t) {
		delegate.addLast(t);
	}

	@Override
	public boolean offerFirst(T t) {
		return delegate.offerFirst(t);
	}

	@Override
	public boolean offerLast(T t) {
		return delegate.offerLast(t);
	}

	@Override
	public T removeFirst() {
		return delegate.removeFirst();
	}

	@Override
	public T removeLast() {
		return delegate.removeLast();
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
	public T getFirst() {
		return delegate.getFirst();
	}

	@Override
	public T getLast() {
		return delegate.getLast();
	}

	@Override
	public T peekFirst() {
		return delegate.peekFirst();
	}

	@Override
	public T peekLast() {
		return delegate.peekLast();
	}

	@Override
	public boolean removeFirstOccurrence(Object o) {
		return delegate.removeFirstOccurrence(o);
	}

	@Override
	public boolean removeLastOccurrence(Object o) {
		return delegate.removeLastOccurrence(o);
	}

	@Override
	public void push(T t) {
		delegate.push(t);
	}

	@Override
	public T pop() {
		return delegate.pop();
	}

	@Override
	@Nonnull
	public Iterator<T> descendingIterator() {
		return delegate.descendingIterator();
	}
}
